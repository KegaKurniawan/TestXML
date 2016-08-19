package com.example.kegafirstapp.testxml;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kega on 8/19/2016.
 */
public class TopCustomerChart extends Activity {

    Spinner spinYear,spinTop;
    ConnectionClass connectionClass;
    PreparedStatement stmt;
    ResultSet rs;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_chart);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        spinYear = (Spinner) findViewById(R.id.YearSpin);
        spinTop = (Spinner) findViewById(R.id.TopSpin);


        spinTop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = spinYear.getSelectedItem().toString();
                String top = spinTop.getSelectedItem().toString();
                connectionClass = new ConnectionClass();
                BarChart chart = (BarChart) findViewById(R.id.chart);

                query="select "+top+"* from (SELECT a.[Customer No_],[Name],SUM(a.[Sales (LCY)])  as amount\n" +
                        "FROM [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$Cust_ Ledger Entry] a INNER JOIN [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$Customer] b \n" +
                        "ON b.No_ = a.[Customer No_] \n" +
                        "where [Posting Date] between '"+year+"/01/01' and '"+year+"/12/31'\n" +
                        "GROUP BY a.[Customer No_],b.Name)B ORDER BY B.[amount] desc";

                System.out.println(query);

                BarData data = new BarData(getCustomer(), getDataSet());

                //BIAR BISA NEGATIF MAKE INI KALO YANG 2.0.9
                YAxis leftAxis = chart.getAxisLeft();
                leftAxis.setStartAtZero(false);

                // PAKE INI BIAR START GRAFIKNYA DARI ANGKA 0
                chart.getAxisLeft().setAxisMinValue(0f);

                XAxis xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(10f);

                chart.setData(data);
                chart.setDescription("Sales");
                chart.animateXY(2000, 2000);
                chart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        try{
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            int counter = 0;
            while (rs.next()){
                int id = rs.getInt("amount");
                //Float fid = new Float(id);
                BarEntry v1 = new BarEntry(id, counter);
                valueSet1.add(v1);
                //BUAT NYARI BUG INI
                //System.out.println(v1);
                //System.out.println(counter);
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Total sales");
        barDataSet1.setColor(Color.rgb(0, 0, 155));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getCustomer() {
        ArrayList<String> xAxis = new ArrayList<>();
        try{
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("Name");
                //BUAT MOTONG STRING INI
                //String newId = id.substring(0, 3);
                xAxis.add(id);
                //BUAT NYARI BUG INI
                //System.out.println(xAxis);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return xAxis;
    }
}
