package com.example.kursw;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mid;
    TextView amount;
    TextView id;
    TextView fullname;
    RelativeLayout currencyLayout;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View itemView) {
        super(itemView);

        currencyLayout = itemView.findViewById(R.id.currencyLayout);
        this.id = itemView.findViewById(R.id.currencyID);
        this.amount = itemView.findViewById(R.id.currencyAmount);
        this.mid = itemView.findViewById(R.id.currencyMid);
        this.fullname = itemView.findViewById(R.id.currencyFullName);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
