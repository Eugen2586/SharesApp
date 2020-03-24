package com.example.sharesapp.ui.aktien.details;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Stock;
import com.anychart.core.stock.Plot;
import com.anychart.core.stock.series.Hilo;
import com.anychart.data.Table;
import com.anychart.data.TableMapping;
import com.example.sharesapp.FunktionaleKlassen.Diagramm.AnyChartDataBuilder;
import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import java.io.IOException;
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

        initializeSwipeRefresh();

        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                setCurrentStock();
                setStockDetails();
                makeChart();
            }
        };

        model.getData().getAktienList().observe(getViewLifecycleOwner(), listObserver);

        final Observer<Aktie> currentStockObserver = new Observer<Aktie>() {
            @Override
            public void onChanged(Aktie aktie) {
                setStockDetails();
                makeChart();
            }
        };

        model.getData().currentStock.observe(getViewLifecycleOwner(), currentStockObserver);

        setStockDetails();

        final Button sellButton = root.findViewById(R.id.verkaufen_button);

        final Button buyButton = root.findViewById(R.id.kaufen_button);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Requests requests = new Requests();
                try {
                    requests.asyncRun(RequestsBuilder.getQuote(model.getData().getCurrentStock().getSymbol()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Context context = AktienDetailsFragment.this.getContext();
                if (context != null) {
                    final View buyDialogView = inflater.inflate(R.layout.buy_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    Data data = new Model().getData();
                    String wert = (new Anzeige()).makeItBeautiful(data.getDepot().getGeldwert());
                    TextView cash = buyDialogView.findViewById(R.id.geldwert);
                    cash.setText((wert + "€"));
                    if (model.getData().getCurrentStock().getPreis() != 0) {
                        TextView price = buyDialogView.findViewById(R.id.price_one);
                        price.setText((new Anzeige()).makeItBeautifulEuro(model.getData().getCurrentStock().getPreis()));
                    }

                    totalPrice = buyDialogView.findViewById(R.id.total_price);

                    kaufMenge = buyDialogView.findViewById(R.id.kaufMenge);

                    kaufMenge.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                            setTotalPrice(true);
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });

                    Limit = buyDialogView.findViewById(R.id.limit);
                    setInputFilter(Limit);

                    Limit.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                            setTotalPrice(true);
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
                                                // new Request if price == 0.0f
                                                if (a.getPreis() == 0.0f) {
                                                    Requests requests = new Requests();
                                                    try {
                                                        requests.asyncRun(RequestsBuilder.getQuote(a.getSymbol()));
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Toast.makeText(AktienDetailsFragment.this.getContext(), "Kauf nicht erfolgreich:\nDer Wert der Aktie wurde aktualisiert.", Toast.LENGTH_LONG).show();
                                                } else {
                                                    a.setAnzahl(number);
                                                    model.getData().getDepot().kaufeAktie(a);

                                                    // Poker-Chip Sound http://soundbible.com/2204-Poker-Chips.html
                                                    MediaPlayer.create(buyButton.getContext(), R.raw.poker_chips).start();

                                                    sellButton.setVisibility(View.VISIBLE);
                                                    Toast.makeText(AktienDetailsFragment.this.getContext(), "Habe Aktien gekauft.", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                handleBuyOrder(buyDialogView);
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

                    final Observer<Aktie> currentStockObserver = new Observer<Aktie>() {
                        @Override
                        public void onChanged(Aktie aktie) {
                            if (aktie.getPreis() != 0) {
                                TextView price = buyDialogView.findViewById(R.id.price_one);
                                price.setText((new Anzeige()).makeItBeautifulEuro(aktie.getPreis()));
                            }
                        }
                    };

                    model.getData().currentStock.observe(getViewLifecycleOwner(), currentStockObserver);
                    dialog.show();
                }
            }
        });

        final Button portfolioButton = root.findViewById(R.id.portfolio_button);
        boolean foundInPortfolio = getFoundInPortfolio();
        setTextForPortfolioButton(portfolioButton, foundInPortfolio);
        portfolioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean foundInPortfolio = getFoundInPortfolio();
                setTextForPortfolioButton(portfolioButton, !foundInPortfolio);

                // add to or remove from Portfolio
                String currentSymbol = model.getData().getCurrentStock().getSymbol();
                if (foundInPortfolio) {
                    model.getData().removeFromPortfolio(currentSymbol);
                } else {
                    model.getData().addToPortfolio(model.getData().getCurrentStock(), currentSymbol);
                }

                // Stapling Paper Sound http://soundbible.com/1537-Stapling-Paper.html
                MediaPlayer.create(buyButton.getContext(), R.raw.stapling_paper).start();
            }
        });


        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Requests requests = new Requests();
                try {
                    requests.asyncRun(RequestsBuilder.getQuote(model.getData().getCurrentStock().getSymbol()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Context context = AktienDetailsFragment.this.getContext();
                if (context != null) {
                    final View sellDialogView = inflater.inflate(R.layout.sell_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    TextView meine_anzahl = sellDialogView.findViewById(R.id.anzahl_aktien);
                    meine_anzahl.setText(String.valueOf(getFoundInDepot()));
                    if (model.getData().getCurrentStock().getPreis() != 0) {
                        TextView price = sellDialogView.findViewById(R.id.price_one);
                        price.setText((new Anzeige()).makeItBeautifulEuro(model.getData().getCurrentStock().getPreis()));
                    }
                    totalPrice = sellDialogView.findViewById(R.id.total_price);


                    kaufMenge = sellDialogView.findViewById(R.id.verkaufMenge);

                    kaufMenge.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            setTotalPrice(false);
                            checkVerkaufMenge(getFoundInDepot());
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });

                    Limit = sellDialogView.findViewById(R.id.limit);
                    setInputFilter(Limit);

                    Limit.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            setTotalPrice(false);
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });


                    builder.setCancelable(true);
                    builder.setView(sellDialogView);
                    builder.setPositiveButton("Verkaufen",
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
                                        float price = limit * number * (2f - model.getData().getDepot().getProzent());
                                        if (Integer.parseInt(kaufMenge.getText().toString()) > getFoundInDepot()) {
                                            // falls will mehr verakufen als habe
                                            Toast.makeText(AktienDetailsFragment.this.getContext(), "Nicht genug Aktien zu verkaufen!", Toast.LENGTH_LONG).show();

                                        } else {
                                            if (!limit_b) {
                                                Aktie a = model.getData().currentStock.getValue().getClone();
                                                a.setAnzahl(Integer.parseInt(kaufMenge.getText().toString()));

                                                // Poker-Chip Sound http://soundbible.com/2204-Poker-Chips.html
                                                MediaPlayer.create(buyButton.getContext(), R.raw.poker_chips).start();

                                                model.getData().getDepot().verkaufeAktie(a);
                                                int anzahl = getFoundInDepot();
                                                if (anzahl == 0) {
                                                    sellButton.setVisibility(View.GONE);
                                                } else {
                                                    sellButton.setVisibility(View.VISIBLE);
                                                }
                                                Toast.makeText(AktienDetailsFragment.this.getContext(), "Habe Aktien verkauft.", Toast.LENGTH_LONG).show();
                                            } else {
                                                handleSellOrder(sellDialogView);
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

                    final Observer<Aktie> currentStockObserver = new Observer<Aktie>() {
                        @Override
                        public void onChanged(Aktie aktie) {
                            if (aktie.getPreis() != 0) {
                                TextView price = sellDialogView.findViewById(R.id.price_one);
                                price.setText((new Anzeige()).makeItBeautifulEuro(aktie.getPreis()));
                            }
                        }
                    };

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        initializeOrderButton();

        return root;
    }

    private void initializeSwipeRefresh() {
        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Requests requests = new Requests();
                try {
                    requests.asyncRun(RequestsBuilder.getQuote(model.getData().getCurrentStock().getSymbol()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initializeOrderButton() {
        Button orderButton = root.findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDeleteOrderConfirmation();
            }
        });

        // Listener for buyOrderList and sellOrderList
        final Observer<ArrayList<Order>> buyOrderObserver = new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orderList) {
                showHideDeleteOrderButton();
            }
        };
        model.getData().getBuyOrderList().observe(getViewLifecycleOwner(), buyOrderObserver);

        final Observer<ArrayList<Order>> sellOrderObserver = new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orderList) {
                showHideDeleteOrderButton();
            }
        };
        model.getData().getSellOrderList().observe(getViewLifecycleOwner(), sellOrderObserver);
    }

    private void createDeleteOrderConfirmation() {
        Context context = AktienDetailsFragment.this.getContext();
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle("Achtung!");
            builder.setMessage("Sollen wirklich alle Aufträge dieser Aktie gelöscht werden?");
            builder.setPositiveButton("Ja",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AktienDetailsFragment.this.getContext(), "Alle Aufträge dieser Aktie wurden gelöscht", Toast.LENGTH_LONG).show();

                            ArrayList<Order> buyOrderListToRemove = getAllOrderForCurrentStock(model.getData().getBuyOrderList().getValue());
                            model.getData().removeBuyOrderList(buyOrderListToRemove);
                            ArrayList<Order> sellOrderListToRemove = getAllOrderForCurrentStock(model.getData().getSellOrderList().getValue());
                            model.getData().removeSellOrderList(sellOrderListToRemove);
                        }
                    });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showHideDeleteOrderButton() {
        ArrayList<Order> buyOrderList = getAllOrderForCurrentStock(model.getData().getBuyOrderList().getValue());
        ArrayList<Order> sellOrderList = getAllOrderForCurrentStock(model.getData().getSellOrderList().getValue());
        if (buyOrderList.size() == 0 && sellOrderList.size() == 0) {
            root.findViewById(R.id.order_button).setVisibility(View.GONE);
        } else {
            root.findViewById(R.id.order_button).setVisibility(View.VISIBLE);
        }
    }

    private void handleBuyOrder(View buyDialogView) {
        EditText numberText = buyDialogView.findViewById(R.id.kaufMenge);
        EditText limitText = buyDialogView.findViewById(R.id.limit);
        int number = Integer.parseInt(numberText.getText().toString());
        float limit = Float.parseFloat(limitText.getText().toString());

        if (limit < model.getData().getCurrentStock().getPreis()) {
            Order buyOrder = new Order(model.getData().getCurrentStock(), model.getData().getCurrentStock().getSymbol(), number, limit);
            model.getData().addBuyOrder(buyOrder);
            Toast.makeText(AktienDetailsFragment.this.getContext(), "Kaufsauftrag wurde erstellt.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AktienDetailsFragment.this.getContext(), "Angegebenes Limit befindet sich nicht unter dem Einzelwert der Aktie.", Toast.LENGTH_LONG).show();
        }
    }

    private void handleSellOrder(View sellDialogView) {
        EditText numberText = sellDialogView.findViewById(R.id.verkaufMenge);
        EditText limitText = sellDialogView.findViewById(R.id.limit);
        int number = Integer.parseInt(numberText.getText().toString());
        float limit = Float.parseFloat(limitText.getText().toString());

        if (limit > model.getData().getCurrentStock().getPreis()) {
            Order sellOrder = new Order(model.getData().getCurrentStock(), model.getData().getCurrentStock().getSymbol(), number, limit);
            model.getData().addSellOrder(sellOrder);
            Toast.makeText(AktienDetailsFragment.this.getContext(), "Verkaufauftrag wurde erstellt.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AktienDetailsFragment.this.getContext(), "Angegebenes Limit befindet sich nicht über dem Einzelwert der Aktie.", Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Order> getAllOrderForCurrentStock(ArrayList<Order> buySellOrderList) {
        Aktie currentStock = model.getData().getCurrentStock();
        ArrayList<Order> orderList = new ArrayList<>();
        if (buySellOrderList != null) {
            for (Order order : buySellOrderList) {
                if (order.getSymbol().equals(currentStock.getSymbol())) {
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    private void checkVerkaufMenge(int anzahl) {
        if (kaufMenge.getText().toString().isEmpty()) {
            return;
        }

        if (Integer.parseInt(kaufMenge.getText().toString()) > anzahl) {
            kaufMenge.setTextColor(Color.RED);
        } else {
            kaufMenge.setTextColor(Color.DKGRAY);
        }

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

    private void setTotalPrice(boolean kaufen) {

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

        if (kaufen) {
            if (price > model.getData().getDepot().getGeldwert()) {
                totalPrice.setTextColor(Color.RED);
            } else {
                totalPrice.setTextColor(Color.DKGRAY);
            }
        }


    }

    private void setStockDetails() {
        Aktie stock = model.getData().getCurrentStock();
        // dont show if lastPrice == 0.0f
        float lastPrice = stock.getPreis();
        if (lastPrice != 0.0f) {
            TextView symbolTV = root.findViewById(R.id.symbol_field);
            symbolTV.setText(stock.getSymbol());
            TextView nameTV = root.findViewById(R.id.name_field);
            nameTV.setText(stock.getName());
            TextView nameBig = root.findViewById(R.id.name_big);
            nameBig.setText(stock.getName());
            TextView priceTV = root.findViewById(R.id.latest_price_field);
            priceTV.setText((new Anzeige()).makeItBeautifulEuro(stock.getPreis()));
            TextView dateTV = root.findViewById(R.id.date_field);
            if (stock.getDate() == null) {
                dateTV.setText(R.string.unbekannt);
            } else {
                dateTV.setText(stock.getDate());
            }
            TextView typeTV = root.findViewById(R.id.type_field);
            typeTV.setText(stock.getType());

            // make buttons visible
            root.findViewById(R.id.kaufen_button).setVisibility(View.VISIBLE);
            root.findViewById(R.id.portfolio_button).setVisibility(View.VISIBLE);
            showHideDeleteOrderButton();
            int numberInDepot = getFoundInDepot();
            if (numberInDepot == 0) {
                root.findViewById(R.id.verkaufen_button).setVisibility(View.GONE);
            } else {
                root.findViewById(R.id.verkaufen_button).setVisibility(View.VISIBLE);
            }
        } else {
            Requests requests = new Requests();
            try {
                requests.asyncRun(RequestsBuilder.getQuote(stock.getSymbol()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeChart() {
        Aktie stock = model.getData().getCurrentStock();
        if (stock.getChart() != null) {
            // Build the stockdatachart
            Stock chartStock = AnyChart.stock();

            Plot plot = chartStock.plot(0);

            plot.yGrid(true)
                    .yMinorGrid(true);

            Table table = Table.instantiate("x");
            table.addData(AnyChartDataBuilder.getStockChartData(stock.getChart()));
            TableMapping mapping = table.mapAs("{'high': 'high', 'low': 'low'}");

            Hilo hilo = plot.hilo(mapping);
            hilo.name("Stockinfo");

            hilo.tooltip().format("Max: {%High}&deg;<br/>Min: {%Low}&deg;");
            chartStock.tooltip().useHtml(true);

            // set the chart and make visible
            AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
            anyChartView.setChart(chartStock);
            anyChartView.setVisibility(View.VISIBLE);
        }
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

    private int getFoundInDepot() {
        int foundInDepot = 0;

        // return the number of stocks if available
        String currentSymbol = model.getData().getCurrentStock().getSymbol();
        if (model.getData().getDepot().getAktien().getValue() != null) {
            for (Aktie Stock : model.getData().getDepot().getAktien().getValue()) {
                if (Stock.getSymbol().equals(currentSymbol)) {
                    foundInDepot = Stock.getAnzahl();
                    break;
                }
            }
        }

        return foundInDepot;
    }

    @Override
    public void onPause() {
        super.onPause();
        AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setVisibility(View.VISIBLE);

    }

    private void setInputFilter(EditText limit) {
        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 6;
            final int maxDigitsAfterDecimalPoint = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        limit.setFilters(new InputFilter[]{filter});
    }
}
