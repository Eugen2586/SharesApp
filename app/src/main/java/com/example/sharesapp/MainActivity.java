package com.example.sharesapp;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;

    TabLayout.Tab tab_uebersicht;
    TabLayout.Tab tab_statistik;
    LinearLayout fragment_loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragment_loader = findViewById(R.id.fragment_loader);
        tabs = findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeFragment(tab.getPosition());
                Intent myIntent = new Intent(MainActivity.this, DrawerActivity.class);
                MainActivity.this.startActivity(myIntent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





        }

    private void changeFragment(int position) {

        TextView test = new TextView(this);
        fragment_loader.addView(test);
        if (position == 0) {
            test.setText("Übersicht");

        }
        if (position == 1) {
            test.setText("Statistik");
        }
    }
}
