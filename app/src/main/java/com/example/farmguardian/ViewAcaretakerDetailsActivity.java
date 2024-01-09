package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewAcaretakerDetailsActivity extends AppCompatActivity {
 Button back = findViewById(R.id.buttonBack);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acaretaker_details);



        // Retrieve data from the Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fullNames = extras.getString("fullNames");
            String location = extras.getString("location");
            String contacts = extras.getString("contacts");
            String experience = extras.getString("experience");
            int isAvailable = extras.getInt("isAvailable", 0);

            // Display the data in the UI
            TextView textViewFullNames = findViewById(R.id.textViewFullNames);
            TextView textViewLocation = findViewById(R.id.textViewLocation);
            TextView textViewContact = findViewById(R.id.textViewContact);
            TextView textViewExperience = findViewById(R.id.textViewExperience);
            TextView textViewAvailable = findViewById(R.id.textViewAvailable);

            textViewFullNames.setText("Full Names: " + fullNames);
            textViewLocation.setText("Location: " + location);
            textViewContact.setText("Contact: " + contacts);
            textViewExperience.setText("Experience: " + experience);
            textViewAvailable.setText("Available: " + (isAvailable == 1 ? "Yes" : "No"));
        }
       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewAcaretakerDetailsActivity.this, ACaretakerProfile2.class));
            }
        });
    }

}
