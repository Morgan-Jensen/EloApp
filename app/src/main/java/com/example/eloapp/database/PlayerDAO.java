package com.example.eloapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Query("select * from Player")
    LiveData<List<Player>> getAll();

    @Insert
    void insert(Player player);

    @Delete
    void delete(Player player);

    @Query("select * from Player where name=:name LIMIT 1")
    Player loadByName(String name);
}
