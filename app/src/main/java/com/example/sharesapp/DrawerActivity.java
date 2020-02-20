package com.example.sharesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sharesapp.FunktionaleKlassen.JSON.LoadFromJson;
import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.REST.Range;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.intellij.lang.annotations.Flow;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import okhttp3.internal.http2.Http2Reader;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;


public class DrawerActivity extends AppCompatActivity {
    private Context context = this.getBaseContext();
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onStop() {
        super.onStop();
        //If the App Stopps we store the Data!
        try {

            SaveToJSON stj = new SaveToJSON();
            String s = stj.getJson();
            context = this.getBaseContext();
            FileOutputStream fos = context.openFileOutput("keep.dat", MODE_APPEND );
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Persistenz Stuff für die


         */
        context = this.getBaseContext();
        try{
        LoadFromJson lfj = new LoadFromJson();
        FileInputStream fis = context.openFileInput("keep.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        String st = (String) ois.readObject();
        lfj.getJson(st);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        // Initializes RequestClient and loads all symbols

        Requests req = new Requests();
        try {
            Model m = new Model();
            boolean b = m.getData().getAktienList().getValue()!= null;
            boolean a  = true;
            if(b) {
                 a = m.getData().getAktienList().getValue().size() < 2;
            }
            if( a && !b) {
                req.asyncRun(RequestsBuilder.getAllSymbolsURL());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
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
