package com.example.astroweather2app;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;

import java.text.DecimalFormat;

public class SunInfo extends Fragment {

    private SunViewModel svm;

    private TextView sunriseTimeValue;
    private TextView sunriseAzimuthValue;
    private TextView sunsetTimeValue;
    private TextView sunsetAzimuthValue;
    private TextView civilDawnValue;
    private TextView civilTwilightValue;

    DecimalFormat df = new DecimalFormat("#.##");

    public static SunInfo newInstance() {
        //SunInfo sunInfo = new SunInfo();
        return new SunInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        svm = ViewModelProviders.of(getActivity()).get(SunViewModel.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sun, container, false);

        sunriseTimeValue = v.findViewById(R.id.sunriseTimeValue);
        sunriseAzimuthValue = v.findViewById(R.id.sunriseAzimuthValue);
        sunsetTimeValue = v.findViewById(R.id.sunsetTimeValue);
        sunsetAzimuthValue = v.findViewById(R.id.sunsetAzimuthValue);
        civilDawnValue = v.findViewById(R.id.civilDawnValue);
        civilTwilightValue = v.findViewById(R.id.civilTwilightValue);

        AstroCalculator.SunInfo sunInfo = svm.getSunInfo();

        if(sunInfo != null){
            sunriseTimeValue.setText(sunInfo.getSunrise().toString());
            sunriseAzimuthValue.setText(String.valueOf(df.format(sunInfo.getAzimuthRise())));
            sunsetTimeValue.setText(sunInfo.getSunset().toString());
            sunsetAzimuthValue.setText(String.valueOf(df.format(sunInfo.getAzimuthSet())));
            civilDawnValue.setText(sunInfo.getTwilightMorning().toString());
            civilTwilightValue.setText(sunInfo.getTwilightEvening().toString());
        }

        return v;
    }

}
