package com.example.kegafirstapp.testxml;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Kega on 9/15/2016.
 */
public class CustomerListCalendar extends ListActivity {

    String passedVar, query;
    ConnectionClass4 connectionClass4;
    PreparedStatement stmt;
    ResultSet rs;
    ListView customer_calendar;
    private static final String TAG_NAMA = "Nama";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list_calendar);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        passedVar = getIntent().getStringExtra(CalenderTest.PASS_VAR);
        //customer_calendar = (ListView) findViewById(R.id.list_calendar);

        fill_list();

    }

    private void fill_list() {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        connectionClass4 = new ConnectionClass4();
        query = "SELECT [Bill-to Name] FROM [JOGJA BAY - Live].[dbo].[PT_ TAMAN WISATA JOGJA$Sales Header] " +
                "where ([Tanggal Aktivitas]) = '"+passedVar+"'";
        try{

            Connection con = connectionClass4.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String costumer = rs.getString("Bill-to Name");
                System.out.println("ini nama customer : "+costumer);
                HashMap<String, String> map = new HashMap<String, String>();
                // adding each child node to HashMap key => value
                map.put(TAG_NAMA, costumer);
                data.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("ini isi array nya = "+data);

        ListAdapter NoCoreAdapter = new SimpleAdapter(CustomerListCalendar.this, data,
                R.layout.custom_list, new String[]{TAG_NAMA}, new int[]{R.id.list_text} );
        setListAdapter(NoCoreAdapter);
    }
}