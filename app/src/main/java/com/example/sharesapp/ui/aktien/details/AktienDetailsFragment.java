package com.example.sharesapp.ui.aktien.details;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class AktienDetailsFragment extends Fragment {

    private Model model = new Model();
    private View root;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_aktien_details, container, false);

        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                System.out.println("Hallo");
                setCurrentStock();
                setStockDetails();
            }
        };

        model.getData().getAktienList().observe(getViewLifecycleOwner(), listObserver);

        final Observer<Aktie> currentStockObserver = new Observer<Aktie>() {
            @Override
            public void onChanged(Aktie aktie) {
                setStockDetails();
            }
        };

        model.getData().currentStock.observe(getViewLifecycleOwner(), currentStockObserver);

        setStockDetails();

        Button buy_button = root.findViewById(R.id.kaufen_button);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = AktienDetailsFragment.this.getContext();
                if (context != null) {
                    View buyDialogView = inflater.inflate(R.layout.buy_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    Data data = new Model().getData();
                    String wert = (new Anzeige()).makeItBeautiful(data.getDepot().getGeldwert());
                    TextView cash = buyDialogView.findViewById(R.id.geldwert);
                    cash.setText((wert + "€"));
                    builder.setCancelable(true);
                    builder.setView(buyDialogView);
                    builder.setPositiveButton("Kaufen",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //todo kaufen
                                }
                            });
                    builder.setNegativeButton("Verwerfen", new DialogInterface.OnClickListener() {
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

        // Inflate the layout for this fragment
        return root;
    }

    private void setCurrentStock() {
        String symbol = model.getData().getCurrentStock().getSymbol();
        ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
        Aktie currentStock = null;
        for (Aktie stock : stockList) {
            if (symbol.equals(stock.getSymbol())) {
                currentStock = stock;
                break;
            }
        }
        model.getData().setCurrentStock(currentStock);
    }

    private void setStockDetails() {
        Aktie stock = model.getData().getCurrentStock();
        TextView symbolTV = root.findViewById(R.id.symbol_field);
        symbolTV.setText(stock.getSymbol());
        TextView nameTV = root.findViewById(R.id.name_field);
        nameTV.setText(stock.getName());
        TextView priceTV = root.findViewById(R.id.latest_price_field);
        priceTV.setText(String.valueOf(stock.getPreis()));
        TextView dateTV = root.findViewById(R.id.date_field);
        dateTV.setText(stock.getDate());
        // todo set all fields
    }
}
