package com.example.farmguardian;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
   TextView Textsource,TextTitle;
   ImageView imageHeadline;
   CardView cardview;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        Textsource = itemView.findViewById(R.id.textSource);
        TextTitle = itemView.findViewById(R.id.textTitle);
        imageHeadline = itemView.findViewById(R.id.imgHeadLine);
        cardview = itemView.findViewById(R.id.main_container);

    }
}
