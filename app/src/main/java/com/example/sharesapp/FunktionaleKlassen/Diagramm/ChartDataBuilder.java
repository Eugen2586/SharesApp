package com.example.sharesapp.FunktionaleKlassen.Diagramm;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

public class ChartDataBuilder {

    LineChartData data;

    public ChartDataBuilder(int twj) {
        data = new LineChartData();
        String[] xAxisData = getAxis(twj);
        System.out.println(xAxisData.length);
        int[] yAxisData = new int[xAxisData.length];
        for (int i = 0; i < xAxisData.length; i++) {
            yAxisData[i] = i;
        }


        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        List lines = new ArrayList();
        lines.add(line);

        for (int i = 0; i < xAxisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(xAxisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        Axis axisX = new Axis();
        axisX.setValues(axisValues);

        data.setLines(lines);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(new Axis());
    }

    public LineChartData getData() {
        return data;
    }

    private String[] getAxis(int twj) {
        Date time = GregorianCalendar.getInstance().getTime();
        String[] order;
        switch (twj) {
            case 0:
                int numberHours = 24;
                int hourIndex = time.getHours() + 1;
                String[] hours = new String[2 * numberHours];
                String hourString;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < numberHours; j++) {
                        if (j < 10) {
                            hourString = "0";
                        } else {
                            hourString = "";
                        }
                        hourString += Integer.toString(j);
                        hours[i * numberHours + j] = hourString;
                    }
                }
                order = new String[numberHours];
                for (int i = hourIndex, counter = 0; counter < numberHours; i++, counter++) {
                    order[counter] = hours[i];
                }
                return order;

            case 1:
                int numberDays = 7;
                int dayIndex = time.getDay();
                String[] days = {"Mon", "Die", "Mit", "Don", "Fre", "Sam", "Son", "Mon", "Die",
                        "Mit", "Don", "Fre", "Sam", "Son"};
                order = new String[numberDays];
                for (int i = dayIndex, counter = 0; counter < numberDays; i++, counter++) {
                    order[counter] = days[i];
                }
                return order;

            case 2:
                int numberMonths = 12;
                int monthIndex = time.getMonth() + 1;
                String[] months = {"Jan", "Feb", "Mär", "Apr", "Mai", "Juni", "Juli", "Aug", "Sept",
                        "Okt", "Nov", "Dez", "Jan", "Feb", "Mär", "Apr", "Mai", "Juni", "Juli", "Aug",
                        "Sept", "Okt", "Nov", "Dez"};
                order = new String[numberMonths];
                for (int i = monthIndex, counter = 0; counter < numberMonths; i++, counter++) {
                    order[counter] = months[i];
                }
                return order;

            default:
                System.out.println("Keine gültige Dauer übergeben");
                return null;
        }
    }
}
