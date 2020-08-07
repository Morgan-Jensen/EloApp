package com.example.eloapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Query("select * from Player")
    LiveData<List<Player>> getAll();

    @Query("select * from Player")
    Player[] getArray();

    @Insert
    void insert(Player player);

    @Delete
    void delete(Player player);

    @Update
    void update(Player player);

    @Query("select * from Player where name=:name LIMIT 1")
    Player loadByName(String name);

    @Query("select * from Player where pid=:pid LIMIT 1")
    Player getByPid(int pid);

    @Query("select * from Player where name=:name LIMIT 1")
    Player getByName(String name);


}
