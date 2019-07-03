package com.example.astroweather;

import android.arch.lifecycle.ViewModel;

import com.astrocalculator.AstroCalculator;

public class SunViewModel extends ViewModel {

    private AstroCalculator.SunInfo sunInfo;

    public AstroCalculator.SunInfo getSunInfo() {
        return sunInfo;
    }

    public void setSunInfo(AstroCalculator.SunInfo sunInfo) {
        this.sunInfo = sunInfo;
    }
}
