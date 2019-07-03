package com.example.astroweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private EditText frequencyEditText;

    private Button confirmButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        frequencyEditText = findViewById(R.id.frequencyEditText);

        // http://strefakodera.pl/programowanie/android-java/przechowywanie-danych-w-aplikacji-za-pomoca-sharedpreferences
        sharedPreferences = getSharedPreferences("SharedpreferencesFile", Activity.MODE_PRIVATE);
        latitudeEditText.setText(sharedPreferences.getString("SharedpreferencesLatitude", ""));
        longitudeEditText.setText(sharedPreferences.getString("SharedpreferencesLongitude", ""));
        frequencyEditText.setText(sharedPreferences.getString("SharedpreferencesFrequency", ""));

        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    SharedPreferences.Editor prefsEdit = sharedPreferences.edit();
                    prefsEdit.putString("SharedpreferencesLatitude", latitudeEditText.getText().toString());
                    prefsEdit.putString("SharedpreferencesLongitude", longitudeEditText.getText().toString());
                    prefsEdit.putString("SharedpreferencesFrequency", frequencyEditText.getText().toString());
                    prefsEdit.commit();

                    setResult(Activity.RESULT_OK, new Intent());
                    finish();
                }
            }
        });
    }

    private boolean validate(){
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();
        String frequency = frequencyEditText.getText().toString();

        if(latitude.isEmpty() || longitude.isEmpty() || frequency.isEmpty()) {
            //System.out.println("Atleast one field is empty");
            Toast.makeText(this, "Atleast one field is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        double latitudeDouble, longitudeDouble, frequencyDouble;

        try {
            latitudeDouble = Double.parseDouble(latitude);
            longitudeDouble = Double.parseDouble(longitude);
            frequencyDouble = Double.parseDouble(frequency);
        } catch (NumberFormatException e){
            //System.out.println("Wrong number in latitude or longitude");
            Toast.makeText(this, "Wrong number in latitude or longitude", Toast.LENGTH_LONG).show();
            return false;
        }

        if(frequencyDouble <= 0 ){
            //System.out.println("Frequency value have to be more than 0");
            Toast.makeText(this, "Frequency value have to be more than 0", Toast.LENGTH_LONG).show();
            return false;
        }

        if(latitudeDouble < -90 || latitudeDouble > 90 || longitudeDouble > 180 || longitudeDouble < -180) {
            //System.out.println("Latitude have to be between <-90, 90>");
            //System.out.println("Longitude have to be between <-180, 180>");
            Toast.makeText(this, "Latitude have to be between <-90, 90>\n" +
                    "Longitude have to be between <-180, 180>", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}