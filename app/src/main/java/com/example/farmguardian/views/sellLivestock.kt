package com.example.farmguardian.views

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.farmguardian.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SellLivestockActivity : AppCompatActivity() {

    private lateinit var livestockNameEditText: EditText
    private lateinit var livestockDescriptionEditText: EditText
    private lateinit var livestockPriceEditText: EditText
    private lateinit var sellButtonlivestock: Button

    private lateinit var currentUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_livestock)

        livestockNameEditText = findViewById(R.id.editTextEquipmentName)
        livestockDescriptionEditText = findViewById(R.id.editTextEquipmentDescription)
        livestockPriceEditText = findViewById(R.id.editTextEquipmentPrice)
        sellButtonlivestock = findViewById(R.id.buttonSell)


        findViewById<Button>(R.id.buttonUploadImage).setBackgroundTintList(ContextCompat.getColorStateList(this,
            R.color.DarkGreen
        ))

        sellButtonlivestock.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen))

            //upload image
        findViewById<Button>(R.id.buttonUploadImage).setOnClickListener {
            openImagePicker(healthactivity.PICK_IMAGE_REQUEST_CODE + 1)
        }

        currentUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance().reference

        sellButtonlivestock.setOnClickListener {
            sellEquipment()
        }
        val backHealth = findViewById<ImageView>(R.id.backsell)

        backHealth.setOnClickListener {
            val intent = Intent(this, LivestockActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sellEquipment() {
        val equipmentName = livestockNameEditText.text.toString().trim()
        val equipmentDescription = livestockDescriptionEditText.text.toString().trim()
        val equipmentPrice = livestockPriceEditText.text.toString().trim()

        if (equipmentName.isNotEmpty() && equipmentDescription.isNotEmpty() && equipmentPrice.isNotEmpty()) {
            val equipmentId = databaseReference.child("users").child(currentUser.uid).child("equipment").push().key
            val equipmentData = Equipment(equipmentId, equipmentName, equipmentDescription, equipmentPrice.toDouble())

            if (equipmentId != null) {
                databaseReference.child("users").child(currentUser.uid).child("equipment").child(equipmentId)
                    .setValue(equipmentData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "EquipmentService added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add equipment", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openImagePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }
}


data class livestock(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null
)
