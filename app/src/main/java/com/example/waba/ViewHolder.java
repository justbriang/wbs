package com.example.waba;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout histxml;
    public TextView driverer;
    public TextView casher;
    public TextView locationer;
    public TextView dateer;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        histxml=itemView.findViewById(R.id.root);
        driverer=itemView.findViewById(R.id.driver);
        casher=itemView.findViewById(R.id.cash);
        locationer=itemView.findViewById(R.id.location);
        dateer=itemView.findViewById(R.id.date);
    }

    public void setCasher(String cash) {
        this.casher.setText(cash);
    }

    public void setDriverer(String driver) {
        this.driverer.setText(driver);
    }

    public void setLocationer(String location) {
        this.locationer.setText(location);
    }

    public void setDateer(String date) {
        this.dateer.setText(date);
    }
}
