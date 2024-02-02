package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.ProgressBar;
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
            loadingBAR();

            return true;

        }
        if(item.getItemId() == R.id.menu_becomeAnimalCaretaker)
        {
            loadingBAR();
            fragment =  new ACaretakerProfile2Fragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;



        }else if (item.getItemId() == R.id.menu_RequestDetails) {

            loadingBAR();
            fragment =  new RequestDetailsFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_containerRD, fragment)
                    .commit();
            return true;


        }
        return false;
    }

    public void loadingBAR()
    {
        ProgressBar loadingProgress = findViewById(R.id.loading_progress);
        loadingProgress.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide the loading ProgressBar after a delay
                loadingProgress.setVisibility(View.GONE);
            }
        }, 2000);

    }
    private void openAnimalCaretakerFragment() {


        


        Fragment fragment = new HireAnimalCaretakerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();






        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
               // findViewById(R.id.loading_animation).setVisibility(View.GONE);
            }
        });

    }

}
