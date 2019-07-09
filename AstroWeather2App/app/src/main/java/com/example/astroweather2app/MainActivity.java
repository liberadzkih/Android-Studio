package com.example.astroweather2app;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button optionsButton;
    private Button setCityButton;
    private Button refreshButton;

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment moonInfo;
    private Fragment sunInfo;
    private Fragment weatherForecastInfo;
    private Fragment windInfo;
    private Fragment locationInfo;

    boolean determineDeviceIsTablet;

    private SharedPreferences sharedPreferences;

    private TextView longitudeValue;
    private TextView latitudeValue;
    private TextView timeValue;
    private TextView cityValue;

    String refreshFrequency;

    private ViewPager viewPager;
    private ViewPager viewPager1;
    private ViewPager viewPager2;

    private FragmentPagerAdapter fragmentPagerAdapter;
    private FragmentPagerAdapter fragmentPagerAdapterWeather;
    private FragmentPagerAdapter fragmentPagerAdapterSunMoon;

    private Handler handler;
    private Runnable timeRunnable;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd\nHH:mm:ss");
    private Runnable sunInfoAndMoonInfoRunnable;
    private Calendar calendar;

    private SunViewModel svm;
    private MoonViewModel mvm;
    private WeatherForecastViewModel wfvm;
    private WindViewModel wvm;
    private LocationViewModel lvm;

    String unit;

    private static final int ACTIVITY_REQUEST_CODE = 1;
    private static final int DELAY_TIME_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svm = ViewModelProviders.of(this).get(SunViewModel.class);
        mvm = ViewModelProviders.of(this).get(MoonViewModel.class);
        wfvm = ViewModelProviders.of(this).get(WeatherForecastViewModel.class);
        wvm = ViewModelProviders.of(this).get(WindViewModel.class);
        lvm = ViewModelProviders.of(this).get(LocationViewModel.class);

        determineDeviceIsTablet = getResources().getBoolean(R.bool.determineDeviceIsTablet);

        if (determineDeviceIsTablet) {
            viewPager1 = findViewById(R.id.viewPager1);
            fragmentPagerAdapterWeather = new ViewPagerAdapterWeather(getSupportFragmentManager());
            viewPager1.setAdapter(fragmentPagerAdapterWeather);

            viewPager2 = findViewById(R.id.viewPager2);
            fragmentPagerAdapterSunMoon = new ViewPagerAdapterSunMoon(getSupportFragmentManager());
            viewPager2.setAdapter(fragmentPagerAdapterSunMoon);
        } else {
            viewPager = findViewById(R.id.viewPager);
            fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragmentPagerAdapter);
        }

        timeValue = findViewById(R.id.timeValue);
        latitudeValue = findViewById(R.id.latitudeValue);
        longitudeValue = findViewById(R.id.longitudeValue);
        cityValue = findViewById(R.id.cityValue);

        sharedPreferences = getSharedPreferences("SharedpreferencesFile", Activity.MODE_PRIVATE);
        latitudeValue.setText(sharedPreferences.getString("SharedpreferencesLatitude", ""));
        longitudeValue.setText(sharedPreferences.getString("SharedpreferencesLongitude", ""));
        refreshFrequency = sharedPreferences.getString("SharedpreferencesFrequency", "");
        unit = sharedPreferences.getString("SharedpreferencesUnit", "Metric");
        cityValue.setText(sharedPreferences.getString("SharedpreferencesCity", ""));

        optionsButton = findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });

        setCityButton = findViewById(R.id.setCityButton);
        setCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetCityActivity.class);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE+1);
            }
        });
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        handler = new Handler();
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                String time = sdf.format(new Date());
                timeValue.setText(time);
                handler.postDelayed(this, DELAY_TIME_MILLIS);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ACTIVITY_REQUEST_CODE == requestCode && resultCode == Activity.RESULT_OK) {
            latitudeValue.setText(sharedPreferences.getString("SharedpreferencesLatitude", ""));
            longitudeValue.setText(sharedPreferences.getString("SharedpreferencesLongitude", ""));
            refreshFrequency = sharedPreferences.getString("SharedpreferencesFrequency", "");
            cityValue.setText(sharedPreferences.getString("SharedpreferencesCity", ""));

            sunInfoAndMoonInfoRunnable = new Runnable() {

                @Override
                public void run() {
                    String latitude = latitudeValue.getText().toString();
                    String longitude = longitudeValue.getText().toString();

                    calendar = calendar.getInstance();
                    Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_LONG).show();

                    AstroDateTime adt = new AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND), calendar.get(Calendar.ZONE_OFFSET), true);

                    AstroCalculator astroCalc = new AstroCalculator(adt, new AstroCalculator.Location(Double.valueOf(latitude),
                            Double.valueOf(longitude)));

                    AstroCalculator.SunInfo suninfo = astroCalc.getSunInfo();
                    AstroCalculator.MoonInfo mooninfo = astroCalc.getMoonInfo();
                    mvm.setMoonInfo(mooninfo);
                    svm.setSunInfo(suninfo);

                    if (determineDeviceIsTablet) {
                        viewPager1.getAdapter().notifyDataSetChanged();
                        viewPager2.getAdapter().notifyDataSetChanged();
                    } else {
                        viewPager.getAdapter().notifyDataSetChanged();
                    }

                    handler.postDelayed(this, Integer.valueOf(refreshFrequency) * DELAY_TIME_MILLIS);


                }
            };

            String city = sharedPreferences.getString("SharedpreferencesCity","");
            String unit = sharedPreferences.getString("SharedpreferencesUnit", "");
            if (isConnectedToNetwork() && city!=null) {
                checkTodayWeather(city, unit);
            } else {
                Toast.makeText(getApplicationContext(), "No network connection. \nWeather data may be outdated.", Toast.LENGTH_LONG).show();
            }
        }
    }
/*
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(getApplicationContext(), "Main window", Toast.LENGTH_LONG).show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(sunInfoAndMoonInfoRunnable);
        handler.post(timeRunnable);
        Toast.makeText(getApplicationContext(), "Resumed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(sunInfoAndMoonInfoRunnable);
        handler.removeCallbacks(timeRunnable);
        Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_LONG).show();
    }

    //https://blog.creativesdk.com/2016/01/checking-network-status-on-android/
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void checkTodayWeather(String city, String unit) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.openweathermap.org/data/2.5/weather?q=").append(city)
                .append("&appid=b27c81c33339b4aa82f4037695bb9f4f&units=").append(unit);
        String url = stringBuilder.toString();

        JsonObjectRequest jor_weather = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    JSONObject wind = response.getJSONObject("wind");
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject jsonobject = weather.getJSONObject(0);
                    String temp = String.valueOf((int) main.getDouble("temp"));
                    String pressure = String.valueOf(main.getDouble("pressure"));
                    String humidity = String.valueOf(main.getDouble("humidity"));
                    String speed = String.valueOf(wind.getDouble("speed"));

                    String deg = String.valueOf(Double.isNaN(wind.optDouble("deg", Double.NaN)) ? "no data" : wind.optDouble("deg", Double.NaN));
                    String visibility = response.getString("visibility");
                    String description = jsonobject.getString("description");

                    String image = "icon" + jsonobject.getString("icon");

                    lvm.setTemp(temp + "°C");
                    lvm.setWeather(description);
                    lvm.setPressure(pressure);
                    lvm.setIcon(image);

                    wvm.setWindForce(speed);
                    wvm.setDirection(deg);
                    wvm.setHumidity(humidity);
                    wvm.setVisibility(visibility);

                    if (determineDeviceIsTablet) {
                        viewPager1.getAdapter().notifyDataSetChanged();
                        viewPager2.getAdapter().notifyDataSetChanged();
                    } else {
                        viewPager.getAdapter().notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");
                    } else if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");
                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");
                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    } else if (error instanceof ParseError) {
                        System.out.println("ParseError");
                    }
                } catch (Exception e) {
                }
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        );

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("http://api.openweathermap.org/data/2.5/forecast?q=").append(city)
                .append("&appid=b27c81c33339b4aa82f4037695bb9f4f&units=").append(unit);
        String url2 = stringBuilder2.toString();

        JsonObjectRequest jor_forecast = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
                    Calendar c = Calendar.getInstance();
                    JSONArray array = response.getJSONArray("list");

                    JSONObject main_object = array.getJSONObject(12).getJSONObject("main");
                    String temp = String.valueOf((int) main_object.getDouble("temp"));
                    JSONObject weather_object = array.getJSONObject(12).getJSONArray("weather").getJSONObject(0);
                    String rainfall = weather_object.getString("description");
                    String image = "icon" + weather_object.getString("icon");
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    wfvm.setDay1(sdf2.format(c.getTime()));
                    wfvm.setDay1Temp(temp + "°C");
                    wfvm.setDay1Rainfall(rainfall);
                    wfvm.setDay1image(image);

                    main_object = array.getJSONObject(20).getJSONObject("main");
                    temp = String.valueOf((int) main_object.getDouble("temp"));
                    weather_object = array.getJSONObject(20).getJSONArray("weather").getJSONObject(0);
                    rainfall = weather_object.getString("description");
                    image = "icon" + weather_object.getString("icon");
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    wfvm.setDay2(sdf2.format(c.getTime()));
                    wfvm.setDay2Temp(temp + "°C");
                    wfvm.setDay2Rainfall(rainfall);
                    wfvm.setDay2image(image);

                    main_object = array.getJSONObject(28).getJSONObject("main");
                    temp = String.valueOf((int) main_object.getDouble("temp"));
                    weather_object = array.getJSONObject(28).getJSONArray("weather").getJSONObject(0);
                    rainfall = weather_object.getString("description");
                    image = "icon" + weather_object.getString("icon");
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    wfvm.setDay3(sdf2.format(c.getTime()));
                    wfvm.setDay3Temp(temp + "°C");
                    wfvm.setDay3Rainfall(rainfall);
                    wfvm.setDay3image(image);

                    main_object = array.getJSONObject(36).getJSONObject("main");
                    temp = String.valueOf((int) main_object.getDouble("temp"));
                    weather_object = array.getJSONObject(36).getJSONArray("weather").getJSONObject(0);
                    rainfall = weather_object.getString("description");
                    image = "icon" + weather_object.getString("icon");
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    wfvm.setDay4(sdf2.format(c.getTime()));
                    wfvm.setDay4Temp(temp + "°C");
                    wfvm.setDay4Rainfall(rainfall);
                    wfvm.setDay4image(image);

                    if (determineDeviceIsTablet) {
                        viewPager1.getAdapter().notifyDataSetChanged();
                        viewPager2.getAdapter().notifyDataSetChanged();
                    } else {
                        viewPager.getAdapter().notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");
                    } else if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");
                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");
                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    } else if (error instanceof ParseError) {
                        System.out.println("ParseError");
                    }
                } catch (Exception e) {
                }
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue q = Volley.newRequestQueue(this);
        q.add(jor_weather);
        q.add(jor_forecast);
        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
    }
}
