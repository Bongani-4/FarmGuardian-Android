package com.example.farmguardian.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.farmguardian.R;

public class Equipment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        CardView equipmentshare = findViewById(R.id.EquipmentShare);
        CardView Equipmentbuy = findViewById(R.id.EquipmentBuy);
        CardView backEquipment = findViewById(R.id.EquipmentBack);

      equipmentshare.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(Equipment.this,EquipmentSharingActivity.class));
          }
      });
        backEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Equipment.this,HomeActivity.class));
            }
        });

    }
}