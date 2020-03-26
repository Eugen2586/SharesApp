package com.example.sharesapp.ui.depot.statistik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Stock;
import com.anychart.core.stock.Plot;
import com.anychart.core.stock.series.Hilo;
import com.anychart.core.stock.series.Line;
import com.anychart.data.Table;
import com.anychart.data.TableMapping;
import com.example.sharesapp.FunktionaleKlassen.Diagramm.AnyChartDataBuilder;
import com.example.sharesapp.FunktionaleKlassen.Diagramm.ChartDataBuilder;
import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.LineChartView;

public class StatistikFragment extends Fragment {

    private View root;
    private Model model = new Model();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_depot_statistik, container, false);

        final Observer<ArrayList<Trade>> tradesObserver = new Observer<ArrayList<Trade>>() {
            @Override
            public void onChanged(ArrayList<Trade> tradeList) {
                showLineChart(tradeList);
            }
        };
        model.getData().getTradesMutable().observe(getViewLifecycleOwner(), tradesObserver);

        showLineChart(model.getData().getTrades());

        return root;
    }

    private void showLineChart(ArrayList<Trade> tradeList) {
        // https://github.com/AnyChart/AnyChart-Android/blob/master/sample/src/main/java/com/anychart/sample/charts/LineChartActivity.java
        if (tradeList != null) {
            // Build the stockdatachart
            Cartesian cartesian = AnyChart.line();
            cartesian.animation(true);

            // create data
            ArrayList<DataEntry> dataList = generateDataFromTradeList(tradeList);

            // create a line series and set the data
            cartesian.line(dataList);

            // set the container id
            cartesian.container("container");

            // change visibility of components
            AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
            anyChartView.setChart(cartesian);
//            cartesian.draw(false);
            anyChartView.setVisibility(View.VISIBLE);
            root.findViewById(R.id.no_chart_view).setVisibility(View.GONE);
        } else {
            root.findViewById(R.id.no_chart_view).setVisibility(View.VISIBLE);
            root.findViewById(R.id.any_chart_view).setVisibility(View.GONE);
        }
    }

    private ArrayList<DataEntry> generateDataFromTradeList(ArrayList<Trade> tradeList) {
        ArrayList<DataEntry> dataList = new ArrayList<>();
        float currentMoney = model.getData().getDepot().getStartMoney();
        for (Trade trade : tradeList) {
            if(trade.isKauf()) {
                currentMoney -= trade.getPreis();
            } else {
                currentMoney += trade.getPreis();
            }
            dataList.add(createDataEntry(trade.getDate(), currentMoney));
        }

        return dataList;
    }

    private DataEntry createDataEntry(String x, float y) {
        DataEntry dataEntry = new DataEntry();
        dataEntry.setValue(x, y);
        return dataEntry;
    }
}
