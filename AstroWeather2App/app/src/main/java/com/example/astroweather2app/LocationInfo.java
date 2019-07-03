package com.example.astroweather2app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationInfo extends Fragment {

    private LocationViewModel lvm;

    private TextView temperatureValue;
    private TextView pressureValue;
    private TextView weatherValue;

    public static LocationInfo newInstance() {
        return new LocationInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lvm = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_location, container, false);

        temperatureValue = v.findViewById(R.id.temperatureValue);
        pressureValue = v.findViewById(R.id.pressureValue);
        weatherValue = v.findViewById(R.id.weatherValue);

        temperatureValue.setText(lvm.getTemp() != null ? lvm.getTemp() : "");
        pressureValue.setText(lvm.getPressure() != null ? lvm.getPressure() : "");
        weatherValue.setText(lvm.getWeather() != null ? lvm.getWeather() : "");

        return v;

    }

}
