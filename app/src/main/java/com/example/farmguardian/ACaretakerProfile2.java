package com.example.farmguardian;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ACaretakerProfile2 extends AppCompatActivity {

    EditText fullnames, experience, location, contacts;
    String username;

    CheckBox CBavailable;
    Button btnSave, btnViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acaretaker_profile2);

        LoginActivity login = new LoginActivity();
        btnViewProfile = findViewById(R.id.buttonViewACprofile);
        btnSave = findViewById(R.id.buttonSetACprofile);
        CBavailable = findViewById(R.id.checkBoxAVailable);
        contacts = findViewById(R.id.editTextContacts);
        location = findViewById(R.id.editTextLocation);
        fullnames = findViewById(R.id.editTextProfileNames);
        experience = findViewById(R.id.editTextExperiance);
        username = login.getUsername();



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Contacts = contacts.getText().toString();
                String Location = location.getText().toString();
                String Fullnames = fullnames.getText().toString();
                String Experience = experience.getText().toString();
                int isAvailable = CBavailable.isChecked() ? 1 : 0;


                // Validation of entered data
                if (Fullnames.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Full Name is required", Toast.LENGTH_SHORT).show();
                } else {
                    // Save data to the database
                    Database db = new Database(getApplicationContext(), "FarmGuardian", null, 1);
                    db.saveProfile(username, Contacts, Location, Fullnames, Experience, isAvailable);

                    // Display a toast message
                    Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();


                }
            }
        });
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Contacts = contacts.getText().toString();
                String Location = location.getText().toString();
                String Fullnames = fullnames.getText().toString();
                String Experience = experience.getText().toString();
                int isAvailable = CBavailable.isChecked() ? 1 : 0;

                // Pass data to the ViewAcaretakerDetailsActivity
                Intent intent = new Intent(ACaretakerProfile2.this, ViewAcaretakerDetailsActivity.class);
                intent.putExtra("fullNames", Fullnames);
                intent.putExtra("location", Location);
                intent.putExtra("contacts", Contacts);
                intent.putExtra("experience", Experience);
                intent.putExtra("isAvailable", isAvailable);

                // Navigate to the ViewAcaretakerDetailsActivity
                startActivity(intent);
            }
        });
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle schema updates here
        if (oldVersion < 2) {
            //updating schema from version 1 to version 2
            db.execSQL("ALTER TABLE ACprofile ADD COLUMN new_column_name TEXT");
        }
    }
}
