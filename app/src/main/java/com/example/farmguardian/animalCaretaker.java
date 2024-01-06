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
        setContentView(R.layout.aniamal_caretaker);

        Button hireButton = findViewById(R.id.Hirecaretaker);
        hireButton.setBackgroundColor(ContextCompat.getColor(this, R.color.DarkGreen));

        hireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isHireCaretakerButtonClicked = true;
                startActivity(new Intent(FirstPageActivity.this, LoginActivity.class));
            }
        });
    }
        //get method
        public boolean isHireCaretakerButtonClicked() {
            return isHireCaretakerButtonClicked;
        }

}
