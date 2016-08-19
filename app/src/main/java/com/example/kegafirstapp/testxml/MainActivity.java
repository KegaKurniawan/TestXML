package com.example.kegafirstapp.testxml;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends Activity {
    private TextView txt1,txt2,txt3;
    //private String url = "http://jogjabay.cloudapp.net:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd./Codeunit/Test";
    private String url = "http://192.168.1.107:81/android_test/testing.xml";
    private TestPullParser obj;
    private final String NAMESPACE = "http://tempuri.org/";
    private final String URL2 = "http://localhost:55821/Service.asmx";
    private final String SOAP_ACTION = "http://tempuri.org/primaKe";
    private final String METHOD_NAME = "primaKe";
    Button btnView,btnJdom,btnTemperature,btnSql,btnCustomer,btnChart,btnLineChart, btnQChart,btnQrCode,btnTopCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnView = (Button) findViewById(R.id.btnView);
        btnJdom = (Button) findViewById(R.id.btnToJdom);
        txt1 = (TextView) findViewById(R.id.textView_ksoap);
        txt2 = (TextView) findViewById(R.id.textView2);
        txt3 = (TextView) findViewById(R.id.textView3);
        btnTemperature = (Button) findViewById(R.id.bTemp);
        btnSql = (Button) findViewById(R.id.bSqlConnect);
        btnCustomer = (Button) findViewById(R.id.bCustomer);
        btnChart = (Button) findViewById(R.id.bChart);
        btnLineChart = (Button) findViewById(R.id.bLineChart);
        btnQChart = (Button) findViewById(R.id.bQChart);
        btnQrCode = (Button) findViewById(R.id.bQRCode);
        btnTopCustomer = (Button) findViewById(R.id.bTopCust);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj=new TestPullParser(url);
                obj.fetchXML();
                //txt1.setText(url);

                //Initialize soap request + add parameters
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("n",txt3.getText().toString());

                //Declare the version of the SOAP request
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;


                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL2);

                    //this is the actual part that will call the webservice
                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject)envelope.bodyIn;

                    if(result != null)
                    {
                        //Get the first property and change the label text
                        txt3.setText(result.getProperty(0).toString());
                        System.out.println("Data Sudah di simpan");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No Response",Toast.LENGTH_LONG).show();
                    }
                    System.out.println("============ Sudah bisa login =================");
                    Toast.makeText(getApplicationContext(), "====== Dapat Login =====",Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.toString()+" ============ Belum Bisa Login ===============");
                    Toast.makeText(getApplicationContext(), "======== Belum Bisa Login =============",Toast.LENGTH_LONG).show();
                }


                while (obj.parsingComplete);
               // txt1.setText(obj.getInputData());
                txt2.setText(obj.getNama());
                txt1.setText(obj.getKode());


            }
        });


        /*ToursPullParser parser  = new ToursPullParser();
        List<Tour> tours = parser.parseXML(this);

        ArrayAdapter<Tour> adapter = new ArrayAdapter<Tour>(this,android.R.layout.simple_list_item_1,tours);
        setListAdapter(adapter);*/

        btnJdom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Ksoap.class);
                startActivity(i);
            }
        });

        btnTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Temperature.class);
                startActivity(i);
            }
        });

        btnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SqlConnect.class);
                startActivity(i);
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CustomerList.class);
                startActivity(i);
            }
        });

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TestChart.class);
                startActivity(i);
            }
        });

        btnLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Chart_Line.class);
                startActivity(i);
            }
        });

        btnQChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SelectQuarter.class);
                startActivity(i);
            }
        });

        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TestQrCode.class);
                startActivity(i);
            }
        });

        btnTopCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TopCustomerChart.class);
                startActivity(i);
            }
        });
    }
}
