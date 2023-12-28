package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

public class ACaretakerProfile2 extends AppCompatActivity {

    EditText fullnames,experience,location,contacts;
    String username;

     CheckBox CBavailable;
     Button btnDone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acaretaker_profile2);

        LoginActivity login = new LoginActivity();

        btnDone= findViewById(R.id.buttonSetACprofile);
        CBavailable = findViewById(R.id.checkBoxAVailable);
        contacts = findViewById(R.id.editTextContacts);
        location = findViewById(R.id.editTextLocation);
        fullnames= findViewById(R.id.editTextProfileNames);
        experience = findViewById(R.id.editTextExperiance);
        username = login.getUsername();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String  Contacts =contacts.getText().toString();
                String   Location = location.getText().toString();
                String  Fullnames= fullnames.getText().toString();
                String  Expirience = experience.getText().toString();
                int isavailable  = CBavailable.isChecked() ? 1 : 0;

                Database db = new Database(getApplicationContext(),"FarmGuardian",null,1);
                db.saveProfile(username,Contacts,Location,Fullnames,Expirience,isavailable);
                Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ACaretakerProfile2.this,HomeACActivity.class));



            }
        });



    }
}