package com.example.astroweather2app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WindInfo extends Fragment {

    private WindViewModel windViewModel;

    private TextView forceValue;
    private TextView directionValue;
    private TextView humidityValue;
    private TextView visibilityValue;

    public static WindInfo newInstance() {
        return new WindInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        windViewModel = ViewModelProviders.of(getActivity()).get(WindViewModel.class);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wind, container, false);

        forceValue = view.findViewById(R.id.forceValue);
        directionValue = view.findViewById(R.id.directionValue);
        humidityValue = view.findViewById(R.id.humidityValue);
        visibilityValue = view.findViewById(R.id.visibilityValue);

        forceValue.setText(windViewModel.getForce() != null ? windViewModel.getForce() : "");
        directionValue.setText(windViewModel.getDirection() != null ? windViewModel.getDirection() : "");
        humidityValue.setText(windViewModel.getHumidity() != null ? windViewModel.getHumidity() : "");
        visibilityValue.setText(windViewModel.getVisibility() != null ? windViewModel.getVisibility() : "");

        return view;

    }

}