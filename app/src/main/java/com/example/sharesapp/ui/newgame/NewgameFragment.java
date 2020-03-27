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

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

/**
 * responsible for the NewGame Fragment
 * Enables the user to reset the game and delete user specific data
 */
public class NewgameFragment extends Fragment {

    private Model model = new Model();
    private Data data;
    private TextView cash;

    /**
     * TODO
     *
     * @param inflater           to inflate the newgame_fragment
     * @param container          used for the inflation
     * @param savedInstanceState not needed
     * @return
     */
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);

        data = model.getData();
        cash = root.findViewById(R.id.betrag_text);
        String wert = (new Anzeige()).makeItBeautiful(data.getDepot().getGeldwert());
        cash.setText((wert + "€"));
        TextView schwierigkeitsgrad = root.findViewById(R.id.schwierigkeitsgrad);
        schwierigkeitsgrad.setText("Schwierigkeitsgrad: " + data.getDepot().getSchwierigkeitsgrad(0)[0]);

        final Button reset_button = root.findViewById(R.id.reset_button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserButtonClickHandler(inflater, reset_button);
            }
        });

        Button licence = root.findViewById(R.id.licence);
        licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                licenceButtonClickHandler(inflater, reset_button);
            }
        });
        return root;
    }

    private void licenceButtonClickHandler(LayoutInflater inflater, Button reset_button) {
        Context context = NewgameFragment.this.getContext();
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle("Licence Note");
            builder.setMessage("Im Programm wird auch folgende Musik benutzt: Creative Minds, " +
                    "Composer: Benjamin Tissot, von Webseite \"RoyaltyFreeMusicfromBensound\" " +
                    "(www.bensound.com/royalty-free-music/), Bomb Sound, von Webseite http://soundbible.com/ " +
                    "- unter Attribution 3.0 Lizenz https://creativecommons.org/licenses/by/3.0/, " +
                    "PokerChips,von Webseite http://soundbible. com/-unterAttribution 3.0 Lizenz " +
                    "https://creativecommons.org/licenses/ by/3.0/, StaplingPaperSound,von Webseite " +
                    "http://soundbible. com/ -unterPublicDomainLizenz https://creativecommons.org/licenses/ publicdomain/deed.de\n");


            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void reserButtonClickHandler(final LayoutInflater inflater, final Button reset_button) {
        Context context = NewgameFragment.this.getContext();
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle("Achtung!");
            builder.setMessage("Alle gekauften Aktien und Favoriten werden ebenfalls gelöscht");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Bomb Sound http://soundbible.com/1234-Bomb.html
                            MediaPlayer.create(reset_button.getContext(), R.raw.bomb).start();

                            Toast.makeText(NewgameFragment.this.getContext(), "Betrag, alle gekauften Aktien und Favoriten werden zurückgesetzt", Toast.LENGTH_LONG).show();
                            showDifficultyDialog(inflater);
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

    /**
     Zeigt Dialog um Schwierigkeitsgrad auszuwählen
     */
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

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            Button einfach = diffLevelDialog.findViewById(R.id.b_einfach);
            Button normal = diffLevelDialog.findViewById(R.id.b_normal);
            Button schwer = diffLevelDialog.findViewById(R.id.b_schwer);
            Button challenge = diffLevelDialog.findViewById(R.id.b_challenge);

            einfach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(1);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(1)[1]);
                }
            });

            normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(2);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(2)[1]);
                }
            });

            schwer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(3);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(3)[1]);
                }
            });

            challenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(4);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(4)[1]);
                }
            });
        }
    }
}