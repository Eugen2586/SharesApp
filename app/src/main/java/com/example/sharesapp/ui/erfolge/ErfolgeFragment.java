package com.example.sharesapp.ui.erfolge;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.Model.Erfolge;
import com.example.sharesapp.R;

public class ErfolgeFragment extends Fragment {

    private ErfolgeViewModel erfolgeViewModel;
    private Erfolge erfolge;
    private Button[] kaufen;
    private Button[] verkaufen;
    private Button[] spiel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_erfolge, container, false);

        erfolge = new Erfolge();

        Button bk1 = root.findViewById(R.id.bk1);
        Button bk2 = root.findViewById(R.id.bk2);
        Button bk3 = root.findViewById(R.id.bk3);
        Button bk4 = root.findViewById(R.id.bk4);

        Button bv1 = root.findViewById(R.id.bv1);
        Button bv2 = root.findViewById(R.id.bv2);
        Button bv3 = root.findViewById(R.id.bv3);
        Button bv4 = root.findViewById(R.id.bv4);

        Button br1 = root.findViewById(R.id.br1);
        Button br2 = root.findViewById(R.id.br2);
        Button br3 = root.findViewById(R.id.br3);
        Button br4 = root.findViewById(R.id.br4);

        Button[] kaufen = {bk1, bk2, bk3, bk4};
        Button[] verkaufen = {bv1, bv2, bv3, bv4};
        Button[] spiel = {br1, br2, br3, br4};

        erfolge = new Erfolge();

        this.colorButtons(kaufen, verkaufen, spiel);

        return root;
    }

    private void colorButtons(Button[] kaufen, Button[] verkaufen, Button[] spiel) {

        boolean[] k = erfolge.getKaufen();
        boolean[] v = erfolge.getVerkaufen();
        boolean[] r = erfolge.getReset();
        boolean a = erfolge.getAll();

        int red = Color.rgb(255, 102, 102);
        int green = Color.rgb(128, 255, 191);


        for (int i = 0; i < 4; i += 1) {
            if (k[i]) {
                kaufen[i].setBackgroundColor(green);
            } else {
                kaufen[i].setBackgroundColor(red);
            }
            if (v[i]) {
                verkaufen[i].setBackgroundColor(green);
            } else {
                verkaufen[i].setBackgroundColor(red);
            }
            if (i < 3) {
                if (r[i]) {
                    spiel[i].setBackgroundColor(green);
                } else {
                    spiel[i].setBackgroundColor(red);
                }
            } else {
                if (a) {
                    spiel[i].setBackgroundColor(green);
                } else {
                    spiel[i].setBackgroundColor(red);
                }
            }
        }
    }
}