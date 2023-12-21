package com.example.farmguardian;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FirstPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Button hireButton = findViewById(R.id.Hirecaretaker);
        hireButton.setBackgroundColor(ContextCompat.getColor(this, R.color.DarkGreen));
    }
}
