package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class animalCaretaker extends AppCompatActivity {

    private boolean isHireCaretakerButtonClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniamal_caretaker);

        ImageView back = findViewById(R.id.imageVbackHome);
        Button hireButton = findViewById(R.id.Hirecaretaker);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
       /*bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_HireAnimalCareteker:
                // Handle item 1 click
                // Example: startActivity(new Intent(YourActivity.this, HireAnimalCaretakerActivity.class));
                return true;
            case R.id.menu_becomeAnimalCaretaker:
                // Handle item 2 click
                // Example: startActivity(new Intent(YourActivity.this, BecomeAnimalCaretakerActivity.class));
                return true;
            // Add more cases if you have additional items
        }
        return false;
    }*/


        hireButton.setBackgroundColor(ContextCompat.getColor(this, R.color.DarkGreen));

        hireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isHireCaretakerButtonClicked = true;
                startActivity(new Intent(animalCaretaker.this, LoginActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(animalCaretaker.this,HomeActivity.class));
            }
        });
    }
        //get method
        public boolean isHireCaretakerButtonClicked() {
            return isHireCaretakerButtonClicked;
        }

}
