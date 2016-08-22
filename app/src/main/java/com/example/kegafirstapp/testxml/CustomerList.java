package com.example.kegafirstapp.testxml;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kega on 8/10/2016.
 */
public class CustomerList extends ListActivity{
    ConnectionClass connectionClass;
    ListView customerlist;
    PreparedStatement stmt;
    ResultSet rs;
    String query;
    ProgressBar pbar;
    public final static String ID_EXTRA = "com.example.kegafirstapp.testxml.CustomerList._ID";
    private static final String TAG_NAMA = "Nama";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        connectionClass = new ConnectionClass();
        //customerlist = (ListView) findViewById(R.id.customer_list);
        pbar = (ProgressBar) findViewById(R.id.pbar);

        new FillList().execute();

        customerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = ((TextView)view.findViewById(R.id.list_text)).getText().toString();
                //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
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

    public class FillList extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            customerlist = getListView();
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
            ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

            try{
                query = "SELECT [Name] FROM [dbo].[CRONUS International Ltd_$Customer]";
                Connection con = connectionClass.CONN();
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();
                while (rs.next()){
                    String id = rs.getString("Name");
                    HashMap<String, String> map = new HashMap<String, String>();
                    // adding each child node to HashMap key => value
                    map.put(TAG_NAMA, id);
                    data.add(map);
                }
                String[] array = data.toArray(new String[0]);
                return data;
            }catch(Exception e){
                e.printStackTrace();
            };
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList data) {
            pbar.setVisibility(View.GONE);
            ListAdapter NoCoreAdapter = new SimpleAdapter(CustomerList.this, data,
                    R.layout.custom_list, new String[]{TAG_NAMA}, new int[]{R.id.list_text} );
            setListAdapter(NoCoreAdapter);
        }
    }
}
