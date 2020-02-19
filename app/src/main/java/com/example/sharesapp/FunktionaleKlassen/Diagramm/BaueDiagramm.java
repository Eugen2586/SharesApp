
package com.example.sharesapp.FunktionaleKlassen.Diagramm;


import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineRadarDataSet;

import org.achartengine.chart.LineChart;

import java.util.ArrayList;

public class BaueDiagramm {
    /*
    Um diese klasse zu benutzen müssen folgende requierments erfüllt werden!
    Creating the View
        For using a LineChart, BarChart, ScatterChart, CandleStickChart, PieChart, BubbleChart or RadarChart , define it in .xml:

    <com.github.mikephil.charting.charts.LineChart
            android:id="@+id/chart"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
            And then retrieve it from your Activity, Fragment or whatever:

    // in this example, a LineChart is initialized from xml
            LineChart chart = (LineChart) findViewById(R.id.chart);
            or create it in code (and then add it to a layout):

    // programmatically create a LineChart
            LineChart chart = new LineChart(Context);

    // get a layout defined in xml
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
            rl.add(chart); // add the programmatically created chart

     */

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
        LineDataSet d = new LineDataSet(values ,"DataSheet " + (z+1)  );
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