package com.example.sharesapp.ui.newgame;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Depot;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

public class NewgameFragment extends Fragment {

    private Model model = new Model();
    Data data;
    TextView cash;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);

        data = model.getData();
        cash = root.findViewById(R.id.betrag_text);
        String wert = (new Anzeige()).makeItBeautiful(data.getDepot().getGeldwert());
        cash.setText((wert + "€"));

        final Button reset_button = root.findViewById(R.id.reset_button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = NewgameFragment.this.getContext();
                if (context != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Achtung!");
                    builder.setMessage("Alle gekauften Aktien und Favoriten werden ebenfalls gelöscht?");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    showDifficultyDialog(inflater);

                                    // Bomb Sound http://soundbible.com/1234-Bomb.html
                                    MediaPlayer.create(reset_button.getContext(), R.raw.bomb).start();

                                    Toast.makeText(NewgameFragment.this.getContext(), "Betrag, alle gekauften Aktien und Favoriten werden zurückgesetzt", Toast.LENGTH_LONG).show();


                                }
                            });
                    builder.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        return root;
    }

    private void showDifficultyDialog(LayoutInflater inflater) {

        final Context context = NewgameFragment.this.getContext();
        if (context != null) {
            final View diffLevelDialog = inflater.inflate(R.layout.difficulty_level_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton("Fortfahren",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            model.getData().resetData();

                            View view = getView();
                            if (view != null) {
                                Navigation.findNavController(view).navigateUp();
                            }

                        }

                    });

            builder.setCancelable(false);
            builder.setView(diffLevelDialog);


            final AlertDialog dialog = builder.create();
            dialog.show();


            System.out.println(dialog.getButton(AlertDialog.BUTTON_POSITIVE));

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            Button einfach = diffLevelDialog.findViewById(R.id.b_einfach);
            Button normal = diffLevelDialog.findViewById(R.id.b_normal);
            Button schwer = diffLevelDialog.findViewById(R.id.b_schwer);
            Button challenge = diffLevelDialog.findViewById(R.id.b_challenge);

            einfach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(1);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(1)[1]);
                }
            });

            normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(2);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(2)[1]);
                }
            });

            schwer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(3);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(3)[1]);
                }
            });

            challenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(4);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(4)[1]);
                }
            });

            dialog.show();

        }

    }
}