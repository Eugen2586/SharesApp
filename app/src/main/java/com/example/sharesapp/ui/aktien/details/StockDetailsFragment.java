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
import android.widget.TableRow;
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
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Range;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StockDetailsFragment extends Fragment {

    private Model model = new Model();
    private View root;
    private Button sellButton;
    private Button buyButton;
    private Button portfolioButton;
    private EditText kaufMenge;
    private EditText limitText;
    private TextView totalPrice;
    private TextView price;
    private Requests requests = new Requests();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien_details, container, false);

        // observer for aktienList and currentStock
        initializeAllObservers();

        // initialize buttons
        sellButton = root.findViewById(R.id.verkaufen_button);
        buyButton = root.findViewById(R.id.kaufen_button);
        portfolioButton = root.findViewById(R.id.portfolio_button);

        // initialize refresh for currentStock and chart
        initializeSwipeRefresh();

        // set stock details with new request if the latestPrice == 0
        setStockDetails();

        // buyButtonClick -> new request for currentStockPrice and open buyDialog
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quoteRequest();
                openBuyDialog(inflater);
            }
        });

        // portfolioButtonClick -> add to or remove from Portfolio and change Text
        portfolioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portfolioButtonClickEvent();
            }
        });

        // sellButtonClick -> new request for currentStockPrice and open sellDialog
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quoteRequest();
                openSellDialog(inflater);
            }
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setVisibility(View.VISIBLE);

    }

    private void initializeAllObservers() {
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
    }

    private void initializeSwipeRefresh() {
        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stockAndQuoteRequest();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private Order getOrderForCurrentStock(ArrayList<Order> buySellOrderList) {
        Aktie currentStock = model.getData().getCurrentStock();
        if (buySellOrderList != null) {
            for (Order order : buySellOrderList) {
                if (order.getSymbol().equals(currentStock.getSymbol())) {
                    return order;
                }
            }
        }
        return null;
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
        if (!limitText.getText().toString().isEmpty()) {
            // if starts with . or other characters, remove text
            try {
                limit = Float.parseFloat(limitText.getText().toString());
            } catch (Exception ignored) {
                limitText.setText("");
                return;
            }

        } else {
            limit = model.getData().getCurrentStock().getPreis();
            // TODO: setting Text not working
            try {
                price.setText((new Anzeige()).makeItBeautifulEuro(model.getData().getCurrentStock().getPreis()));
            } catch (Exception ignore) {

            }

        }
        if (kaufMenge.getText().toString().isEmpty()) {
            return;
        }

        float number = 0;
        if (!kaufMenge.getText().toString().isEmpty()) {
            number = Float.parseFloat(kaufMenge.getText().toString());
        }

        if (kaufen) {
            float price = limit * number * model.getData().getDepot().getProzent();
            totalPrice.setText(String.valueOf((new Anzeige()).makeItBeautifulEuro(price)));
            if (price > model.getData().getDepot().getGeldwert()) {
                totalPrice.setTextColor(Color.RED);
            } else {
                totalPrice.setTextColor(Color.DKGRAY);
            }
        } else {
            float price = limit * number * (2f - model.getData().getDepot().getProzent());
            totalPrice.setText(String.valueOf((new Anzeige()).makeItBeautifulEuro(price)));
        }
    }

    private void setStockDetails() {
        // new request if latestPrice == 0
        Aktie stock = model.getData().getCurrentStock();
        float latestPrice = stock.getPreis();
        if (latestPrice != 0.0f) {
            // fill information fields
            TextView titleView = root.findViewById(R.id.name_big);
            titleView.setText(stock.getCompanyName());

            setTextFieldIdWithString(R.id.symbol_field, stock.getSymbol());
            setTextFieldIdWithString(R.id.name_field, stock.getCompanyName());
            setTextFieldIdWithString(R.id.type_field, stock.getType());
            setTextFieldIdWithTime(R.id.date_field, stock.getLatestUpdate());
            setTextFieldIdWithPrice(R.id.latest_price_field, stock.getPreis());
            setTextFieldIdWithInt(R.id.volume_field, stock.getLatestVolume());
            setTextFieldIdWithString(R.id.open_time_field, stock.getOpen());
            setTextFieldIdWithString(R.id.close_time_field, stock.getClose());

            setTextFieldIdWithPrice(R.id.previous_close_field, stock.getPreviousClose());
            setTextFieldIdWithString(R.id.high_field, stock.getHigh());
            setTextFieldIdWithTime(R.id.high_time_field, stock.getHighTime());
            setTextFieldIdWithString(R.id.low_field, stock.getLow());
            setTextFieldIdWithTime(R.id.low_time_field, stock.getLowTime());
            setTextFieldIdWithPrice(R.id.week_high_field, stock.getWeek52High());
            setTextFieldIdWithPrice(R.id.week_low_field, stock.getWeek52Low());

            // configure the Text for the portfolioButton
            setTextForPortfolioButton(portfolioButton, getFoundInPortfolio());

            // make buy sell portfolio button visible
            root.findViewById(R.id.kaufen_button).setVisibility(View.VISIBLE);
            root.findViewById(R.id.portfolio_button).setVisibility(View.VISIBLE);
            int numberInDepot = getFoundInDepot();
            if (numberInDepot == 0) {
                root.findViewById(R.id.verkaufen_button).setVisibility(View.GONE);
            } else {
                root.findViewById(R.id.verkaufen_button).setVisibility(View.VISIBLE);
            }

            // configure order information and button
            showHideEditOrderViews();
            initializeOrderButton();
        } else {
            stockAndQuoteRequest();
        }
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
                showHideEditOrderViews();
            }
        };
        model.getData().getBuyOrderList().observe(getViewLifecycleOwner(), buyOrderObserver);

        final Observer<ArrayList<Order>> sellOrderObserver = new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orderList) {
                showHideEditOrderViews();
            }
        };
        model.getData().getSellOrderList().observe(getViewLifecycleOwner(), sellOrderObserver);
    }

    private void createDeleteOrderConfirmation() {
        Context context = StockDetailsFragment.this.getContext();
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle("Achtung!");
            builder.setMessage("Sollen wirklich alle Aufträge dieser Aktie gelöscht werden?");
            builder.setPositiveButton("Ja",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(StockDetailsFragment.this.getContext(), "Alle Aufträge dieser Aktie wurden gelöscht", Toast.LENGTH_LONG).show();

                            Order buyOrder = getOrderForCurrentStock(model.getData().getBuyOrderList().getValue());
                            model.getData().removeBuyOrder(buyOrder);
                            Order sellOrder = getOrderForCurrentStock(model.getData().getSellOrderList().getValue());
                            model.getData().removeSellOrder(sellOrder);
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

    private void showHideEditOrderViews() {
        Order buyOrder = getOrderForCurrentStock(model.getData().getBuyOrderList().getValue());
        Order sellOrder = getOrderForCurrentStock(model.getData().getSellOrderList().getValue());
        if (buyOrder == null && sellOrder == null) {
            root.findViewById(R.id.order_button).setVisibility(View.GONE);
        } else {
            root.findViewById(R.id.order_button).setVisibility(View.VISIBLE);
        }

        // edit buyOrder Table
        if (buyOrder != null) {
            root.findViewById(R.id.buy_order_table_layout).setVisibility(View.VISIBLE);
            ((TextView) root.findViewById(R.id.buy_number_field)).setText(String.valueOf(buyOrder.getNumber()));
            ((TextView) root.findViewById(R.id.buy_limit_field)).setText((new Anzeige()).makeItBeautifulEuro(buyOrder.getLimit()));
        } else {
            root.findViewById(R.id.buy_order_table_layout).setVisibility(View.GONE);
        }

        // edit sellOrder Table
        if (sellOrder != null) {
            root.findViewById(R.id.sell_order_table_layout).setVisibility(View.VISIBLE);
            ((TextView) root.findViewById(R.id.sell_number_field)).setText(String.valueOf(sellOrder.getNumber()));
            ((TextView) root.findViewById(R.id.sell_limit_field)).setText((new Anzeige()).makeItBeautifulEuro(sellOrder.getLimit()));
        } else {
            root.findViewById(R.id.sell_order_table_layout).setVisibility(View.GONE);
        }
    }

    private void setTextFieldIdWithString(int id, String str) {
        TextView textView = root.findViewById(id);
        if (str == null) {
            ((TableRow) textView.getParent()).setVisibility(View.GONE);
        } else {
            ((TableRow) textView.getParent()).setVisibility(View.VISIBLE);
            textView.setText(str);
        }
    }

    private void setTextFieldIdWithPrice(int id, float price) {
        TextView textView = root.findViewById(id);
        textView.setText((new Anzeige()).makeItBeautifulEuro(price));
    }

    private void setTextFieldIdWithInt(int id, int number) {
        TextView textView = root.findViewById(id);
        if (number < 0) {
            ((TableRow) textView.getParent()).setVisibility(View.GONE);
        } else {
            ((TableRow) textView.getParent()).setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(number));
        }
    }

    private void setTextFieldIdWithTime(int id, String time) {
        TextView textView = root.findViewById(id);
        if (time != null) {
            ((TableRow) textView.getParent()).setVisibility(View.VISIBLE);
            String formatString = "dd-MM-yyyy HH:mm:ss,SSS";
            SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.GERMANY);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(Long.parseLong(time));

            textView.setText(sdf.format(calendar.getTime()));
        } else {
            ((TableRow) textView.getParent()).setVisibility(View.GONE);
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

    private void openBuyDialog(LayoutInflater inflater) {
        Context context = StockDetailsFragment.this.getContext();
        if (context != null) {
            final View buyDialogView = inflater.inflate(R.layout.buy_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            Data data = new Model().getData();
            String wert = (new Anzeige()).makeItBeautiful(data.getDepot().getGeldwert());
            TextView cash = buyDialogView.findViewById(R.id.geldwert);
            cash.setText((wert + "€"));
            if (model.getData().getCurrentStock().getPreis() != 0) {
                price = buyDialogView.findViewById(R.id.price_one);
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

            limitText = buyDialogView.findViewById(R.id.limit);
            setInputFilter(limitText);

            limitText.addTextChangedListener(new TextWatcher() {

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
                                if (!limitText.getText().toString().isEmpty()) {
                                    limit = Float.valueOf(limitText.getText().toString());
                                    limit_b = true;
                                } else {
                                    limit = model.getData().currentStock.getValue().getPreis();
                                }
                                int number = Integer.parseInt(kaufMenge.getText().toString());
                                float price = limit * number * model.getData().getDepot().getProzent();
                                if (price > model.getData().getDepot().getGeldwert()) {
                                    Toast.makeText(StockDetailsFragment.this.getContext(), "Nicht genug Geld auf dem Konto!", Toast.LENGTH_LONG).show();

                                } else {
                                    if (!limit_b) {
                                        Aktie a = model.getData().currentStock.getValue().getClone();
                                        // new Request if price == 0.0f
                                        if (a.getPreis() == 0.0f) {
                                            quoteRequest();
                                            Toast.makeText(StockDetailsFragment.this.getContext(), "Kauf nicht erfolgreich:\nDer Wert der Aktie wurde aktualisiert.", Toast.LENGTH_LONG).show();
                                        } else {
                                            a.setAnzahl(number);
                                            model.getData().getDepot().kaufeAktie(a);

                                            // Poker-Chip Sound http://soundbible.com/2204-Poker-Chips.html
                                            MediaPlayer.create(buyButton.getContext(), R.raw.poker_chips).start();

                                            sellButton.setVisibility(View.VISIBLE);
                                            Toast.makeText(StockDetailsFragment.this.getContext(), "Habe Aktien gekauft.", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        handleBuyOrder(buyDialogView);
                                    }
                                }
                            } else {
                                Toast.makeText(StockDetailsFragment.this.getContext(), "Bitte Kaufmenge eingeben.", Toast.LENGTH_LONG).show();
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

    private void handleBuyOrder(View buyDialogView) {
        EditText numberText = buyDialogView.findViewById(R.id.kaufMenge);
        EditText limitText = buyDialogView.findViewById(R.id.limit);
        int number = Integer.parseInt(numberText.getText().toString());
        float limit = Float.parseFloat(limitText.getText().toString());

        if (limit < model.getData().getCurrentStock().getPreis()) {
            // remove existing buyOrder before creating new one
            Order buyOrder = getOrderForCurrentStock(model.getData().getBuyOrderList().getValue());
            if (buyOrder != null) {
                model.getData().removeBuyOrder(buyOrder);
            }

            Order newBuyOrder = new Order(model.getData().getCurrentStock(), model.getData().getCurrentStock().getSymbol(), number, limit);
            model.getData().addBuyOrder(newBuyOrder);
            Toast.makeText(StockDetailsFragment.this.getContext(), "Kaufsauftrag wurde erstellt.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(StockDetailsFragment.this.getContext(), "Angegebenes Limit befindet sich nicht unter dem Einzelwert der Aktie.", Toast.LENGTH_LONG).show();
        }
    }

    private void portfolioButtonClickEvent() {
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

    private void openSellDialog(LayoutInflater inflater) {
        Context context = StockDetailsFragment.this.getContext();
        if (context != null) {
            final View sellDialogView = inflater.inflate(R.layout.sell_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            TextView meine_anzahl = sellDialogView.findViewById(R.id.anzahl_aktien);
            meine_anzahl.setText(String.valueOf(getFoundInDepot()));
            price = sellDialogView.findViewById(R.id.price_one);
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

            limitText = sellDialogView.findViewById(R.id.limit);
            setInputFilter(limitText);

            limitText.addTextChangedListener(new TextWatcher() {

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
                                if (!limitText.getText().toString().isEmpty()) {
                                    limit = Float.valueOf(limitText.getText().toString());
                                    limit_b = true;
                                } else {
                                    limit = model.getData().currentStock.getValue().getPreis();
                                }
                                int number = Integer.parseInt(kaufMenge.getText().toString());
                                float price = limit * number * (2f - model.getData().getDepot().getProzent());
                                if (Integer.parseInt(kaufMenge.getText().toString()) > getFoundInDepot()) {
                                    // falls will mehr verakufen als habe
                                    Toast.makeText(StockDetailsFragment.this.getContext(), "Nicht genug Aktien zu verkaufen!", Toast.LENGTH_LONG).show();

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
                                        Toast.makeText(StockDetailsFragment.this.getContext(), "Habe Aktien verkauft.", Toast.LENGTH_LONG).show();
                                    } else {
                                        handleSellOrder(sellDialogView);
                                    }
                                }
                            } else {
                                Toast.makeText(StockDetailsFragment.this.getContext(), "Bitte Kaufmenge eingeben.", Toast.LENGTH_LONG).show();

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

    private void handleSellOrder(View sellDialogView) {
        EditText numberText = sellDialogView.findViewById(R.id.verkaufMenge);
        EditText limitText = sellDialogView.findViewById(R.id.limit);
        int number = Integer.parseInt(numberText.getText().toString());
        float limit = Float.parseFloat(limitText.getText().toString());

        if (limit > model.getData().getCurrentStock().getPreis()) {
            // remove existing sellOrder before creating new one
            Order sellOrder = getOrderForCurrentStock(model.getData().getSellOrderList().getValue());
            if (sellOrder != null) {
                model.getData().removeSellOrder(sellOrder);
            }

            Order newSellOrder = new Order(model.getData().getCurrentStock(), model.getData().getCurrentStock().getSymbol(), number, limit);
            model.getData().addSellOrder(newSellOrder);
            Toast.makeText(StockDetailsFragment.this.getContext(), "Verkaufauftrag wurde erstellt.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(StockDetailsFragment.this.getContext(), "Angegebenes Limit befindet sich nicht über dem Einzelwert der Aktie.", Toast.LENGTH_LONG).show();
        }
    }

    private void quoteRequest() {
        String symbol = model.getData().getCurrentStock().getSymbol();
        try {
            requests.asyncRun(RequestsBuilder.getQuote(symbol));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stockAndQuoteRequest() {
        String symbol = model.getData().getCurrentStock().getSymbol();
        try {
            requests.asyncRun(RequestsBuilder.getQuote(symbol));
            requests.asyncRun(RequestsBuilder.getHistoricalQuotePrices(symbol, Range.oneMonth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
