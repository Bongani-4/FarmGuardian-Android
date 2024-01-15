package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeACActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acactivity);

        CardView logout = findViewById(R.id.Logout);
        CardView ACprofile = findViewById(R.id.ACaretakerProfile);
        CardView viewprofile = findViewById(R.id.viewProfile);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeACActivity.this,FirstPageActivity.class));
            }
        });
        ACprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeACActivity.this,ACaretakerProfile2.class));
            }
        });
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeACActivity.this,ViewACSetProfile.class));
            }
        });


    }
}