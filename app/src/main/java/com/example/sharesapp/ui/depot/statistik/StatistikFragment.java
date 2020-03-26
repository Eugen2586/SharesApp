package com.example.sharesapp.ui.depot.statistik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;

import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.example.sharesapp.FunktionaleKlassen.Diagramm.AnyChartDataBuilder;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;

public class StatistikFragment extends Fragment {

    private View root;
    private Model model = new Model();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_depot_statistik, container, false);

//        final Observer<ArrayList<Trade>> tradesObserver = new Observer<ArrayList<Trade>>() {
//            @Override
//            public void onChanged(ArrayList<Trade> tradeList) {
//                showLineChart(tradeList);
//            }
//        };
//        model.getData().getTradesMutable().observe(getViewLifecycleOwner(), tradesObserver);

        showLineChart(model.getData().getTrades());

        return root;
    }

    private void showLineChart(ArrayList<Trade> tradeList) {
        if (tradeList != null && tradeList.size() != 0) {
            // Build the stockdatachart
            Cartesian cartesian = AnyChart.line();
            cartesian.animation(true);
            cartesian.yAxis(0).title("Euro");
            cartesian.xAxis(0).title("Sek. seit erstem Kauf");

            // create data
            ArrayList<DataEntry> dataList = AnyChartDataBuilder.getTradeChartData(tradeList);

            // create a line series and set the data

            Set set = Set.instantiate();
            set.data(dataList);
            Mapping mapping = set.mapAs("{ x: 'x', value: 'value' }");
            Line line = cartesian.line(mapping);

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
}
