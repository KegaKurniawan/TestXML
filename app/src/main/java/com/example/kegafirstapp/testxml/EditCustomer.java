package com.example.kegafirstapp.testxml;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Kega on 8/10/2016.
 */
public class EditCustomer extends Activity {

    String passedVar,nama;
    ConnectionClass connectionClass;
    PreparedStatement stmt;
    ResultSet rs;
    String query;
    EditText textNo, textName, textAddress, textCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_customer);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        passedVar = getIntent().getStringExtra(CustomerList.ID_EXTRA);
        nama = passedVar;

        connectionClass = new ConnectionClass();
        textNo = (EditText) findViewById(R.id.txtNo);
        textName = (EditText) findViewById(R.id.txtName);
        textAddress = (EditText) findViewById(R.id.txtAddress);
        textCity = (EditText) findViewById(R.id.txtCity);

        //INI BUAT DISABLE EDIT TEXT
        //textNo.setEnabled(false);

        textName.setText(passedVar);

        try{
            query = "SELECT * FROM [dbo].[CRONUS International Ltd_$Customer] WHERE [Name] = '"+nama+"'";
            System.out.println(query);
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("No_");
                textNo.setText(id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            query = "SELECT * FROM [dbo].[CRONUS International Ltd_$Customer] WHERE [Name] = '"+nama+"'";
            System.out.println(query);
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("Address");
                textAddress.setText(id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            query = "SELECT * FROM [dbo].[CRONUS International Ltd_$Customer] WHERE [Name] = '"+nama+"'";
            System.out.println(query);
            Connection con = connectionClass.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("City");
                textCity.setText(id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
