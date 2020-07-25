package com.example.eloapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eloapp.database.Player;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private PlayerRecyclerViewAdapter playerRecyclerViewAdapter;
    private int columnCount = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getContext();
        playerRecyclerViewAdapter = new PlayerRecyclerViewAdapter(new ArrayList<Player>());

        if (columnCount <= 1) recyclerView.setLayoutManager(new LinearLayoutManager(context));
        else recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));

        recyclerView.setAdapter(playerRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

        ViewModelProviders.of(this)
                .get(AllPlayersViewModel.class)
                .getPlayerList(context)
                .observe(this, new Observer<List<Player>>() {
                    @Override
                    public void onChanged(List<Player> players) {
                        if (players != null) playerRecyclerViewAdapter.addItems(players);
                    }
                });
    }
}