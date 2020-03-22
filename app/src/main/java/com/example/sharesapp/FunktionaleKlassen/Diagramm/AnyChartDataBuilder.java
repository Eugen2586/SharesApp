package com.example.sharesapp.FunktionaleKlassen.Diagramm;

import com.anychart.anychart.DataEntry;
import com.anychart.anychart.HighLowDataEntry;
import com.anychart.anychart.ValueDataEntry;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class AnyChartDataBuilder {

    public static List<DataEntry> getStockChartData(ArrayList<DataPoint> chartData) {
        List<DataEntry> anyChartData = new ArrayList<>();

        for (DataPoint dataPoint : chartData) {
            anyChartData.add(
                    new HighLowDataEntry(dataPoint.getDate(),dataPoint.getLow(), dataPoint.getHigh())
            );
        }
        return anyChartData;
    }
}
