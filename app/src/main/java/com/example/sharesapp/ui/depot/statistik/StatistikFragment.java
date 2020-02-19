package com.example.sharesapp.ui.depot.statistik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sharesapp.R;
import com.google.android.material.tabs.TabLayout;

public class StatistikFragment extends Fragment {

    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_depot_statistik, container, false);

//        LineChartView lineChartView = root.findViewById(R.id.line_chart_view);
//        ChartDataBuilder ChartDataBuilder = new ChartDataBuilder();
//        lineChartView.setLineChartData(ChartDataBuilder.getData());

        tabs = root.findViewById(R.id.statistik_tab_layout);
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
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("Falsche Position");
        }
    }
}
