
package com.example.sharesapp.FunktionaleKlassen.Diagramm;
/*
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
*/

import android.provider.ContactsContract;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineRadarDataSet;

import org.achartengine.chart.LineChart;

import java.util.ArrayList;

public class BaueDiagramm {

    LineChart stock;

    BaueDiagramm(ArrayList werte ){
        
        //ToDo Aktuell nur zum Anzeigen einer Aktie!
        LineChart stock;
        
        ArrayList<Entry> values = new ArrayList<>();
        int z = 0;
        for (Object o : werte) {
            double val = (double) werte.get(z);
            // ToDo gegen Z könnten hier auch Daten ersetzt werden.
            values.add(new Entry(z, (float) val));
        }
        LineDataSet d = new LineDataSet(values ,"DataSheet " + (z+1)  )
        d.setLineWidth(2.5f);
        d.setCircleRadius( 0 );
        d.setColor( 0 );
        stock.getDataset();
        this.stock = stock;

    }
    public LineChart getDiagramm( ){

        return stock;

    }



    public void buidDiagram(){

    }
    private ArrayList datenaufbereiten( ArrayList werte ){
        //ToDo Implementiere hier die Datenaufbereitung.
        //mach die Daten zurecht
        for (Object o: werte) {

            //ToDo Übertrag noch neu initialisieren.
            werte.add(null);
        }
        return werte;
    }

}