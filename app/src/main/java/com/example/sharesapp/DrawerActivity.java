package com.example.sharesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
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
import java.sql.Date;
import java.util.ArrayList;


public class DrawerActivity extends AppCompatActivity {
    SharedPreferences prefs;
    private Context context = this.getBaseContext();
    private AppBarConfiguration mAppBarConfiguration;
    private Model model = new Model();
    private Requests requests = new Requests();
    @Override
    protected void onStop() {
        try {
            prefs = getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            prefs.edit().clear();
            SharedPreferences.Editor editor = prefs.edit();
            new SaveToJSON( editor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
        //If the App Stopps we store the Data!

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Do persistance Stuff.


         */
        try{
            String s = null;
            prefs = getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            JSONParser parser = new JSONParser();
            try{
            s = prefs.getString("AktienSymbole", null);
            if(s != null && !s.isEmpty()) {
                new RequestSymbol(s);
            }
            }catch(Exception e){

            }
            s = null;
            try {
                s = prefs.getString("Depot", null);
                if (s != null && !s.isEmpty()) {
                    new Model().getData().getDepot().setAktienImDepot(aktienList(s));
                }
            }catch(Exception e){

            }
            s = null;
            try {
                Float f = prefs.getFloat("Geldwert", (float) -10000000.0);
                if( f < -10000000.0+1 ){
                    f = (float) Constants.MONEY;
                }
                new Model().getData().getDepot().setGeldwert(f);
            }catch(Exception e){
                String t = e.getMessage();
            }
            s = null;
            try {
                s = prefs.getString("Portfolioliste", null);
                if (s != null && !s.isEmpty()) {
                    new Model().getData().setPortfolioList(aktienList(s));
                }
            }catch(Exception e){

            }
            s = null;
            try {
                s = prefs.getString("Tr", null);
                if(s != null && s.length() > 2) {
                    new Model().getData().setTradelist(getTradeListe(s));
                }
            }catch(Exception e){
                System.out.print(e.getMessage());
                String g = new String();
                System.out.print(s.charAt(265));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        // Initializes RequestClient and loads all symbols only if the Persisenz doesn't work.

        Requests req = new Requests();
        try {
            req.asyncRun(RequestsBuilder.getAllSymbolsURL());
        }catch(Exception e){

        }

        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view != null) {
//                    Navigation.findNavController(getCallingActivity(), R.id.fragment_).navigateUp();
//                }
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_depot, R.id.nav_search, R.id.nav_aktien, R.id.nav_historie,
                R.id.nav_erfolge, R.id.nav_newgame)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Initializes RequestClient
        //Requests req = new Requests();
        //String s = null;
        //try {
        //    s =  req.run(RequestsBuilder.getAllSymbolsURL());
        //    RequestSymbol regs = new RequestSymbol(s);
        //    ArrayList a = regs.getAk();
//
  //      } catch (Exception e) {
//
  //      }
    }

    private ArrayList<Trade> getTradeListe(String st) throws ParseException {
        Trade tr = null;
        Aktie ak = null;
        ArrayList<Trade> akl = null;
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(st);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
            ak = new Aktie();
            org.json.simple.JSONObject json = (JSONObject) t;
            ak = getAktie(json.get("aktie").toString());
            try {
                ak.setType(json.get("date").toString());
            } catch (Exception e) {

            }
            try {
                ak.setRegion(json.get("preis").toString());
            } catch (Exception e) {

            }
            Boolean isKauf = null;
            try {
                isKauf = Boolean.parseBoolean(json.get("iskauf").toString());
            } catch (Exception e) {

            }
            tr = new Trade(ak, Integer.parseInt(json.get("anzahl").toString()), isKauf ,Float.parseFloat(json.get("Preis").toString()), Date.valueOf(json.get("Date").toString()));
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
            ak.setCurrency(json.get("currency").toString());
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
        try{
            ak.setPreis(Float.parseFloat((json.get("preis").toString())));
        }
        catch(Exception e){

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
                ak.setCurrency(json.get("currency").toString());
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
            try{
                ak.setPreis(Float.parseFloat((json.get("preis").toString())));
            }
            catch(Exception e){

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
                if( ! searchView.isIconified()) {
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
}
