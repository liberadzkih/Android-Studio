package com.example.astroweather2app;


import android.arch.lifecycle.ViewModel;

import com.astrocalculator.AstroCalculator;

public class MoonViewModel extends ViewModel {

    private AstroCalculator.MoonInfo moonInfo;

    public AstroCalculator.MoonInfo getMoonInfo() {
        return moonInfo;
    }

    public void setMoonInfo(AstroCalculator.MoonInfo moonInfo) {
        this.moonInfo = moonInfo;
    }
}
