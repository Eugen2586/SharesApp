package com.example.sharesapp.FunktionaleKlassen.Diagramm;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.DataPoint;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, in der Anychart Daten gebaut werden.
 */

public class AnyChartDataBuilder {

    /**
     * Baut aus gegeben DataPoint einer Aktie die Daten für einen Anychart Graphen.
     *
     * @param chartData Die DataPoints der Aktie
     * @return Die Liste der Daten für den Graphen.
     */
    public static List<DataEntry> getStockChartData(ArrayList<DataPoint> chartData) {
        List<DataEntry> anyChartData = new ArrayList<>();

        for (DataPoint dataPoint : chartData) {
            anyChartData.add(
                    new HighLowDataEntry(dataPoint.getDate(), dataPoint.getHigh(), dataPoint.getLow())
            );
        }
        return anyChartData;
    }

    /**
     * Baut aus gegeben Trades einer Aktie die Daten für einen Anychart Graphen.
     *
     * @param tradeList Die Tradedaten für den Graphen.
     * @return Die Liste der Daten für den Graphen.
     */

    public static ArrayList<DataEntry> getTradeChartData(ArrayList<Trade> tradeList) {
        ArrayList<DataEntry> dataList = new ArrayList<>();
        Model model = new Model();
        float currentMoney = model.getData().getDepot().getStartMoney();
        long firstMillis = tradeList.get(0).getMillis();
        for (Trade trade : tradeList) {
            if (trade.isKauf()) {
                currentMoney -= trade.getPreis();
            } else {
                currentMoney += trade.getPreis();
            }
            dataList.add(new ValueDataEntry(String.valueOf((trade.getMillis() - firstMillis) / 1000), currentMoney));
        }

        return dataList;
    }

    /**
     * Baut aus gegeben Kryptowährungen die Daten für einen Anychart Graphen.
     * @param crypto Die Kryptowährung
     * @return Die Liste der Daten für den Graphen.
     */

    public static ArrayList<DataEntry> getCryptoChartData(Aktie crypto) {
        ArrayList<DataEntry> dataEntries = new ArrayList<>();
        ArrayList<DataPoint> chart = crypto.getChart();
        for (DataPoint dataPoint : chart) {
            dataEntries.add(new ValueDataEntry(dataPoint.getDate(), Double.parseDouble(dataPoint.getRate())));
        }
        return dataEntries;
    }

    private static long timeStringToLong(String time) {
        if (time != null) {
            return Long.parseLong(time);
        }
        return 0;
    }
}
