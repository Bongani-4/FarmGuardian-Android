package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FirstPageActivity extends AppCompatActivity {

    private boolean isHireCaretakerButtonClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Button becomeButton = findViewById(R.id.BeCaretaker);
        Button hirebutton = findViewById(R.id.Hirecaretaker);


        becomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstPageActivity.this, LoginActivity.class));


                isHireCaretakerButtonClicked = true;
                }
        });
        hirebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 startActivity(new Intent(FirstPageActivity.this,LoginActivity.class));
            }
        });
    }
        //get method
        public boolean isHireCaretakerButtonClicked() {
            return isHireCaretakerButtonClicked;
        }

}
