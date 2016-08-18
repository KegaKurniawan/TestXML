package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 7/20/2016.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class TestPullParser {

   // private String InputData = "InputData";
    private String kode = "kode";
    private String nama = "nama";
    private String urlstring =null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    public TestPullParser(String url){
        this.urlstring=url;
    }

    /*public String getInputData(){
        return InputData;
    }*/

    public String getKode(){
        return kode;
    }

    public String getNama(){
        return nama;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser){
        int event;
        String text=null;
        try{
            event=myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT){
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text=myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("kode")){
                            kode=text;
                        /*}else if(name.equals("InputData")){
                            InputData=text;*/
                        }else if(name.equals("nama")){
                            nama=text;
                        }else{}
                        break;
                }
                event=myParser.next();
            }
            parsingComplete=false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fetchXML(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url=new URL(urlstring);
                    HttpURLConnection connect=(HttpURLConnection)url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream=connect.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser= xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}