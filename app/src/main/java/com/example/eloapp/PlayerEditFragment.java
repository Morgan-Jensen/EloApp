package com.example.eloapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.List;

public class PlayerEditFragment extends Fragment {

    View root;
    private TextInputEditText pName;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_edit, container, false);

        pName = (TextInputEditText)root.findViewById(R.id.txtPlayerNameEdit);
        btnSubmit = (Button)root.findViewById(R.id.btnSubmitEdit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = pName.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(getContext())
                                .playerDAO()
                                .insert(new Player(name));

                        List<Player> players = AppDatabase.getInstance(getContext())
                                .playerDAO()
                                .getAll();

                        for (Player player : players) {
                            Log.d("db", "Player" + player.toString());
                        }
                    }
                }).start();
            }
        });

        return root;
    }
}