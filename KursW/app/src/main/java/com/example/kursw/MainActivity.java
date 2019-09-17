package com.example.kursw;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    RecyclerView mRecyclerView;
    TextView dataNotowania;

    String currency = "", code = "", mid = "", bid = "", ask = "", day = "";

    String[] cr = {"THB", "USD", "AUD", "HKD", "CAD", "NZD", "SGD", "EUR", "HUF", "CHF", "GBP", "UAH", "JPY", "CZK", "DKK", "NOK",
            "SEK", "HRK", "RON", "BGN", "TRY", "ILS", "CLP", "PHP", "MXN", "BRL", "MYR", "RUB", "IDR", "INR", "KRW", "CNY", "XDR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataNotowania = findViewById(R.id.dataNotowania);
        try {
            dataNotowania.setText(readDayFromFile("currencyA.json"));
        } catch (JSONException e) { }
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            myAdapter = new MyAdapter(this, getMyList());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRecyclerView.setAdapter(myAdapter);
    }

    //https://blog.creativesdk.com/2016/01/checking-network-status-on-android/
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private ArrayList<Model> getMyList() throws JSONException {
        ArrayList<Model> models = new ArrayList<>();
        Model m;

        if (isConnectedToNetwork()) {

            readFromJsonTablesA();
            Toast.makeText(getApplicationContext(), "Wczytano dane z sieci", Toast.LENGTH_LONG).show();

            for(String x : cr) {

                getTableFromFile(x, 'A');
                getTableFromFile(x, 'C');
                m = new Model();
                m.setCurrencyFullName(currency);
                m.setCurrencyID(code);
                m.setKursKupnaValue(ask);
                m.setKursSprzedazyValue(bid);
                m.setKursSredniValue(mid);
                models.add(m);
            }

            //READ FROM JSON WEB
        } else {
            //READ FROM FILE HERE
            Toast.makeText(getApplicationContext(), "Brak dostepu do sieci.\nDane moga byc nieaktualne", Toast.LENGTH_LONG).show();

            for(String x : cr) {
                getTableFromFile(x, 'A');
                getTableFromFile(x, 'C');
                m = new Model();
                m.setCurrencyFullName(currency);
                m.setCurrencyID(code);
                m.setKursKupnaValue(ask);
                m.setKursSprzedazyValue(bid);
                m.setKursSredniValue(mid);
                models.add(m);
            }
        }

        return models;
    }

    private void writeToFile(String file, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void getTableFromFile(String curr, char table) throws JSONException {
        String a;
        if(table == 'A')
            a = readFromFile("currencyA.json");
        else
            a = readFromFile("currencyC.json");

        JSONObject currA = new JSONObject(a);
        readTableAFromFile(currA, curr, table);
    }


    public void readFromJsonTablesA() {
        String url = "https://api.nbp.pl/api/exchangerates/tables/A/?format=json";

        JsonArrayRequest jar_table_A = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    writeToFile("currencyA.json", jsonObject.toString());
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
                        error.printStackTrace();
                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    } else if (error instanceof ParseError) {
                        System.out.println("ParseError");
                    }
                } catch (Exception e) {
                }
            }
        }
        );

        String url2 = "https://api.nbp.pl/api/exchangerates/tables/C/?format=json";

        JsonArrayRequest jar_table_C = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    writeToFile("currencyC.json", jsonObject.toString());
                    System.out.print("json object");
                    System.out.print(jsonObject.toString());
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
                        error.printStackTrace();
                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    } else if (error instanceof ParseError) {
                        System.out.println("ParseError");
                    }
                } catch (Exception e) {
                }
            }
        }
        );

        RequestQueue q = Volley.newRequestQueue(this);
        q.add(jar_table_A);
        q.add(jar_table_C);
    }

    private void readTableAFromFile(JSONObject response, String curr, char table) throws JSONException {
        if(table == 'A') {
            for (int i = 0; i < cr.length + 2; i++) {
                if (response.getJSONArray("rates").getJSONObject(i).getString("code").equals(curr)) {
                    day = response.getString("effectiveDate");
                    code = response.getJSONArray("rates").getJSONObject(i).getString("code");
                    currency = response.getJSONArray("rates").getJSONObject(i).getString("currency");
                    mid = response.getJSONArray("rates").getJSONObject(i).getString("mid");
                }
            }
        } else if(table == 'C') {
            for (int i = 0; i < 12; i++) {
                if (response.getJSONArray("rates").getJSONObject(i).getString("code").equals(curr)) {
                    ask = response.getJSONArray("rates").getJSONObject(i).getString("ask");
                    bid = response.getJSONArray("rates").getJSONObject(i).getString("bid");
                    System.out.println(ask + "  " + bid);
                    break;
                } else {
                    ask = "no data";
                    bid = "no data";
                }
            }
        }
    }

    private String readFromFile(String file) {

        String _return = "";

        try {
            InputStream inputStream = getApplicationContext().openFileInput(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                _return = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_SHORT).show();
            Log.e("login activity", "Cannot find file: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Cannot read file: " + e.toString());
        }

        return _return;
    }

    public String readDayFromFile(String filename) throws JSONException {
        String a = readFromFile(filename);
        JSONObject jsonObject = new JSONObject(a);
        String effectiveDate = jsonObject.getString("effectiveDate");
        return effectiveDate;
    }

}

