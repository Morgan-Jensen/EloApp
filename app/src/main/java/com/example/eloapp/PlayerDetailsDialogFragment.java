package com.example.eloapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;

public class PlayerDetailsDialogFragment extends DialogFragment {

    private View root;
    private int player_pk;
    Player player;
    private TextView txtName, txtElo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_details_dialog, container, false);
        txtName = (TextView)root.findViewById(R.id.txtPlayerName_Details);
        txtElo = (TextView)root.findViewById(R.id.txtPlayerElo_Details);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            player_pk = bundle.getInt("player_pk");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    player = AppDatabase.getInstance(getContext())
                            .playerDAO()
                            .getByPid(player_pk);

                    txtName.setText(player.getName());
                    txtElo.setText(Integer.toString(player.getElo()));
                }
            }).start();
        }

        return root;
    }
}