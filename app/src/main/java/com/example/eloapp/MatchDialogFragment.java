package com.example.eloapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;

import java.util.List;

public class MatchDialogFragment extends DialogFragment {

    private View root;
    private Spinner p1Spinner;
    private Spinner p2Spinner;
    private Player[] players;
    private String[] pNames;

    private ArrayAdapter<String> adapter;

    private TextView p1WinPercent;
    private TextView p2WinPercent;

    private Button matchButton;
    private Button p1WinsButton;
    private Button tieButton;
    private Button p2WinsButton;

    private Player p1;
    private Player p2;

    double p1Odds;
    double p2Odds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_match_dialog, container, false);

        p1Spinner = (Spinner) root.findViewById(R.id.player1Spinner);
        p2Spinner = (Spinner) root.findViewById(R.id.player2Spinner);
        p1WinPercent = (TextView) root.findViewById(R.id.txtP1Percent);
        p2WinPercent = (TextView) root.findViewById(R.id.txtP2Percent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                players = AppDatabase.getInstance(getContext())
                            .playerDAO()
                            .getArray();

                pNames = new String[players.length];

                for (int i = 0; i < players.length; i++) {
                    pNames[i] = players[i].getName();
                }

                adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, pNames);

                p1Spinner.setAdapter(adapter);
                p2Spinner.setAdapter(adapter);
            }
        }).start();

        matchButton = (Button) root.findViewById(R.id.btnMatch);
        p1WinsButton = (Button) root.findViewById(R.id.btnP1Wins);
        p2WinsButton = (Button) root.findViewById(R.id.btnP2Wins);
        tieButton = (Button) root.findViewById(R.id.btnTie);

        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMatch();
            }


        });

        return root;
    }

    private void setMatch() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                p1 = AppDatabase.getInstance(getContext())
//                        .playerDAO()
//                        .getByName(p1Spinner.getSelectedItem().toString());
//
//                p2 = AppDatabase.getInstance(getContext())
//                        .playerDAO()
//                        .getByName(p2Spinner.getSelectedItem().toString());
//            }
//        }).start();

        p1 = players[p1Spinner.getSelectedItemPosition()];
        p2 = players[p2Spinner.getSelectedItemPosition()];

//        p1 = (Player) p1Spinner.getSelectedItem();
//        p2 = (Player) p2Spinner.getSelectedItem();


        calcOdds();
    }

    private void calcOdds() {
        double QA = Math.pow(10, (p1.getElo() / 400)); // 10^(Ra / 400)
        double QB = Math.pow(10, (p2.getElo() / 400)); // 10^(Rb / 400);

        p1Odds = QA / (QA + QB);
        p2Odds = QB / (QA + QB);

        p1WinPercent.setText(Double.toString(p1Odds * 100) + "%");
        p2WinPercent.setText(Double.toString(p2Odds * 100) + "%");
    }
}