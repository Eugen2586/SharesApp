package com.example.sharesapp.FunktionaleKlassen.Diagramm;


import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.example.sharesapp.Model.FromServerClasses.DataPoint;

import java.util.ArrayList;
import java.util.List;


public class AnyChartDataBuilder {

    public static List<DataEntry> getStockChartData(ArrayList<DataPoint> chartData) {
        List<DataEntry> anyChartData = new ArrayList<>();

        for (DataPoint dataPoint : chartData) {
            anyChartData.add(
                    new HighLowDataEntry(dataPoint.getDate(), dataPoint.getHigh(), dataPoint.getLow())
            );
        }
        return anyChartData;
    }
}
