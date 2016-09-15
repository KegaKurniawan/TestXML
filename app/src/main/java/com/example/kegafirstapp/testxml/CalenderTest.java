package com.example.kegafirstapp.testxml;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;
//import com.github.sundeepk.compactcalendarview.domain.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Kega on 9/14/2016.
 */
public class CalenderTest extends ActionBarActivity {

    CompactCalendarView compactCalendarView;
    ConnectionClass4 connectionClass4;
    PreparedStatement stmt;
    ResultSet rs;
    String query;
    TextView Title;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private SimpleDateFormat MonthEvent = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat YearEvent = new SimpleDateFormat("yyyy", Locale.getDefault());
    private SimpleDateFormat DayEvent = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public final static String PASS_VAR = "com.example.kegafirstapp.testxml.CalenderTest._ID";
    //private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_calender);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Calendar Reservation");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Title = (TextView) findViewById(R.id.judul);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.calendar_test);
        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Title.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        getMonthEvent();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //Toast.makeText(CalenderTest.this, "Date : " + dateClicked.toString(), Toast.LENGTH_SHORT).show();

                String pass_date = new String(DayEvent.format(dateClicked).toString());

                Intent in = new Intent(getApplicationContext(), CustomerListCalendar.class);
                in.putExtra(PASS_VAR, String.valueOf(pass_date));
                startActivityForResult(in, 100);

                System.out.println("ini tanggal yang dipilih : "+pass_date);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                // Changes toolbar title on monthChange
                Title.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                getMonthEvent();
                System.out.println("ini ganti bulan "+dateFormatForMonth.format(firstDayOfNewMonth));

            }

        });

    }

    private void getMonthEvent(){
        connectionClass4 = new ConnectionClass4();
        String bulan = new String(MonthEvent.format(compactCalendarView.getFirstDayOfCurrentMonth()).toString());
        String tahun = new String(YearEvent.format(compactCalendarView.getFirstDayOfCurrentMonth()).toString());
        query = "SELECT [Tanggal Aktivitas]\n" +
                "FROM [JOGJA BAY - Live].[dbo].[PT_ TAMAN WISATA JOGJA$Sales Header] " +
                "where month([Tanggal Aktivitas]) = '"+bulan+"' and year([Tanggal Aktivitas]) = '"+tahun+"'";
        try{

            Connection con = connectionClass4.CONN();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()){
                String date_act = rs.getString("Tanggal Aktivitas");
                Date tanggal = null;
                tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(date_act);
                long date_act2 = tanggal.getTime();
                CalendarDayEvent ev1 = new CalendarDayEvent(date_act2, Color.GREEN);
                compactCalendarView.addEvent(ev1,false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        //Untuk refresh kalender-nya
        compactCalendarView.invalidate();
    }

}