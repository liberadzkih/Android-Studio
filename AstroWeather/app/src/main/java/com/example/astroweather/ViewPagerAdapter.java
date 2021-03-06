package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0)
            return SunInfo.newInstance();
        else
            return MoonInfo.newInstance();
    }

    @Override
    public int getCount() {
        return 1+1;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
