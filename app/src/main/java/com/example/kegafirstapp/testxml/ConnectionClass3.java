package com.example.kegafirstapp.testxml;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Kega on 9/7/2016.
 */
public class ConnectionClass3 {
    //String ip = "jogjabay.cloudapp.net";
    String ip = "172.16.4.124";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    //String classs = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    //String db = "Demo Database NAV (9-0)";
    //String db = "android_test";
    String db = "Demo Database NAV (9-0)";
    String un = "sa";
    //String password = "Guru2013";
    String password = "Naturals2014";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";

            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
