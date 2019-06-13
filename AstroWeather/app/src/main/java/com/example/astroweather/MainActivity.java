package com.example.astroweather;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private Button optionButton;

    private TextView longitudeValue;
    private TextView latitudeValue;
    private TextView timeValue;
    private TextView refreshFrequencyValue;

    private ViewPager viewPager;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd\nHH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
