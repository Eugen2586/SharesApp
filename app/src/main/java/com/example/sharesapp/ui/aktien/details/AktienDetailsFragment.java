package com.example.sharesapp.ui.aktien.details;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.ui.newgame.NewgameFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AktienDetailsFragment extends Fragment {

    private Model model = new Model();
    private View root;
    private EditText kaufMenge;
    private EditText Limit;
    private TextView totalPrice;

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

        final Button buyButton = root.findViewById(R.id.kaufen_button);

        buyButton.setOnClickListener(new View.OnClickListener() {
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
                    totalPrice = buyDialogView.findViewById(R.id.total_price);


                    kaufMenge = buyDialogView.findViewById(R.id.kaufMenge);

                    kaufMenge.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            setTotalPrice();
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });

                    Limit = buyDialogView.findViewById(R.id.Limit);

                    Limit.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            setTotalPrice();
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });


                    builder.setCancelable(true);
                    builder.setView(buyDialogView);
                    builder.setPositiveButton("Kaufen",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!kaufMenge.getText().toString().isEmpty()) {
                                        float limit;
                                        boolean limit_b = false; // shows if we want to buy with limit or direct with the price
                                        if (!Limit.getText().toString().isEmpty()) {
                                            limit = Float.valueOf(Limit.getText().toString());
                                            limit_b = true;
                                        } else {
                                            limit = model.getData().currentStock.getValue().getPreis();
                                        }
                                        int number = Integer.parseInt(kaufMenge.getText().toString());
                                        float price = limit * number * model.getData().getDepot().getProzent();
                                        if (price > model.getData().getDepot().getGeldwert()) {
                                            Toast.makeText(AktienDetailsFragment.this.getContext(), "Nicht genug Geld auf dem Konto!", Toast.LENGTH_LONG).show();

                                        } else {
                                            if (!limit_b) {
                                                Aktie a = model.getData().currentStock.getValue().getClone();
                                                a.setAnzahl(number);
                                                model.getData().getDepot().kaufeAktie(a);
                                                Toast.makeText(AktienDetailsFragment.this.getContext(), "Habe Aktien gekauft.", Toast.LENGTH_LONG).show();
                                            } else {
                                                //todo kaufen mit limit
                                                Toast.makeText(AktienDetailsFragment.this.getContext(), "TODO: AKTIEN KAUFEN", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    } else {
                                        Toast.makeText(AktienDetailsFragment.this.getContext(), "Bitte Kaufmenge eingeben.", Toast.LENGTH_LONG).show();

                                    }
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

        final Button portfolioButton = root.findViewById(R.id.portfolio_button);
        boolean foundInPortfolio = getFoundInPortfolio();
        setTextForPortfolioButton(portfolioButton, foundInPortfolio);
        System.out.println(model.getData().getPortfolioList());
        portfolioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(model.getData().getPortfolioList());
                boolean foundInPortfolio = getFoundInPortfolio();
                setTextForPortfolioButton(portfolioButton, !foundInPortfolio);

                // add to or remove from Portfolio
                String currentSymbol = model.getData().getCurrentStock().getSymbol();
                if (foundInPortfolio) {
                    model.getData().removeFromPortfolio(currentSymbol);
                } else {
                    model.getData().addToPortfolio(model.getData().getCurrentStock(), currentSymbol);
                }
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    private void setCurrentStock() {
        Aktie currentStock = model.getData().getCurrentStock();
        String symbol = currentStock.getSymbol();
        ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
        if (stockList != null) {
            for (Aktie stock : stockList) {
                if (symbol.equals(stock.getSymbol())) {
                    currentStock = stock;
                    break;
                }
            }
            model.getData().setCurrentStock(currentStock);
        }
    }

    private void setTotalPrice() {

        float limit;
        if (!Limit.getText().toString().isEmpty()) {
            limit = Float.valueOf(Limit.getText().toString());
        } else {
            limit = model.getData().getCurrentStock().getPreis();
        }
        if (kaufMenge.getText().toString().isEmpty()) {
            return;
        }

        float number = 0;
        System.out.println(number);
        if (!kaufMenge.getText().toString().isEmpty()) {
            number = Float.valueOf(kaufMenge.getText().toString());
            System.out.println(number);
        }

        float price = limit * number * model.getData().getDepot().getProzent();
        totalPrice.setText(String.valueOf((new Anzeige()).makeItBeautifulEuro(price)));

        if (price > model.getData().getDepot().getGeldwert()) {
            totalPrice.setTextColor(Color.RED);
        } else {
            totalPrice.setTextColor(Color.DKGRAY);
        }


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
        TextView typeTV = root.findViewById(R.id.type_field);
        typeTV.setText(stock.getType());
        // todo set all fields
    }

    private boolean getFoundInPortfolio() {
        boolean foundInPortfolio = false;

        // change foundInPortfolio if portfolio contains currentStock
        String currentSymbol = model.getData().getCurrentStock().getSymbol();
        if (model.getData().getPortfolioList().getValue() != null) {
            for (Aktie portfolioStock : model.getData().getPortfolioList().getValue()) {
                if (portfolioStock.getSymbol().equals(currentSymbol)) {
                    foundInPortfolio = true;
                    break;
                }
            }
        }

        return foundInPortfolio;
    }

    private void setTextForPortfolioButton(Button portfolioButton, boolean foundInPortfolio) {
        if (foundInPortfolio) {
            portfolioButton.setText(R.string.remove_from_portfolio);
        } else {
            portfolioButton.setText(R.string.add_to_portfolio);
        }
    }
}
