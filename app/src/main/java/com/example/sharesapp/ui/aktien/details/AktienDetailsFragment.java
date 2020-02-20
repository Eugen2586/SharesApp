package com.example.sharesapp.ui.aktien.details;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

        final Button buy_button = root.findViewById(R.id.kaufen_button);

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
                    TextView price = buyDialogView.findViewById(R.id.price_one);
                    price.setText((new Anzeige()).makeItBeautifulEuro(model.getData().getCurrentStock().getPreis()));
                    TextView totalPrice = buyDialogView.findViewById(R.id.total_price);


                    EditText kaufMenge = buyDialogView.findViewById(R.id.kaufMenge);

                    kaufMenge.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    EditText Limit = buyDialogView.findViewById(R.id.Limit);

                    Limit.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });



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

    private void setTotalPrice() {
        
    }

    private void setStockDetails() {
        Aktie stock = model.getData().getCurrentStock();
        TextView symbolTV = root.findViewById(R.id.symbol_field);
        symbolTV.setText(stock.getSymbol());
        TextView nameTV = root.findViewById(R.id.name_field);
        nameTV.setText(stock.getName());
        TextView nameBig = root.findViewById(R.id.name_big);
        nameBig.setText(stock.getName());
        TextView priceTV = root.findViewById(R.id.latest_price_field);
        priceTV.setText((new Anzeige()).makeItBeautifulEuro(stock.getPreis()));
        TextView dateTV = root.findViewById(R.id.date_field);
        dateTV.setText(stock.getDate());
        // todo set all fields
    }
}
