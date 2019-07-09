package com.example.astroweather2app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

public class SetCityActivity extends AppCompatActivity {

    private Button setCity;
    private EditText cityValue;

    private String cityName;
    private String cityId;
    private String cityLati;
    private String cityLongi;

    private DatabaseManager databaseManager;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_city);
        sharedPreferences = getSharedPreferences("SharedpreferencesFile", Activity.MODE_PRIVATE);
        setCity = findViewById(R.id.setCity);
        cityValue = findViewById(R.id.cityValue);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        setCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCity = cityValue.getText().toString();
                if (enteredCity != null)
                    checkCity(enteredCity);
            }
        });
    }

    private void checkCity(String city) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://api.openweathermap.org/data/2.5/weather?q=")
                .append(city).append("&appid=b27c81c33339b4aa82f4037695bb9f4f");
        String url = urlBuilder.toString();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String n = response.getString("name");
                    cityName = n;
                    String id = String.valueOf(response.getInt("id"));
                    cityId = id;
                    String lati = String.valueOf(response.getJSONObject("coord").getDouble("lat"));
                    cityLati = lati;
                    String longi = String.valueOf(response.getJSONObject("coord").getDouble("lon"));
                    cityLongi = longi;

                    databaseManager.insert(cityName, cityId, cityLati, cityLongi);

                    SharedPreferences.Editor prefsEdit = sharedPreferences.edit();
                    prefsEdit.putString("SharedpreferencesCityID", cityId);
                    prefsEdit.putString("SharedpreferencesCity", cityName);
                    prefsEdit.putString("SharedpreferencesLatitude", cityLati);
                    prefsEdit.putString("SharedpreferencesLongitude", cityLongi);
                    prefsEdit.putBoolean("SharedpreferencesChanged", true);
                    prefsEdit.commit();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

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
                Toast.makeText(getApplicationContext(), "Wrong city or connection problem", Toast.LENGTH_LONG).show();
            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }
}
