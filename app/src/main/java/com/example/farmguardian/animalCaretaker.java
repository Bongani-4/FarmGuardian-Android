package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

            openAnimalCaretakerFragment();
            return true;

        }
        if(item.getItemId() == R.id.menu_becomeAnimalCaretaker)
        {
            BecomeCaretakerFragment();
            return true;



        }else if (item.getItemId() == R.id.menu_RequestDetails) {
            RequestDetailsFragment();

            return true;


        }
        return false;
    }

    public void loadingBAR()
    {

        ProgressBar loadingProgress = findViewById(R.id.Barprogress);
        loadingProgress.setIndeterminate(true);
        // Set the color of the ProgressBar to green
        loadingProgress.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(this, R.color.DarkGreen), android.graphics.PorterDuff.Mode.SRC_IN
        );


        loadingProgress.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide the loading ProgressBar after a delay
                loadingProgress.setVisibility(View.GONE);
            }
        }, 5000);

    }
    public void lectedCaretaker(AcaretakerModel selectedCaretaker) {
        RequestDetailsFragment requestDetailsFragment = new RequestDetailsFragment();
        requestDetailsFragment.setSelectedCaretaker(selectedCaretaker);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, requestDetailsFragment)
                .addToBackStack(null)
                .commit();
    }




    private void openAnimalCaretakerFragment() {
        // Show loading progress bar
        loadingBAR();
        ProgressBar loadingProgress = findViewById(R.id.Barprogress);

        fragment = new hirefrgament();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        //check when the fragment is added
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Fragment is added, retrieve the dataloaded variable from the fragment
                if (fragment != null) {
                    // Check if fragment is an instance of hirefrgament
                    if (fragment instanceof hirefrgament) {
                        // Cast fragment to hirefrgament and access dataloaded
                        boolean dataLoaded = ((hirefrgament) fragment).isDataLoaded();
                        // If dataloaded is true, hide the progress bar
                        if (dataLoaded) {
                            loadingProgress.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    private void BecomeCaretakerFragment() {
        // Show loading progress bar
        loadingBAR();
        fragment =  new BecomeAnimalcaretakerFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Fragment transaction is complete, hide the loading progress bar
                hideLoadingBAR();
            }
        });
    }
    private void RequestDetailsFragment() {
        // Show loading progress bar

        loadingBAR();
        fragment =  new RequestDetailsFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Fragment transaction is complete, hide the loading progress bar
                hideLoadingBAR();
            }
        });
    }
    public void onDataLoadedChanged(boolean isLoaded) {
        if (isLoaded) {
            hideLoadingBAR();
        }
    }
    public void passSelectedCaretaker(AcaretakerModel selectedCaretaker) {
        RequestDetailsFragment requestDetailsFragment = new RequestDetailsFragment();
        requestDetailsFragment.setSelectedCaretaker(selectedCaretaker);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, requestDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void hideLoadingBAR() {
        ProgressBar loadingProgress = findViewById(R.id.Barprogress);
        loadingProgress.setVisibility(View.GONE);
    }





}
