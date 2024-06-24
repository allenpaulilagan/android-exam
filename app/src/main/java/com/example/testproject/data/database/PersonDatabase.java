package com.example.testproject.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testproject.data.dao.RandomPersonDao;
import com.example.testproject.data.model.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract RandomPersonDao randomPersonDao();

    private static volatile PersonDatabase instance;

    public static PersonDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (PersonDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            PersonDatabase.class, "person_database")
                            .build();
                }
            }
        }
        return instance;
    }
}
