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

public class MoonInfo extends Fragment {

    private MoonViewModel mvm;

    private TextView moonriseTimeValue;
    private TextView moonsetTimeValue;
    private TextView newMoonValue;
    private TextView fullMoonValue;
    private TextView moonPhaseValue;
    private TextView synodMonthValue;

    DecimalFormat df = new DecimalFormat("#.##");

    public static MoonInfo newInstance() {
        return new MoonInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvm = ViewModelProviders.of(getActivity()).get(MoonViewModel.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Zwieksz layout o dodawany fragment
        View v = inflater.inflate(R.layout.fragment_moon, container, false);

        moonriseTimeValue = v.findViewById(R.id.moonriseTimeValue);
        moonsetTimeValue = v.findViewById(R.id.moonsetTimeValue);
        newMoonValue = v.findViewById(R.id.newMoonValue);
        fullMoonValue = v.findViewById(R.id.fullMoonValue);
        moonPhaseValue = v.findViewById(R.id.moonPhaseValue);
        synodMonthValue = v.findViewById(R.id.synodMonthValue);

        AstroCalculator.MoonInfo moonInfo = mvm.getMoonInfo();

        if(moonInfo != null){
            moonriseTimeValue.setText(moonInfo.getMoonrise().toString());
            moonsetTimeValue.setText(moonInfo.getMoonset().toString());
            newMoonValue.setText(moonInfo.getNextNewMoon().toString());
            fullMoonValue.setText(moonInfo.getNextFullMoon().toString());
            moonPhaseValue.setText(String.valueOf(df.format(moonInfo.getIllumination())));
            synodMonthValue.setText(String.valueOf(df.format(moonInfo.getAge())));
        }

        return v;
    }

}