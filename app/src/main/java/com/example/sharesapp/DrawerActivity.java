package com.example.sharesapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.REST.Range;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.internal.http2.Http2Reader;


public class DrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializes RequestClient and loads all symbols
        Requests req = new Requests();
        try {
            req.asyncRun(RequestsBuilder.getAllSymbolsURL());
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
                R.id.nav_depot, R.id.nav_aktien, R.id.nav_historie,
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
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
