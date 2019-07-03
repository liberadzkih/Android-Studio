package com.example.astroweather2app;

import android.arch.lifecycle.ViewModel;

public class WindViewModel extends ViewModel {

    private String force;
    private String direction;
    private String humidity;
    private String visibility;

    public WindViewModel(String force, String direction, String humidity, String visibility) {
        this.force = force;
        this.direction = direction;
        this.humidity = humidity;
        this.visibility = visibility;
    }

    public WindViewModel() {
    }

    public String getForce() {
        return force;
    }

    public void setWindForce(String windForce) {
        this.force = windForce;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String windDirection) {
        this.direction = windDirection;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
