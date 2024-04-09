package com.example.farmguardian.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.farmguardian.R
import com.example.farmguardian.views.healthactivity

class LivestockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livestock)
        val back = findViewById<CardView>(R.id.LIvestockBack)
        val Health = findViewById<CardView>(R.id.LIvestockHealth)
        val sell = findViewById<CardView>(R.id.LivestockBuyandsell)
        val Transport = findViewById<CardView>(R.id.livestockTransport)
        val lost = findViewById<CardView>(R.id.livestocklost)
        lost.setOnClickListener {
            startActivity(
                Intent(
                    this@LivestockActivity,
                    LostLivestock::class.java
                )
            )
        }
        Transport.setOnClickListener {
            startActivity(
                Intent(
                    this@LivestockActivity,
                    TransportLivestock::class.java
                )
            )
        }
        sell.setOnClickListener {
            startActivity(
                Intent(
                    this@LivestockActivity,
                    SellLivestockActivity::class.java
                )
            )
        }
        Health.setOnClickListener {
            startActivity(
                Intent(
                    this@LivestockActivity,
                    healthactivity::class.java
                )
            )
        }
        back.setOnClickListener {
            startActivity(
                Intent(
                    this@LivestockActivity,
                    HomeActivity::class.java
                )
            )
        }
    }
}