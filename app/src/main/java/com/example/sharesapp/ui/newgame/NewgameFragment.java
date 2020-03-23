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
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

public class NewgameFragment extends Fragment {

    private Model model = new Model();
    Data data;
    TextView cash;

    public View onCreateView(@NonNull LayoutInflater inflater,
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
                                    // Bomb Sound http://soundbible.com/1234-Bomb.html
                                    MediaPlayer.create(reset_button.getContext(), R.raw.bomb).start();

                                    Toast.makeText(NewgameFragment.this.getContext(), "Betrag, alle gekauften Aktien und Favoriten werden zurückgesetzt", Toast.LENGTH_LONG).show();

                                    model.getData().resetData();

                                    View view = getView();
                                    if (view != null) {
                                        Navigation.findNavController(view).navigateUp();
                                    }
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
}