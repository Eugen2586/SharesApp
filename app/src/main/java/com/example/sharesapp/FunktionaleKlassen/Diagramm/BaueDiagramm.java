
package com.example.sharesapp.FunktionaleKlassen.Diagramm;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.anychart.charts.Stock;
import com.anychart.core.stock.Plot;
import com.anychart.data.Table;
import com.anychart.data.TableMapping;
import com.anychart.enums.StockSeriesType;
import java.util.ArrayList;
import java.util.List;


public class BaueDiagramm {
    Stock stock;

    BaueDiagramm(ArrayList werte ){

        Table table = Table.instantiate("x");
        table.addData(datenaufbereiten( werte ));

        TableMapping mapping = table.mapAs("{open: 'open', high: 'high', low: 'low', close: 'close'}");

        Stock stock = AnyChart.stock();

        Plot plot = stock.plot(0);
        plot.yGrid(true)
                .xGrid(true)
                .yMinorGrid(true)
                .xMinorGrid(true);

        plot.ema(table.mapAs("{value: 'close'}"), 20d, StockSeriesType.LINE);

        plot.ohlc(mapping)
                .name("CSCO")
                .legendItem("{\n" +
                        "        iconType: 'rising-falling'\n" +
                        "      }");

        stock.scroller().ohlc(mapping);

        this.stock = stock;

    }
    public Stock getDiagramm( ){

        return stock;

    }

    public void buidDiagram(){

    }
    private List<DataEntry> datenaufbereiten( ArrayList werte ){
        //ToDo Implementiere hier die Datenaufbereitung.
        //mach die Daten zurecht
        for (Object o: werte) {
            DataEntry entry = (DataEntry) o;
            //ToDo Übertrag noch neu initialisieren.
            werte.add(new OHCLDataEntry(638380800000L,0.0825,0.0842,0.0816,0.0842));
        }
        return werte;
    }

    private class OHCLDataEntry extends HighLowDataEntry {
        OHCLDataEntry(Long x, Double open, Double high, Double low, Double close) {
            super(x, high, low);
            setValue("open", open);
            setValue("close", close);
        }
    }
}