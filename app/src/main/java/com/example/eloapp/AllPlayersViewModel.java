package com.example.eloapp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;

import java.util.List;

public class AllPlayersViewModel extends ViewModel {

    private LiveData<List<Player>> playerList;

    public LiveData<List<Player>> getPlayerList(Context c) {
        if (playerList != null) return playerList;
        else return playerList = AppDatabase.getInstance(c).playerDAO().getAll();
    }
}
