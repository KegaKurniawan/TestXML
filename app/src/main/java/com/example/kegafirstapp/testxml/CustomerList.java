package com.example.kegafirstapp.testxml;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Kega on 8/10/2016.
 */
public class CustomerList extends Activity {
    ConnectionClass connectionClass;
    ListView customerlist;
    PreparedStatement stmt;
    ResultSet rs;
    String query;
    ProgressBar pbar;
    public final static String ID_EXTRA = "com.example.kegafirstapp.testxml.CustomerList._ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        connectionClass = new ConnectionClass();
        customerlist = (ListView) findViewById(R.id.customer_list);
        pbar = (ProgressBar) findViewById(R.id.pbar);

        FillList fillList = new FillList();
        fillList.execute("");

        customerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = (String) customerlist.getItemAtPosition(i);
                Intent in = new Intent(getApplicationContext(), EditCustomer.class);
                in.putExtra(ID_EXTRA, String.valueOf(text));
                startActivityForResult(in, 100);

            }
        });

        /*try{
            query = "SELECT [Name] FROM [dbo].[CRONUS International Ltd_$Customer]";
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rs.next()){
                String id = rs.getString("Name");
                data.add(id);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
            customerlist.setAdapter(NoCoreAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    public class FillList extends AsyncTask<String, String, ArrayList<String>> {
        ArrayList<String> z;

        @Override
        protected void onPreExecute() {

            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try{
                query = "SELECT [Name] FROM [dbo].[CRONUS International Ltd_$Customer]";
                Connection con = connectionClass.CONN();
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();
                ArrayList<String> data = new ArrayList<String>();
                while (rs.next()){
                    String id = rs.getString("Name");
                    data.add(id);
                }
                String[] array = data.toArray(new String[0]);
                return data;
            }catch(Exception e){
                e.printStackTrace();
            }
            return z;
        }

        @Override
        protected void onPostExecute(ArrayList data) {
            pbar.setVisibility(View.GONE);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(CustomerList.this,android.R.layout.simple_list_item_1,data);
            customerlist.setAdapter(NoCoreAdapter);
        }
    }
}
