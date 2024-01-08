package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class animalCaretaker extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniamal_caretaker);

        ImageView back = findViewById(R.id.imageVbackHome);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(animalCaretaker.this, HomeActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_layout, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_HireAnimalCareteker) {
            Intent intent = new Intent(animalCaretaker.this, HireAnimalCaretakerActivity.class);
            startActivity(intent);
           return true;

        } else if(item.getItemId() == R.id.menu_becomeAnimalCaretaker) {
            // Handle item 2 click
            startActivity(new Intent(animalCaretaker.this,ACaretakerProfile2.class));
            return true;
        }

        return false;
    }


}
