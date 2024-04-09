package com.example.farmguardian.views

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.farmguardian.R

class ViewACSetProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_acset_profile)

        // Data from the previous activity
        val intent = intent
        val fullNames = intent.getStringExtra("fullNames")
        val location = intent.getStringExtra("location")
        val contacts = intent.getStringExtra("contacts")
        val experience = intent.getStringExtra("experience")
        val available =
            intent.getBooleanExtra("available", false) // Default to false if not present

        // Updating UI with received data
        val textViewFullNames = findViewById<TextView>(R.id.textViewFullNames1)
        val textViewLocation = findViewById<TextView>(R.id.textViewLocation1)
        val textViewContacts = findViewById<TextView>(R.id.textViewContacts1)
        val textViewExperience = findViewById<TextView>(R.id.textViewExperience1)
        val textViewAvailable = findViewById<TextView>(R.id.textViewAvailable1)
        textViewFullNames.text = "Full Names: $fullNames"
        textViewLocation.text = "Location: $location"
        textViewContacts.text = "Contacts: $contacts"
        textViewExperience.text = "Experience: $experience"
        textViewAvailable.text = "Availability: " + if (available) "Yes" else "No"

        // Button to navigate back to HomeACActivity
        val btn = findViewById<Button>(R.id.buttonhome)
    }
}
