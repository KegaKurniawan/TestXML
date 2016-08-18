package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 7/25/2016.
 */
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IntegerRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Temperature extends Activity
{
    /** Called when the activity is first created. */
    private static String SOAP_ACTION1 = "http://www.w3schools.com/xml/FahrenheitToCelsius";
    private static String SOAP_ACTION2 = "http://www.w3schools.com/xml/CelsiusToFahrenheit";
    private static String NAMESPACE = "http://www.w3schools.com/xml/";
    private static String METHOD_NAME1 = "FahrenheitToCelsius";
    private static String METHOD_NAME2 = "CelsiusToFahrenheit";
    private static String URL = "http://www.w3schools.com/xml/tempconvert.asmx?WSDL";

    Button btnFar,btnCel,btnClear;
    EditText txtFar,txtCel;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btnFar = (Button)findViewById(R.id.btnFar);
        btnCel = (Button)findViewById(R.id.btnCel);
        btnClear = (Button)findViewById(R.id.btnClear);
        txtFar = (EditText)findViewById(R.id.txtFar);
        txtCel = (EditText)findViewById(R.id.txtCel);

        btnFar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Initialize soap request + add parameters
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
                System.out.println(request.toString());

                //Use this to add parameters
                //int suhu = Integer.valueOf(txtFar.getText().toString());
                String suhu = txtFar.getText().toString();
                System.out.println(suhu);
                request.addProperty("Fahrenheit", txtFar.getText().toString());
                System.out.println(request.toString());

                //Declare the version of the SOAP request
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);
                System.out.println(envelope.toString());
                envelope.dotNet = true;

                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                    //this is the actual part that will call the webservice
                    androidHttpTransport.call(SOAP_ACTION1, envelope);

                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject)envelope.bodyIn;

                    if(result != null)
                    {
                        //Get the first property and change the label text
                        txtCel.setText(result.getProperty(0).toString());
                        System.out.println(result.toString());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No Response",Toast.LENGTH_LONG).show();
                    }
                    System.out.println("============ Sudah bisa login =================");

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.toString()+" ============ Belum Bisa Login ===============");
                }
            }
        });

        btnCel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Initialize soap request + add parameters
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                //Use this to add parameters
                request.addProperty("Celsius",txtCel.getText().toString());

                //Declare the version of the SOAP request
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;

                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                    //this is the actual part that will call the webservice
                    androidHttpTransport.call(SOAP_ACTION2, envelope);

                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject)envelope.bodyIn;

                    if(result != null)
                    {
                        //Get the first property and change the label text
                        txtFar.setText(result.getProperty(0).toString());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No Response",Toast.LENGTH_LONG).show();
                    }
                    System.out.println("=========== Sudah Bisa Login =================");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.toString()+" ============ Belum Bisa Login===============");
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtCel.setText("");
                txtFar.setText("");
            }
        });
    }
}