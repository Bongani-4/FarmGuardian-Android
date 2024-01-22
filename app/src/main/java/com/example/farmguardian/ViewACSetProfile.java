package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewACSetProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acset_profile);

        // Data from the previous activity
        Intent intent = getIntent();
        String fullNames = intent.getStringExtra("fullNames");
        String location = intent.getStringExtra("location");
        String contacts = intent.getStringExtra("contacts");
        String experience = intent.getStringExtra("experience");
        boolean available = intent.getBooleanExtra("available", false); // Default to false if not present

        // Updating UI with received data
        TextView textViewFullNames = findViewById(R.id.textViewFullNames1);
        TextView textViewLocation = findViewById(R.id.textViewLocation1);
        TextView textViewContacts = findViewById(R.id.textViewContacts1 );
        TextView textViewExperience = findViewById(R.id.textViewExperience1);
        TextView textViewAvailable = findViewById(R.id.textViewAvailable1);

        textViewFullNames.setText("Full Names: " + fullNames);
        textViewLocation.setText("Location: " + location);
        textViewContacts.setText("Contacts: " + contacts);
        textViewExperience.setText("Experience: " + experience);
        textViewAvailable.setText("Availability: " + (available ? "Yes" : "No"));

        // Button to navigate back to HomeACActivity
        Button btn = findViewById(R.id.buttonhome);


    }
}
