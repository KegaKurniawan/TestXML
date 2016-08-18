package com.example.kegafirstapp.testxml;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kega on 8/15/2016.
 */
public class QuarterChart extends Activity {

    ConnectionClass connectionClass;
    PreparedStatement stmt;
    ResultSet rs;
    String query,query2,query3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quarter_chart);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String passedVar = getIntent().getStringExtra(SelectQuarter.ID_EXTRA);
        String nama = passedVar;

        System.out.println(nama);

        connectionClass = new ConnectionClass();
        BarChart chart = (BarChart) findViewById(R.id.chart);
        List<String> Month = new ArrayList<String>();

        if(nama.equals("Quarter 1")){
            query = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/01/01' and '2016/01/31'";
            query2 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/02/01' and '2016/02/29'";
            query3 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/03/01' and '2016/03/31'";
            Month.add("JANUARY");
            Month.add("FEBRUARY");
            Month.add("MARCH");
        }else if(nama.equals("Quarter 2")){
            query = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/04/01' and '2016/04/30'";
            query2 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/05/01' and '2016/05/31'";
            query3 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/06/01' and '2016/06/30'";
            Month.add("APRIL");
            Month.add("MAY");
            Month.add("JUNE");
        }else if(nama.equals("Quarter 3")){
            query = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/07/01' and '2016/07/31'";
            query2 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/08/01' and '2016/08/31'";
            query3 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/09/01' and '2016/09/30'";
            Month.add("JULY");
            Month.add("AUGUST");
            Month.add("SEPTEMBER");
        }else if(nama.equals("Quarter 4")){
            query = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/10/01' and '2016/10/31'";
            query2 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/11/01' and '2016/11/30'";
            query3 = "select [Amount] from [Demo Database NAV (9-0)].[dbo].[CRONUS International Ltd_$G_L Entry] where [G_L Account No_] IN (6110,6120,6130,6190,6191) and [Posting Date] between '2016/12/01' and '2016/12/31'";
            Month.add("OCTOBER");
            Month.add("NOVEMBER");
            Month.add("DECEMBER");
        }

        BarData data = new BarData(Month, getDataSet());

        //BIAR BISA NEGATIF MAKE INI KALO YANG 2.0.9
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setStartAtZero(false);

        chart.setData(data);
        chart.setDescription("Amount");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        int counter = 0;
        try{
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            System.out.println(query);
            rs = stmt.executeQuery();
            List<Integer> storageArray = new ArrayList<Integer>();
            while (rs.next()){
                int id = rs.getInt("Amount");
                System.out.println("ini angka yang diambil : "+id);
                storageArray.add(id);
            }
            System.out.println(storageArray);
            int result = 0;
            for(int i=0; i<storageArray.size(); i++){
                result += storageArray.get(i);
            }
            //result = result * -1;
            System.out.println("ini hasil resultnya : "+result);
            BarEntry v1 = new BarEntry(result, counter);
            valueSet1.add(v1);
            System.out.println("ini hasil counternya : "+counter);
            counter++;
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query2);
            System.out.println(query2);
            rs = stmt.executeQuery();
            List<Integer> storageArray = new ArrayList<Integer>();
            while (rs.next()){
                int id = rs.getInt("Amount");
                System.out.println("ini angka yang diambil : "+id);
                storageArray.add(id);
            }
            System.out.println(storageArray);
            int result = 0;
            for(int i=0; i<storageArray.size(); i++){
                result += storageArray.get(i);
            }
            result = result * -1;
            System.out.println("ini hasil resultnya : "+result);
            BarEntry v1 = new BarEntry(result, counter);
            valueSet1.add(v1);
            System.out.println("ini hasil counternya : "+counter);
            counter++;
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query3);
            System.out.println(query3);
            rs = stmt.executeQuery();
            List<Integer> storageArray = new ArrayList<Integer>();
            while (rs.next()){
                int id = rs.getInt("Amount");
                System.out.println("ini angka yang diambil : "+id);
                storageArray.add(id);
            }
            System.out.println(storageArray);
            int result = 0;
            for(int i=0; i<storageArray.size(); i++){
                result += storageArray.get(i);
            }
            //result = result * -1;
            System.out.println("ini hasil resultnya : "+result);
            BarEntry v1 = new BarEntry(result, counter);
            valueSet1.add(v1);
            System.out.println("ini hasil counternya : "+counter);
            counter++;
        }catch(Exception e){
            e.printStackTrace();
        }


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Amount");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    /*private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        return xAxis;
    }*/
}
