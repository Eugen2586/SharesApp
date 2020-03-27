package com.example.sharesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.FunktionaleKlassen.Services.NotificationOnlyStickyService;
import com.example.sharesapp.FunktionaleKlassen.Services.RequestDataService;
import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.google.android.material.navigation.NavigationView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class DrawerActivity extends AppCompatActivity {
    SharedPreferences prefs;
    public Context context = this.getBaseContext();
    private AppBarConfiguration mAppBarConfiguration;
    private Model model = new Model();
    private Requests requests = new Requests();
    private MediaPlayer backgroundMediaPlayer;

    @Override
    protected void onStop() {
        try {
            prefs = getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            prefs.edit().clear();
            SharedPreferences.Editor editor = prefs.edit();
            new SaveToJSON(editor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // stop RequestDataService
        Intent requestIntent = new Intent(this, RequestDataService.class);
        stopService(requestIntent);

        super.onStop();
        //If the App Stops we store the Data!
    }

    @Override
    protected void onDestroy() {
        // start NotificationOnlyStickyService
        Intent notificationOnlyIntent = new Intent(this, NotificationOnlyStickyService.class);
        startService(notificationOnlyIntent);

        // remove MediaPlayer
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
            backgroundMediaPlayer.release();
            backgroundMediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // observer for buy and sell order
        initializeObserverForOrderLists();

        super.onCreate(savedInstanceState);
        /* Do persistance Stuff.


         */
        try {
            String s = null;
            prefs = getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            JSONParser parser = new JSONParser();
            try {
                s = prefs.getString("AktienSymbole", null);
                if (s != null && !s.isEmpty()) {
                    new RequestSymbol(s);
                }
            } catch (Exception e) {

            }
            s = null;
            try {
                s = prefs.getString("Depot", null);
                if (s != null && !s.isEmpty()) {
                    new Model().getData().getDepot().setAktienImDepot(aktienList(s));
                }
            } catch (Exception e) {

            }
            s = null;
            try {
                Float f = prefs.getFloat("Geldwert", (float) -10000000.0);
                if (f < -10000000.0 + 1) {
                    f = (float) Constants.MONEY;
                }
                new Model().getData().getDepot().setGeldwert(f);
            } catch (Exception e) {
                String t = e.getMessage();
            }
            s = null;
            try {
                int schwierigkeitsgrad = prefs.getInt("Schwierigkeitsgrad", 0);
                if (schwierigkeitsgrad != 0) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(schwierigkeitsgrad);
                    new Model().getData().getDepot().applySchwierigkeitsgrad(false);
                } else {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(schwierigkeitsgrad);
                }

            } catch (Exception e) {
                String t = e.getMessage();
            }
            s = null;
            try {
                int kaufCounter = prefs.getInt("KaufCounter", 0);
                new Model().getData().getDepot().setKaufCounter(kaufCounter);
            }catch(Exception e){
                String t = e.getMessage();
            }
            s = null;
            try {
                int verkaufCounter = prefs.getInt("VerkaufCounter", 0);
                new Model().getData().getDepot().setVerkaufCounter(verkaufCounter);

            }catch(Exception e){
                String t = e.getMessage();
            }
            s = null;
            try {
                int resetCounter = prefs.getInt("ResetCounter", 0);
                new Model().getData().setResetCounter(resetCounter);

            }catch(Exception e){
                String t = e.getMessage();
            }
            s = null;
            try {
                s = prefs.getString("Portfolioliste", null);
                if (s != null && !s.isEmpty()) {
                    new Model().getData().setPortfolioList(aktienList(s));
                }
            } catch (Exception e) {

            }
            s = null;
            try {
                s = prefs.getString("Tr", null);
                if (s != null && s.length() > 2) {
                    new Model().getData().setTradelist(getTradeListe(s));
                }
            } catch (Exception e) {

            }
            s = null;
            try {
                s = prefs.getString("BuyList", null);
                if (s != null && s.length() > 2) {
                    new Model().getData();
                }
            } catch (Exception e) {


            }
            s = null;
            try {
                s = prefs.getString("SellList", null);
                if (s != null && s.length() > 2) {
                    new Model().getData();
                }
            } catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Initializes RequestClient and loads all symbols only if the Persisenz doesn't work.

        Requests req = new Requests();
        try {
            req.asyncRun(RequestsBuilder.getAllSymbolsURL());
            req.asyncRun(RequestsBuilder.getCryptoSymbolsUrl());
//            req.asyncRun(RequestsBuilder.getCurrencyRatesUrl());
        } catch (Exception ignored) {

        }

        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_depot, R.id.nav_search, R.id.nav_aktien, R.id.nav_order,
                R.id.nav_historie, R.id.nav_erfolge, R.id.nav_newgame)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // initialize Media Player https://www.bensound.com/royalty-free-music/track/creative-minds
        backgroundMediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        backgroundMediaPlayer.setLooping(true);
        backgroundMediaPlayer.start();
    }

    private ArrayList<Trade> getTradeListe(String st) throws ParseException {
        Trade tr = null;
        Aktie ak = null;
        ArrayList<Trade> akl = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(st);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
            ak = new Aktie();
            org.json.simple.JSONObject json = (JSONObject) t;
            try {
                //Aktie:
                ak.setAnzahl((Integer) json.get("menge"));
            } catch (Exception e) {

            }
            try {
                ak.setExchange((String) json.get("exchange"));
            } catch (Exception e) {

            }
            try {
                ak.setSymbol((String) json.get("symbol"));
            } catch (Exception e) {

            }
            try {
                ak.setName((String) json.get("name"));
            } catch (Exception e) {

            }
            try {
                ak.setDate((String) json.get("date"));
            } catch (Exception e) {

            }
            try {
                ak.setType((String) json.get("type"));
            } catch (Exception e) {

            }
            try {
                ak.setRegion((String) json.get("region"));
            } catch (Exception e) {

            }
            try {
                ak.setCurrency((String) json.get("RequestCurrency"));
            }catch(Exception e){

            }
            try {
                ak.setCurrency((String) json.get("currency"));
            } catch (Exception e) {

            }
            try {
                ak.setEnabled((String) json.get("enabled"));
            } catch (Exception e) {

            }
            try {
                ak.setPrice(Float.parseFloat((String) json.get("preis")));
            } catch (Exception e) {

            }
            try {
                ak.setAnzahl(Integer.parseInt((String) json.get("anzahlak")));
            } catch (Exception e) {

            }
            try {
                ak.setChange(Float.parseFloat((String) json.get("change")));
            } catch (Exception e) {

            }
            try {
                ak.setAnzahl(Integer.parseInt((String) json.get("anzahl")));
            } catch (Exception e) {

            }
            try {
                ak.setDate((String) json.get("date"));
            } catch (Exception e) {

            }
            try {
                ak.setPrice(Float.parseFloat((String) json.get("preisi")));
            } catch (Exception e) {

            }
            try {
                ak.setRegion(json.get("region").toString());
            } catch (Exception e) {

            }
            Boolean isKauf = null;
            try {
                isKauf = Boolean.parseBoolean(json.get("iskauf").toString());
            } catch (Exception e) {

            }
            int anzahlImTrade = Integer.parseInt((String) json.get("anzahl"));
            float ft = Float.parseFloat(json.get("preis").toString());
            long millis = Long.parseLong(Objects.requireNonNull(json.get("date")).toString());
            // (Aktie aktie, int anzahl, boolean kauf, float preis, Date date)
            tr = new Trade(ak, anzahlImTrade, isKauf, ft, millis, ak.getSymbol());
            new Model().getData().addTrade(tr);
            akl.add(tr);
        }
        return akl;
    }

    private Aktie getAktie(String st) throws ParseException {
        Aktie ak = null;
        ArrayList akl = new ArrayList();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject json = (JSONObject) parser.parse(st);
        ak = new Aktie();
        try {
            ak.setSymbol(json.get("symbol").toString());

        } catch (Exception e) {

        }
        try {
            ak.setExchange(json.get("exchange").toString());
        } catch (Exception e) {

        }
        try {
            ak.setName(json.get("name").toString());
        } catch (Exception e) {

        }
        try {
            ak.setDate(json.get("date").toString());
        } catch (Exception e) {

        }
        try {
            ak.setType(json.get("type").toString());
        } catch (Exception e) {

        }
        try {
            ak.setRegion(json.get("region").toString());
        } catch (Exception e) {

        }
        try {
            ak.setCurrency(json.get("RequestCurrency").toString());
        } catch (Exception e) {

        }
        try {
            ak.setEnabled(json.get("isEnabled").toString());
        } catch (Exception e) {

        }
        try {
            ak.setChange(Float.parseFloat(json.get("change").toString()));
        } catch (Exception e) {

        }
        try {
            ak.setAnzahl(Integer.parseInt(json.get("anzahl").toString()));
        } catch (Exception e) {

        }
        try {
            ak.setPrice(Float.parseFloat((json.get("preis").toString())));
        } catch (Exception e) {

        }
        return ak;
    }

    private ArrayList aktienList(String st) throws ParseException {
        Aktie ak = null;
        ArrayList akl = new ArrayList();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONArray jsonar = (JSONArray) parser.parse(st);
        jsonar.isEmpty();
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
            ak = new Aktie();
            org.json.simple.JSONObject json = (JSONObject) t;
            try {
                ak.setSymbol(json.get("symbol").toString());

            } catch (Exception e) {

            }
            try {
                ak.setExchange(json.get("exchange").toString());
            } catch (Exception e) {

            }
            try {
                ak.setName(json.get("name").toString());
            } catch (Exception e) {

            }
            try {
                ak.setDate(json.get("date").toString());
            } catch (Exception e) {

            }
            try {
                ak.setType(json.get("type").toString());
            } catch (Exception e) {

            }
            try {
                ak.setRegion(json.get("region").toString());
            } catch (Exception e) {

            }
            try {
                ak.setCurrency(json.get("RequestCurrency").toString());
            } catch (Exception e) {

            }
            try {
                ak.setEnabled(json.get("isEnabled").toString());
            } catch (Exception e) {

            }
            try {
                ak.setChange(Float.parseFloat(json.get("change").toString()));
            } catch (Exception e) {

            }
            try {
                ak.setAnzahl(Integer.parseInt(json.get("anzahl").toString()));
            } catch (Exception e) {

            }
            try {
                ak.setPrice(Float.parseFloat((json.get("preis").toString())));
            } catch (Exception e) {

            }
            akl.add(ak);
        }
        return akl;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.search_stock);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                // send SearchRequest to server
                try {
                    requests.asyncRun(RequestsBuilder.getSearchURL(query));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                model.getData().setCurrentSearchString(query);
                Navigation.findNavController(DrawerActivity.this, R.id.nav_host_fragment).
                        navigate(R.id.nav_search);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (backgroundMediaPlayer.isPlaying()) {
            backgroundMediaPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        // stop NotificationOnlyStickyService
        Intent notificationOnlyIntent = new Intent(this, NotificationOnlyStickyService.class);
        stopService(notificationOnlyIntent);

        // start RequestDataService
        Intent requestIntent = new Intent(this, RequestDataService.class);
        startService(requestIntent);

        if (backgroundMediaPlayer != null && !backgroundMediaPlayer.isPlaying()) {
            backgroundMediaPlayer.start();
        }
        super.onResume();
    }

    private void initializeObserverForOrderLists() {
        // Observer for stockList
        final Observer<ArrayList<Aktie>> stockObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> stockList) {
                model.getData().checkOrderListsForBuyingSelling(stockList);
            }
        };
        model.getData().getAktienList().observe(this, stockObserver);
    }
}
