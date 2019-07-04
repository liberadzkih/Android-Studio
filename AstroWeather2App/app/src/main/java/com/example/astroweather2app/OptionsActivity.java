package com.example.astroweather2app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    private Spinner unitComboBox;
    private EditText frequencyEditText;
    private Button confirmButton;

    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        confirmButton = findViewById(R.id.confirmButton);
        unitComboBox = findViewById(R.id.unitComboBox);
        frequencyEditText = findViewById(R.id.frequencyEditText);

        // http://strefakodera.pl/programowanie/android-java/przechowywanie-danych-w-aplikacji-za-pomoca-sharedpreferences
        sharedPreferences = getSharedPreferences("SharedpreferencesFile", Activity.MODE_PRIVATE);
        frequencyEditText.setText(sharedPreferences.getString("SharedpreferencesFrequency", ""));

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    SharedPreferences.Editor prefsEdit = sharedPreferences.edit();
                    prefsEdit.putString("SharedpreferencesUnit", unitComboBox.getSelectedItem().toString());
                    prefsEdit.putString("SharedpreferencesFrequency", frequencyEditText.getText().toString());
                    prefsEdit.commit();

                    setResult(Activity.RESULT_OK, new Intent());
                    finish();
                }
            }
        });
    }

    private boolean validate(){
        String frequency = frequencyEditText.getText().toString();

        if(frequency.isEmpty()) {
            Toast.makeText(this, "Frequency is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        double frequencyDouble;

        try {
            frequencyDouble = Double.parseDouble(frequency);
        } catch (NumberFormatException e){
            Toast.makeText(this, "It's not a number", Toast.LENGTH_LONG).show();
            return false;
        }

        if(frequencyDouble <= 0 ){
            Toast.makeText(this, "Frequency value have to be more than 0", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}