package com.example.eloapp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eloapp.database.Player;
import com.google.android.material.textfield.TextInputEditText;

public class NewPlayerDialogFragment extends DialogFragment {

    private View root;
    private TextInputEditText txtPlayerName;
    Player player;
    Toolbar toolbar;
    private boolean isNew = true; // tells if adding or editing
    private int player_pkTwo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_new_player_dialog, container, false);
        toolbar = (Toolbar)root.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

        setHasOptionsMenu(true);

        return root;
    }
}