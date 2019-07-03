package com.example.astroweather2app;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button optionsButton;

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment moonInfo;
    private Fragment sunInfo;

    boolean determineDeviceIsTablet;

    private SharedPreferences sharedPreferences;

    private TextView longitudeValue;
    private TextView latitudeValue;
    private TextView timeValue;
    private TextView refreshFrequencyValue;

    private ViewPager viewPager;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private Handler handler;
    private Runnable timeRunnable;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd\nHH:mm:ss");
    private Runnable sunInfoAndMoonInfoRunnable;
    private Calendar calendar;
    private SunViewModel svm;
    private MoonViewModel mvm;

    private static final int ACTIVITY_REQUEST_CODE = 1;
    private static final int DELAY_TIME_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svm = ViewModelProviders.of(this).get(SunViewModel.class);
        mvm = ViewModelProviders.of(this).get(MoonViewModel.class);

        determineDeviceIsTablet = getResources().getBoolean(R.bool.determineDeviceIsTablet);
        if(determineDeviceIsTablet){
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
            moonInfo = MoonInfo.newInstance();
            sunInfo = SunInfo.newInstance();
            fragmentTransaction.replace(R.id.fragment1, sunInfo);
            fragmentTransaction.replace(R.id.fragment2, moonInfo);
            fragmentTransaction.commit();
        } else {
            viewPager = findViewById(R.id.viewPager);
            fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragmentPagerAdapter);
        }

        timeValue = findViewById(R.id.timeValue);
        latitudeValue = findViewById(R.id.latitudeValue);
        longitudeValue = findViewById(R.id.longitudeValue);
        refreshFrequencyValue = findViewById(R.id.refreshFrequencyValue);

        sharedPreferences = getSharedPreferences("SharedpreferencesFile", Activity.MODE_PRIVATE);
        latitudeValue.setText(sharedPreferences.getString("SharedpreferencesLatitude", ""));
        longitudeValue.setText(sharedPreferences.getString("SharedpreferencesLongitude", ""));
        refreshFrequencyValue.setText(sharedPreferences.getString("SharedpreferencesFrequency", ""));

        optionsButton = findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });

        handler = new Handler();
        timeRunnable = new Runnable(){
            @Override
            public void run() {
                String time = sdf.format(new Date());
                timeValue.setText(time);
                handler.postDelayed(this, DELAY_TIME_MILLIS);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ACTIVITY_REQUEST_CODE == requestCode && resultCode == Activity.RESULT_OK) {
            latitudeValue.setText(sharedPreferences.getString("SharedpreferencesLatitude", ""));
            longitudeValue.setText(sharedPreferences.getString("SharedpreferencesLongitude", ""));
            refreshFrequencyValue.setText(sharedPreferences.getString("SharedpreferencesFrequency", ""));

            sunInfoAndMoonInfoRunnable = new Runnable() {

                @Override
                public void run() {
                    String latitude = latitudeValue.getText().toString();
                    String longitude = longitudeValue.getText().toString();
                    String frequency = refreshFrequencyValue.getText().toString();

                    calendar = calendar.getInstance();
                    Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_LONG).show();

                    AstroDateTime adt = new AstroDateTime(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,
                            calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND), calendar.get(Calendar.ZONE_OFFSET) ,true);

                    AstroCalculator astroCalc = new AstroCalculator(adt, new AstroCalculator.Location(Double.valueOf(latitude),
                            Double.valueOf(longitude)));

                    AstroCalculator.SunInfo suninfo = astroCalc.getSunInfo();
                    AstroCalculator.MoonInfo mooninfo = astroCalc.getMoonInfo();
                    mvm.setMoonInfo(mooninfo);
                    svm.setSunInfo(suninfo);

                    if(determineDeviceIsTablet){
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        moonInfo = MoonInfo.newInstance();
                        sunInfo = SunInfo.newInstance();
                        fragmentTransaction.replace(R.id.fragment1, sunInfo);
                        fragmentTransaction.replace(R.id.fragment2, moonInfo);
                        fragmentTransaction.commit();
                    } else {
                        viewPager.getAdapter().notifyDataSetChanged();
                    }

                    handler.postDelayed(this,Integer.valueOf(frequency)*DELAY_TIME_MILLIS);


                }
            };
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(getApplicationContext(), "Main window", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        handler.post(sunInfoAndMoonInfoRunnable);
        handler.post(timeRunnable);
        Toast.makeText(getApplicationContext(), "Resumed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(sunInfoAndMoonInfoRunnable);
        handler.removeCallbacks(timeRunnable);
        Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_LONG).show();
    }
}
