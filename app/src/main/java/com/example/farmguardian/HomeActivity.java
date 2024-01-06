package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView logOff = findViewById(R.id.Logout);
        CardView  animalcaretaker = findViewById(R.id.animalcaretaker);
        CardView news = findViewById(R.id.News);
        CardView EquipmentSharing = findViewById(R.id.EquipmentSharing);

        EquipmentSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EquipmentSharingActivity.class));
            }
        });
        animalcaretaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, animalCaretaker.class));
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,NewsActivity.class));
            }
        });

        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
    }
}