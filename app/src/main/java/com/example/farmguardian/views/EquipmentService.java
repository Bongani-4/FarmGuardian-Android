package com.example.farmguardian.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.farmguardian.R;

public class EquipmentService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        CardView equipmentshare = findViewById(R.id.EquipmentShare);
        CardView Equipmentsell = findViewById(R.id.EquipmentSell);
        CardView backEquipment = findViewById(R.id.EquipmentBack);

        Equipmentsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipmentService.this, SellEquipmentActivity.class));


            }
        });


      equipmentshare.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(EquipmentService.this,EquipmentSharingActivity.class));
          }
      });
        backEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(EquipmentService.this,HomeActivity.class));
            }
        });

    }
}