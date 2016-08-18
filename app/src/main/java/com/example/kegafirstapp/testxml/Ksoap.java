package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 7/22/2016.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Ksoap extends Activity {

    private final String NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/Test";
    private final String URL = "http://jogjabay.cloudapp.net:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd./Codeunit/Test";
    private final String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/Test:InputData";
    private final String METHOD_NAME = "InputData";
    /*String NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/TestWSEga";
    String URL = "http://192.168.1.107:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd/Codeunit/TestWSEga";
    String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/TestWSEga:InputWSEga";
    String METHOD_NAME = "InputWSEga";*/
    Button btnView,btnResult;
    TextView txt1;
    EditText Edit1, Edit2;
    final Context context = this;
    String  great;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksoap_test);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btnView = (Button) findViewById(R.id.bConnect);
        txt1 = (TextView) findViewById(R.id.textView_ksoap);
        Edit1 = (EditText) findViewById(R.id.txtField1);
        Edit2 = (EditText) findViewById(R.id.txtField2);
        btnResult = (Button) findViewById(R.id.bResult);


        btnView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {

                    SoapObject InputWSEga = new SoapObject("urn:microsoft-dynamics-schemas/Codeunit/Test", METHOD_NAME);
                    SoapObject InputWS = new SoapObject(NAMESPACE, METHOD_NAME);
                    InputWS.addProperty("kode_Negara",Edit1.getText().toString());
                    InputWS.addProperty("nama",Edit2.getText().toString());
                    InputWSEga.addSoapObject(InputWS);

                    SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope2.dotNet = true;
                    envelope2.setAddAdornments(false);
                    envelope2.encodingStyle = SoapSerializationEnvelope.XSD;
                    envelope2.setOutputSoapObject(InputWS);

                    NtlmTransport ntlm = new NtlmTransport(URL, "Administrator", "Guru2013", "", "");
                    ntlm.debug = true;
                    ntlm.setCredentials(URL, "Administrator", "Guru2013", "", "");
                    ntlm.call(SOAP_ACTION, envelope2);

                    if (envelope2.bodyIn instanceof SoapFault) {
                        String str = ((SoapFault) envelope2.bodyIn).faultstring;
                        System.out.println(str);
                        System.out.println("this is if");

                    } else {
                        SoapObject resultsRequestSOAP = (SoapObject) envelope2.bodyIn;
                        String ut = String.valueOf(resultsRequestSOAP);
                        System.out.println(String.valueOf(resultsRequestSOAP));
                        System.out.println("this is else");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Data Already Sent");
                        alertDialogBuilder
                                .setMessage("Click yes to exit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        Ksoap.this.finish();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    System.out.println("=== Try Running ===");


                } catch (Exception e) {
                    e.printStackTrace();
                    great = e.toString();
                    System.out.println(great);
                    System.out.println("=== Catch Running ===");
                }
            }
        });


        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SoapObject InputWSEga = new SoapObject("urn:microsoft-dynamics-schemas/Codeunit/TestWSEga", "SelectWSEga");
                    SoapObject InputWS = new SoapObject("urn:microsoft-dynamics-schemas/codeunit/TestWSEga", "SelectWSEga");
                    InputWS.addProperty("selectCode",Edit1.getText().toString());
                    InputWS.addProperty("selectDesc",Edit2.getText().toString());
                    InputWSEga.addSoapObject(InputWS);

                    SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope2.dotNet = true;
                    envelope2.setAddAdornments(false);
                    envelope2.encodingStyle = SoapSerializationEnvelope.XSD;
                    envelope2.setOutputSoapObject(InputWS);

                    NtlmTransport ntlm = new NtlmTransport("http://192.168.1.107:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd/Codeunit/TestWSEga", "kega", "123456", "cronus", "");
                    ntlm.debug = true;
                    ntlm.setCredentials("http://192.168.1.107:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd/Codeunit/TestWSEga", "kega", "123456", "cronus", "");
                    ntlm.call("urn:microsoft-dynamics-schemas/codeunit/TestWSEga:SelectWSEga", envelope2);
                    SoapObject resultsRequestSOAP = (SoapObject) envelope2.bodyIn;
                    System.out.println(resultsRequestSOAP.toString());
                    great = (resultsRequestSOAP.getProperty(0).toString().concat(" "+resultsRequestSOAP.getProperty(1).toString()));
                    System.out.println(great);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Data Has Been Retrieved");
                    alertDialogBuilder
                           .setMessage(great)
                           .setCancelable(false)
                           .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    System.out.println("=== Try Running ===");


                } catch (Exception e) {
                    e.printStackTrace();
                    great = e.toString();
                    System.out.println(great);
                    System.out.println("=== Catch Running ===");
                }
            }
        });
    }
}

// BUAT DOKUMENTASI
/*
String great;
                try {

                    SoapObject InputWSEga = new SoapObject("urn:microsoft-dynamics-schemas/Codeunit/TestWSEga", METHOD_NAME);
                    SoapObject InputWS = new SoapObject(NAMESPACE, METHOD_NAME);
                    InputWS.addProperty("codeWS",Edit1.getText().toString());
                    InputWS.addProperty("descWS",Edit2.getText().toString());
                    InputWSEga.addSoapObject(InputWS);

                    SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope2.dotNet = true;
                    envelope2.setAddAdornments(false);
                    envelope2.encodingStyle = SoapSerializationEnvelope.XSD;
                    envelope2.setOutputSoapObject(InputWS);

                    NtlmTransport ntlm = new NtlmTransport(URL, "kega", "123456", "cronus", "");
                    ntlm.debug = true;
                    ntlm.setCredentials(URL, "kega", "123456", "cronus", "");
                    ntlm.call(SOAP_ACTION, envelope2);

                    if (envelope2.bodyIn instanceof SoapFault) {
                        String str = ((SoapFault) envelope2.bodyIn).faultstring;
                        System.out.println(str);
                        System.out.println("this is if");

                    } else {
                        SoapObject resultsRequestSOAP = (SoapObject) envelope2.bodyIn;
                        String ut = String.valueOf(resultsRequestSOAP);
                        System.out.println(String.valueOf(resultsRequestSOAP));
                        System.out.println("this is else");
                    }

                    System.out.println("=== Try Running ===");


                } catch (Exception e) {
                    e.printStackTrace();
                    great = e.toString();
                    System.out.println(great);
                    System.out.println("=== Catch Running ===");
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Data Already Sent");
                        alertDialogBuilder
                                .setMessage("Click yes to exit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        Ksoap.this.finish();
                                    }
                                });
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
 */