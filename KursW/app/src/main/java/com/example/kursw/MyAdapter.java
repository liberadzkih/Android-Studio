package com.example.kursw;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    //https://www.youtube.com/watch?v=oq_xGMN0mRE&t=
    //https://www.youtube.com/watch?v=7r50UlnarpU&t=1007s
    Context c;
    ArrayList<Model> models;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.fullname.setText(models.get(i).getCurrencyFullName());
        myHolder.id.setText(models.get(i).getCurrencyID());
        myHolder.amount.setText(1 + " " + models.get(i).getCurrencyID());
        myHolder.mid.setText(models.get(i).getKursSredniValue());


        myHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String title = models.get(position).getCurrencyFullName();
                String currencyID = models.get(position).getCurrencyID();
                String currencyFullName = models.get(position).getCurrencyFullName();
                String kursKupna = models.get(position).getKursKupnaValue();
                String kursSprzedazy = models.get(position).getKursSprzedazyValue();
                String kursSredni = models.get(position).getKursSredniValue();

                Intent intent = new Intent(c, CurrencyActivity.class);
                intent.putExtra("iTitle", title);
                intent.putExtra("iCurrencyID", currencyID);
                intent.putExtra("iCurrencyFullName", currencyFullName);
                intent.putExtra("iKursKupna", kursKupna);
                intent.putExtra("iKursSprzedazy", kursSprzedazy);
                intent.putExtra("iKursSredni", kursSredni);

                c.startActivity(intent);
            }
        });

        /*myHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
