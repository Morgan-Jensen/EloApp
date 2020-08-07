package com.example.eloapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;

import org.w3c.dom.Text;

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
    private TextView p1Elo;
    private TextView p2Elo;
    private TextView result;
    private TextView errorMessage;

    private Button matchButton;
    private Button p1WinsButton;
    private Button tieButton;
    private Button p2WinsButton;

    private Player p1;
    private Player p2;

    private double p1Odds;
    private double p2Odds;

    private boolean isMatchSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_match_dialog, container, false);

        p1Spinner = (Spinner) root.findViewById(R.id.player1Spinner);
        p2Spinner = (Spinner) root.findViewById(R.id.player2Spinner);
        p1WinPercent = (TextView) root.findViewById(R.id.txtP1Percent);
        p2WinPercent = (TextView) root.findViewById(R.id.txtP2Percent);
        p1Elo = (TextView) root.findViewById(R.id.txtP1Elo);
        p2Elo = (TextView) root.findViewById(R.id.txtP2Elo);
        result = (TextView) root.findViewById(R.id.txtResult);
        errorMessage = (TextView) root.findViewById(R.id.txtErrorMessage);

        isMatchSet = false;

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

        p1WinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMatchSet) p1Victory();
            }
        });

        p2WinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMatchSet) p2Victory();
            }
        });

        tieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMatchSet) tie();
            }
        });

        return root;
    }

    private void tie() {
        int newP1Elo = (int) Math.round(p1.getElo() + (32 * (.5 - p1Odds)));
        int newP2Elo = (int) Math.round(p2.getElo() + (32 * (.5 - p2Odds)));

        result.setText("Tie!");
        updateElo(newP1Elo, newP2Elo);
    }

    private void p1Victory() {
        int newP1Elo = (int) Math.round(p1.getElo() + (32 * (1 - p1Odds)));
        int newP2Elo = (int) Math.round(p2.getElo() + (32 * (0 - p2Odds)));

        result.setText(p1.getName() + " Wins!");
        updateElo(newP1Elo, newP2Elo);
    }

    private void p2Victory() {
        int newP1Elo = (int) Math.round(p1.getElo() + (32 * (0 - p1Odds)));
        int newP2Elo = (int) Math.round(p2.getElo() + (32 * (1 - p2Odds)));

        result.setText(p2.getName() + " Wins!");
        updateElo(newP1Elo, newP2Elo);
    }

    private void setMatch() {
        p1 = players[p1Spinner.getSelectedItemPosition()];
        p2 = players[p2Spinner.getSelectedItemPosition()];

        if (p1 == p2) {
            errorMessage.setText("Please Select 2 Different Players!");
            isMatchSet = false;
            return;
        } else {
            errorMessage.setText("");
            isMatchSet = true;
        }

        p1Elo.setText(Integer.toString(p1.getElo()));
        p2Elo.setText(Integer.toString(p2.getElo()));

        calcOdds();
    }

    private void calcOdds() {
        double RA = (double) p1.getElo() / 400;
        Log.d("test", "elo1: " + Integer.toString(p1.getElo() / 400));
        double RB = (double) p2.getElo() / 400;
        Log.d("test", "calcOdds: " + Integer.toString(p2.getElo() / 400));
        double QA = Math.pow(10, RA);
        Log.d("test", "calcOdds: " + Double.toString(QA));
        double QB = Math.pow(10, RB);
        Log.d("test", "calcOdds: " + Double.toString(QB));

        p1Odds = QA / (QA + QB);
        p2Odds = QB / (QA + QB);


        p1WinPercent.setText(String.format("%.2f", p1Odds * 100) + "%");
        p2WinPercent.setText(String.format("%.2f", p2Odds * 100) + "%");
    }

    private void updateElo(int newP1Elo, int newP2Elo) {
        p1.setElo(newP1Elo);
        p2.setElo(newP2Elo);

        p1Elo.setText(Integer.toString(newP1Elo));
        p2Elo.setText(Integer.toString(newP2Elo));

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(getContext())
                        .playerDAO()
                        .update(p1);

                AppDatabase.getInstance(getContext())
                        .playerDAO()
                        .update(p2);
            }
        }).start();

        calcOdds();
    }
}