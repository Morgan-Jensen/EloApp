package com.example.eloapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.eloapp.database.AppDatabase;
import com.example.eloapp.database.Player;

public class PlayerDetailsDialogFragment extends DialogFragment {

    private View root;
    private int player_pk;
    private Player player;
    private TextView txtName, txtElo;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_details_dialog, container, false);
        txtName = (TextView)root.findViewById(R.id.txtPlayerName_Details);
        txtElo = (TextView)root.findViewById(R.id.txtPlayerElo_Details);
        toolbar = (Toolbar)root.findViewById(R.id.toolbar_details);

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
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_dialog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            // click the 'X'
            case android.R.id.home:
                dismiss();
                break;
            // user clicks delete
            // use AlertDialog builder
            // set the PositiveButton click to delete the user from the db.
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this?")
                        .setTitle("Delete");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase.getInstance(getContext())
                                        .playerDAO()
                                        .delete(player);
                            }
                        }).start();
                        dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;

            // user clicks on edit
            // create bundle
            // launch the new user dialog fragment and send bundle


            // ALL DB TRANSACTION NEEDS TO BE DONE IN A THREAD!!!
        }

        return super.onOptionsItemSelected(item);
    }
}