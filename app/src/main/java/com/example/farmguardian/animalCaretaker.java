package com.example.farmguardian;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class animalCaretaker extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    Fragment fragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniamal_caretaker);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(this);


        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.DarkGreen));
        FrameLayout fragmentContainer = findViewById(R.id.fragment_container);


        openAnimalCaretakerFragment();
        TextView backhome = findViewById(R.id.Animalcaretakerbackhome);


        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(animalCaretaker.this,HomeActivity.class));
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

        if (item.getItemId() == R.id.menu_HireAnimalCareteker) {
            openAnimalCaretakerFragment();

            return true;

        }
        if(item.getItemId() == R.id.menu_becomeAnimalCaretaker)
        {
            fragment =  new ACaretakerProfile2Fragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;



        }else if (item.getItemId() == R.id.menu_RequestDetails) {

        }
        return false;
    }
    private void openAnimalCaretakerFragment() {
        Fragment fragment = new HireAnimalCaretakerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
