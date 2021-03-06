package com.example.eloapp;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;

import java.util.List;

public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder>{

    public final List<Player> players;

    public PlayerRecyclerViewAdapter(List<Player> players) {
        this.players = players;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        public Player player;
        public TextView txtPlayerName, txtPlayerElo;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;

            txtPlayerName = (TextView)root.findViewById(R.id.txtPlayerNameCard);
            txtPlayerElo = (TextView)root.findViewById(R.id.txtPlayerEloCard);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Player player = players.get(position);

        if (player != null) {
            holder.player = player;
            holder.txtPlayerName.setText(player.getName());
            holder.txtPlayerElo.setText(Integer.toString(player.getElo()));

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("player_pk", player.getPid());

                    PlayerDetailsDialogFragment dialogFragment = new PlayerDetailsDialogFragment();
                    dialogFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, dialogFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void addItems(List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
        notifyDataSetChanged();
    }


}
