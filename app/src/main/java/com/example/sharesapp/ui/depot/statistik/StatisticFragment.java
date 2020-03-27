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

import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.example.sharesapp.FunktionaleKlassen.Diagramm.AnyChartDataBuilder;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;

/**
 * Die Klasse, mit der die Statistik und Verlauf des Geldflusses des Users angezeigt wird.
 */
public class StatisticFragment extends Fragment {

    private View root;
    private Model model = new Model();

    /**
     * tradeObserver wird initalisiert.
     * lineChart wird gezeigt.
     *
     * @param inflater           nflates das depot Fragment
     * @param container          wird gebraucht für die inflation
     * @param savedInstanceState Nicht gebraucht.
     * @return Root.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_depot_statistik, container, false);

        initializeTradeObserver();

        showLineChart(model.getData().getTrades());

        return root;
    }

    /**
     * Initalisierung des Oberservers für die tradeList.
     */
    private void initializeTradeObserver() {
        final Observer<ArrayList<Trade>> tradesObserver = new Observer<ArrayList<Trade>>() {
            @Override
            public void onChanged(ArrayList<Trade> tradeList) {
                showLineChart(tradeList);
            }
        };
        model.getData().getTradesMutable().observe(getViewLifecycleOwner(), tradesObserver);
    }

    /**
     * Graph wird konfiguriert.
     * Daten des Graphen wird durch die tradeList berechnet.
     * Sichtbarkeit des Fillers wird durch die Länge/Existenz der tradeList bestimmt.
     *
     * @param tradeList Liste der Handel des Users.
     */
    private void showLineChart(ArrayList<Trade> tradeList) {
        // set visibility of components and set chart if trades available
        if (tradeList != null && tradeList.size() > 1) {
            // Build the stockdatachart
            Cartesian cartesian = AnyChart.line();
            cartesian.title("Eigenes Vermögen");
            cartesian.animation(true);
            cartesian.yAxis(0).title("Euro");
            cartesian.xAxis(0).title("Sek. seit erstem Kauf");

            // create data
            ArrayList<DataEntry> dataList = AnyChartDataBuilder.getTradeChartData(tradeList);

            // create a line series and set the data
            Set set = Set.instantiate();
            set.data(dataList);
            Mapping mapping = set.mapAs("{ x: 'x', value: 'value' }");
            cartesian.line(mapping);

            AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
            anyChartView.setChart(cartesian);

            // change visibility of components
            anyChartView.setVisibility(View.VISIBLE);
            root.findViewById(R.id.no_chart_view).setVisibility(View.GONE);
        } else {
            // change visibility of components
            root.findViewById(R.id.no_chart_view).setVisibility(View.VISIBLE);
            root.findViewById(R.id.any_chart_view).setVisibility(View.GONE);
        }
    }
}
