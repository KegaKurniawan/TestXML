package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 7/25/2016.
 */
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class NavJava extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_java);

        //ohneNTLM();
        WithNTLM();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // TOLOONG DICARI SALAHNYA KENAPA
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // TOLOONG DICARI SALAHNYA KENAPA
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    public void WithNTLM()
    {
        String namespace = "urn:microsoft-dynamics-schemas/codeunit/Test";
        String url = "http://jogjabay.cloudapp.net:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd./Codeunit/Test";
        String soap_action = "urn:microsoft-dynamics-schemas/codeunit/Test:InputData";
        String method_name = "InputData";

        String great;
        try {
            SoapObject request = new SoapObject(namespace, method_name);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport(url, "Administrator", "Guru2013", "billy", "");
            ntlm.debug = true;
            ntlm.setCredentials(url, "Administrator", "Guru2013", "billy", "");
            ntlm.call(soap_action, envelope); // Receive Error here!
            SoapObject result = (SoapObject) envelope.getResponse();

            great = result.toString();
            System.out.println(great);

        } catch (Exception e) {
            e.printStackTrace();
            great = e.toString();
            System.out.println(great);
        }
    }


}
