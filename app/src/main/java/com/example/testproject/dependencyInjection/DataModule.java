package com.example.testproject.dependencyInjection;

import android.app.Application;

import com.example.testproject.data.dao.RandomPersonDao;
import com.example.testproject.data.database.PersonDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {
    @Provides
    @Singleton
    public PersonDatabase provideAppDatabase(Application application) {
        return PersonDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    public RandomPersonDao providePersonDao(PersonDatabase appDatabase) {
        return appDatabase.randomPersonDao();
    }
}
