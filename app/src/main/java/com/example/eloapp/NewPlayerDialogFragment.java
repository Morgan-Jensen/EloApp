package com.example.eloapp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.eloapp.database.AppDatabase;
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
        txtPlayerName = (TextInputEditText)root.findViewById(R.id.txtPlayerName_Dialog);

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_create_dialog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isNew) {
                            Player newPlayer = new Player(txtPlayerName.getText().toString());
                            AppDatabase.getInstance(getContext())
                                    .playerDAO()
                                    .insert(newPlayer);
                        } else {
                            // update player
                        }
                    }
                }).start();
                dismiss();
                break;

            case android.R.id.home:
                dismiss();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}