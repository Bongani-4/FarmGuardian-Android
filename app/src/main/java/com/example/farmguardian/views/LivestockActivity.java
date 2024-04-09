package com.example.farmguardian.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.farmguardian.R;

public class LivestockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livestock);
        CardView back = findViewById(R.id.LIvestockBack);
        CardView Health = findViewById(R.id.LIvestockHealth);
        CardView sell = findViewById(R.id.LivestockBuyandsell);








        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LivestockActivity.this, SellLivestockActivity.class));


            }
        });




        Health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LivestockActivity.this,healthactivity.class));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LivestockActivity.this, HomeActivity.class));
            }
        });
    }

}