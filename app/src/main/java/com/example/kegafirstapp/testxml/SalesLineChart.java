package com.example.kegafirstapp.testxml;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Kega on 9/7/2016.
 */
public class SalesLineChart extends ActionBarActivity {

    Spinner spinYear,spinQuarter;
    ConnectionClass3 connectionClass3;
    PreparedStatement stmt;
    ResultSet rs;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_line_chart);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sales Chart");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        spinYear = (Spinner) findViewById(R.id.YearSpin);
        spinQuarter = (Spinner) findViewById(R.id.QuarterSpin);

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getValue();
                spinQuarter.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinQuarter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getValue(){
        String year = spinYear.getSelectedItem().toString();
        connectionClass3 = new ConnectionClass3();
        LineChart chart = (LineChart) findViewById(R.id.LineChart);
        query = "select b.Bulan, isnull(c.jumlah, 0) as jumlah from [Demo Database NAV (9-0)].[dbo].[Android_test_View_1] b LEFT JOIN\n" +
                "(select substring(convert(varchar,a.[Posting Date],126),6, 2) as bulan, sum (a.[Sales (LCY)]) as jumlah \n" +
                "from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$Cust_ Ledger Entry] a where year(a.[Posting Date]) = '"+year+"' " +
                "group by substring(convert(varchar,[Posting Date],126),6, 2)) c  \n" +
                "ON c.bulan = b.no order by b.no";

        LineData data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Sales");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        try{

            Connection con = connectionClass3.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            int counter = 0;
            while (rs.next()){
                int id = rs.getInt("jumlah");
                //Float fid = new Float(id);
                Entry v1 = new Entry(id, counter);
                valueSet1.add(v1);
                //BUAT NYARI BUG INI
                System.out.println(v1);
                //System.out.println(counter);
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Ini isi array yang ketiga :"+valueSet1.get(11));


        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Sales");
        lineDataSet1.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        try{
            Connection con = connectionClass3.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("Bulan");
                String sub_id = id.substring(0, 3);
                xAxis.add(sub_id);
                //BUAT NYARI BUG INI
                //System.out.println(xAxis);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return xAxis;
    }
}
