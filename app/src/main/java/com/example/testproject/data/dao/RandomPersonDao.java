package com.example.testproject.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.testproject.data.model.Person;

import java.util.List;

@Dao
public interface RandomPersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersons(List<Person> persons);

    @Query("SELECT * FROM persons")
    LiveData<List<Person>> getAllPersons();

    @Query("DELETE FROM persons")
    void deleteAllPersons();
}
