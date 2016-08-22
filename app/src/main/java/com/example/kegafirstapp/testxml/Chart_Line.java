package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 8/12/2016.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Chart_Line extends ActionBarActivity {

    ConnectionClass2 connectionClass2;
    PreparedStatement stmt;
    ResultSet rs;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        connectionClass2 = new ConnectionClass2();

        LineChart chart = (LineChart) findViewById(R.id.chart);

        LineData data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Penjualan");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        try{
            query = "SELECT [Target penjualan] FROM [dbo].[tabel_toko]";
            Connection con = connectionClass2.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            int counter = 0;
            while (rs.next()){
                int id = rs.getInt("Target penjualan");
                //Float fid = new Float(id);
                Entry v1 = new Entry(id, counter);
                valueSet1.add(v1);
                //BUAT NYARI BUG INI
                //System.out.println(v1);
                //System.out.println(counter);
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        ArrayList<Entry> valueSet2 = new ArrayList<>();
        try{
            query = "SELECT [Penjualan] FROM [dbo].[tabel_toko]";
            Connection con = connectionClass2.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            int counter = 0;
            while (rs.next()){
                int id = rs.getInt("Penjualan");
                //Float fid = new Float(id);
                Entry v2 = new Entry(id, counter);
                valueSet2.add(v2);
                //BUAT NYARI BUG INI
                //System.out.println(v2);
                //System.out.println(counter);
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Target penjualan");
        lineDataSet1.setColor(Color.rgb(0, 155, 0));
        LineDataSet lineDataSet2 = new LineDataSet(valueSet2, "Penjualan");
        lineDataSet2.setColors(ColorTemplate.PASTEL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        try{
            query = "SELECT [Nama toko] FROM [dbo].[tabel_toko]";
            Connection con = connectionClass2.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("Nama toko");
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

