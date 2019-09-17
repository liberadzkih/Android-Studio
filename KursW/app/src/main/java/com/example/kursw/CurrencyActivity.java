package com.example.kursw;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CurrencyActivity extends AppCompatActivity {

    TextView currencyID, currencyFullName;
    TextView kursSredniValue, kursSprzedazyValue, kursKupnaValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        ActionBar actionBar = getSupportActionBar();

        currencyID = findViewById(R.id.currencyID);
        currencyFullName = findViewById(R.id.currencyFullName);
        kursSredniValue = findViewById(R.id.kursSredniValue);
        kursSprzedazyValue = findViewById(R.id.kursSprzedazyValue);
        kursKupnaValue = findViewById(R.id.kursKupnaValue);

        Intent intent = getIntent();

        String title = intent.getStringExtra("iTitle");
        String id = intent.getStringExtra("iCurrencyID");
        String fullname = intent.getStringExtra("iCurrencyFullName");
        System.out.println(fullname);
        System.out.println(id);
        String kursKupna = intent.getStringExtra("iKursKupna");
        String kursSprzedazy = intent.getStringExtra("iKursSprzedazy");
        String kursSredni = intent.getStringExtra("iKursSredni");

        actionBar.setTitle(title);

        currencyID.setText(id);
        currencyFullName.setText(fullname);
        kursSredniValue.setText(kursSredni);
        kursSprzedazyValue.setText(kursSprzedazy);
        kursKupnaValue.setText(kursKupna);


    }
}
