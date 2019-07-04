package com.example.astroweather2app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherForecastInfo extends Fragment {
    private WeatherForecastViewModel wfvm;

    private TextView day1;
    private TextView day1temp;
    private TextView day1rainfall;
    private ImageView day1image;

    private TextView day2;
    private TextView day2temp;
    private TextView day2rainfall;
    private ImageView day2image;

    private TextView day3;
    private TextView day3temp;
    private TextView day3rainfall;
    private ImageView day3image;

    private TextView day4;
    private TextView day4temp;
    private TextView day4rainfall;
    private ImageView day4image;

    public static WeatherForecastInfo newInstance() {
        return new WeatherForecastInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wfvm = ViewModelProviders.of(getActivity()).get(WeatherForecastViewModel.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        day1 = v.findViewById(R.id.day1);
        day1temp = v.findViewById(R.id.day1temp);
        day1rainfall = v.findViewById(R.id.day1rainfall);
        day1image = v.findViewById(R.id.day1image);

        day2 = v.findViewById(R.id.day2);
        day2temp = v.findViewById(R.id.day2temp);
        day2rainfall = v.findViewById(R.id.day2rainfall);
        day2image = v.findViewById(R.id.day2image);

        day3 = v.findViewById(R.id.day3);
        day3temp = v.findViewById(R.id.day3temp);
        day3rainfall = v.findViewById(R.id.day3rainfall);
        day3image = v.findViewById(R.id.day3image);

        day4 = v.findViewById(R.id.day4);
        day4temp = v.findViewById(R.id.day4temp);
        day4rainfall = v.findViewById(R.id.day4rainfall);
        day4image = v.findViewById(R.id.day4image);

        String icon;

        day1.setText(wfvm.getDay1() != null ? wfvm.getDay1() : "");
        day1temp.setText(wfvm.getDay1Temp() != null ? wfvm.getDay1Temp() : "");
        day1rainfall.setText(wfvm.getDay1Rainfall() != null ? wfvm.getDay1Rainfall() : "");
        icon = wfvm.getDay1image();
        if(icon != null){
            day1image.setImageResource(getContext().getResources().getIdentifier(icon, "drawable", getContext().getPackageName()));
        }

        day2.setText(wfvm.getDay2() != null ? wfvm.getDay2() : "");
        day2temp.setText(wfvm.getDay2Temp() != null ? wfvm.getDay2Temp() : "");
        day2rainfall.setText(wfvm.getDay2Rainfall() != null ? wfvm.getDay2Rainfall() : "");
        icon = wfvm.getDay2image();
        if(icon != null){
            day2image.setImageResource(getContext().getResources().getIdentifier(icon, "drawable", getContext().getPackageName()));
        }

        day3.setText(wfvm.getDay3() != null ? wfvm.getDay3() : "");
        day3temp.setText(wfvm.getDay3Temp() != null ? wfvm.getDay3Temp() : "");
        day3rainfall.setText(wfvm.getDay3Rainfall() != null ? wfvm.getDay3Rainfall() : "");
        icon = wfvm.getDay3image();
        if(icon != null){
            day3image.setImageResource(getContext().getResources().getIdentifier(icon, "drawable", getContext().getPackageName()));
        }

        day4.setText(wfvm.getDay4() != null ? wfvm.getDay4() : "");
        day4temp.setText(wfvm.getDay4Temp() != null ? wfvm.getDay4Temp() : "");
        day4rainfall.setText(wfvm.getDay4Rainfall() != null ? wfvm.getDay4Rainfall() : "");
        icon = wfvm.getDay4image();
        if(icon != null){
            day4image.setImageResource(getContext().getResources().getIdentifier(icon, "drawable", getContext().getPackageName()));
        }
        return v;

    }
}
