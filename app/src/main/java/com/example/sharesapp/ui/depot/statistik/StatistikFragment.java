package com.example.sharesapp.ui.depot.statistik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.FunktionaleKlassen.Diagramm.ChartDataBuilder;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.depot.DepotViewModel;
import com.google.android.material.tabs.TabLayout;


import org.achartengine.chart.LineChart;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.LineChartView;

public class StatistikFragment extends Fragment {

    private DepotViewModel depotViewModel;
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        depotViewModel =
                ViewModelProviders.of(this).get(DepotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_depot, container, false);

//        LineChartView lineChartView = root.findViewById(R.id.line_chart_view);
//        ChartDataBuilder ChartDataBuilder = new ChartDataBuilder();
//        lineChartView.setLineChartData(ChartDataBuilder.getData());

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return root;
    }

    private void changeFragment(int position) {
        switch (position) {
            case 0: break;
            case 1: break;
            case 2: break;
            default: System.out.println("Falsche Position");
        }
    }
}
