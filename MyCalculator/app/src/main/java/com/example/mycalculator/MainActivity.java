package com.example.mycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    /*
    TO DO
    zmiana orientacji poprzez zapisywanie do zmiennych, nie xml
     */
    Button simpleButton;
    Button advancedButton;
    Button aboutButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setText("About");
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AboutActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        simpleButton = findViewById(R.id.simpleButton);
        simpleButton.setText("Simple");
        simpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SimpleActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        advancedButton = findViewById(R.id.advancedButton);
        advancedButton.setText("Advanced");
        advancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdvancedActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        exitButton = findViewById(R.id.exitButton);
        exitButton.setText("Exit");
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
                System.exit(0);
            }
        });
    }
}
