package com.example.kegafirstapp.testxml;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Kega on 8/8/2016.
 */
public class SqlConnect extends Activity {
    ConnectionClass connectionClass;
    Spinner spinner1,spinner2,spinner3;
    PreparedStatement stmt;
    ResultSet rs;
    String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        connectionClass = new ConnectionClass();
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        try{
            query = "SELECT [No_] FROM [dbo].[CRONUS International Ltd_$Customer]";
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rs.next()){
                String id = rs.getString("No_");
                data.add(id);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
            spinner1.setAdapter(NoCoreAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no = spinner1.getSelectedItem().toString();
                //int no2 = Integer.parseInt(no);
                try{
                    query = "SELECT * FROM [dbo].[CRONUS International Ltd_$Customer] WHERE [No_] = '"+no+"'";
                    System.out.println(query);
                    Connection con = connectionClass.CONN();
                    stmt = con.prepareStatement(query);
                    rs = stmt.executeQuery();
                    ArrayList<String> data2 = new ArrayList<String>();
                    while (rs.next()){
                        String id = rs.getString("Name");
                        data2.add(id);
                    }
                    String[] array = data2.toArray(new String[0]);
                    ArrayAdapter NoCoreAdapter2 = new ArrayAdapter(SqlConnect.this,android.R.layout.simple_spinner_item,data2);
                    spinner2.setAdapter(NoCoreAdapter2);
                }catch(Exception e){
                    e.printStackTrace();
                }

                try{
                    query = "SELECT * FROM [dbo].[CRONUS International Ltd_$Customer] WHERE [No_] = '"+no+"'";
                    System.out.println(query);
                    Connection con = connectionClass.CONN();
                    stmt = con.prepareStatement(query);
                    rs = stmt.executeQuery();
                    ArrayList<String> data3 = new ArrayList<String>();
                    while (rs.next()){
                        String id = rs.getString("Search Name");
                        data3.add(id);
                    }
                    String[] array = data3.toArray(new String[0]);
                    ArrayAdapter NoCoreAdapter3 = new ArrayAdapter(SqlConnect.this,android.R.layout.simple_spinner_item,data3);
                    spinner3.setAdapter(NoCoreAdapter3);
                }catch(Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
}
