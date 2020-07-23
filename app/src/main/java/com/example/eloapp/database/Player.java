package com.example.eloapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {



    @PrimaryKey(autoGenerate = true)
    private int pid;
    private String name;
    private int elo;

    public Player(String name) {
        this.name = name;
        elo = 1000;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    @NonNull
    @Override
    public String toString() {
        return "{PID: " + pid + "\tName: " + name + "\tElo: " + elo + "}";
    }
}
