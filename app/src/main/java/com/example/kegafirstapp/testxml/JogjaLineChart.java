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
 * Created by Kega on 9/13/2016.
 */
public class JogjaLineChart extends ActionBarActivity {

    Spinner spinYear,spinQuarter;
    ConnectionClass4 connectionClass4;
    PreparedStatement stmt;
    ResultSet rs;
    String query;
    Integer counter,sp;

    ArrayList<Integer> sales_value;
    ArrayList<String> month_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jogja_line_chart);
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
                retrieveSql();
                getTable();
                spinQuarter.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinQuarter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTable();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void retrieveSql(){
        sales_value = new ArrayList<>();
        month_list = new ArrayList<>();
        String year = spinYear.getSelectedItem().toString();
        connectionClass4 = new ConnectionClass4();
        query = "select b.Bulan, isnull(c.jumlah, 0) as jumlah from [JOGJA BAY - Live].[dbo].[Android_test_View_1] b LEFT JOIN\n" +
                "(select substring(convert(varchar,a.[Posting Date],126),6, 2) as bulan, sum (a.[Sales (LCY)]) as jumlah \n" +
                "from [JOGJA BAY - Live].[dbo].[PT_ TAMAN WISATA JOGJA$Cust_ Ledger Entry] a where year(a.[Posting Date]) = '"+year+"' " +
                "group by substring(convert(varchar,[Posting Date],126),6, 2)) c  \n" +
                "ON c.bulan = b.no order by b.no";
        try{

            Connection con = connectionClass4.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("jumlah");
                String sql_month  = rs.getString("Bulan");
                String sub_sql_month = sql_month.substring(0, 3);
                sales_value.add(id);
                month_list.add(sub_sql_month);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("ini isi value : "+sales_value);
        System.out.println("ini isi bulannya : "+month_list);
    }

    private void getTable(){
        LineChart chart = (LineChart) findViewById(R.id.LineChart);

        LineData data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Sales");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();

        getchoose();

        int count2 = 0;
        while(counter != 0){
            int fill_chart = sales_value.get(sp);
            Entry v1 = new Entry(fill_chart, count2);
            valueSet1.add(v1);
            //BUAT NYARI BUG INI
            System.out.println(v1);
            //System.out.println(counter);
            counter--;
            count2++;
            sp++;
        }

        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Sales");
        lineDataSet1.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        getchoose();

        while(counter != 0){
            String fill_month = month_list.get(sp);
            xAxis.add(fill_month);
            //BUAT NYARI BUG INI
            //System.out.println(v1);
            //System.out.println(counter);
            counter--;
            sp++;
        }

        return xAxis;
    }

    private void getchoose(){
        String choose = spinQuarter.getSelectedItem().toString();

        if(choose.equals("Quarter 1")){
            counter = 3;
            sp = 0;
        }else if(choose.equals("Quarter 2")){
            counter = 3;
            sp = 3;
        }else if(choose.equals("Quarter 3")){
            counter = 3;
            sp = 6;
        }
        else if(choose.equals("Quarter 4")){
            counter = 3;
            sp = 9;
        }
        else{
            counter = 12;
            sp = 0;
        }
    }
}
