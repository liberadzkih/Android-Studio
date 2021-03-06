package com.example.astroweather2app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapterWeather extends FragmentPagerAdapter {
    public ViewPagerAdapterWeather(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0)
            return LocationInfo.newInstance();
        else if(i==1)
            return WindInfo.newInstance();
        else
            return WeatherForecastInfo.newInstance();
    }

    @Override
    public int getCount() {
        return 1+1+1;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}

