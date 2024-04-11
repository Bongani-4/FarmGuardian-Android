package com.example.farmguardian.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmguardian.R
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.IOException
import java.util.Properties


class healthactivity : AppCompatActivity() {

    lateinit var context: Context
    lateinit var apiKey: String
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    // Image bitmaps
    lateinit var image1Bitmap: Bitmap
    lateinit var image2Bitmap: Bitmap

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthactivity)

        context = this
        val  resultTextView = findViewById<TextView>(R.id.result_health)

        // Initializing apiKey by calling getApiKey function
        apiKey = getApiKey(context)

        val image1View = findViewById<ImageView>(R.id.image1)
        val image2View = findViewById<ImageView>(R.id.image2)
        val   progressBar = findViewById<ProgressBar>(R.id.progressBarHealth)
        val backHealth = findViewById<ImageView>(R.id.backHealth)

        val fabCompose = findViewById<FloatingActionButton>(R.id.fabCompose)
        fabCompose.setOnClickListener {
            // Open compose screen or activity
            startActivity(Intent(this, ChatDR::class.java))
        }

        backHealth.setOnClickListener {
            val intent = Intent(this, LivestockActivity::class.java)
            startActivity(intent)
            finish()
        }
        // Upload Image 1
        findViewById<Button>(R.id.button_upload_image1).setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_CODE)
        }

        // Upload Image 2
        findViewById<Button>(R.id.button_upload_image2).setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_CODE + 1)
        }

        // Process Images
        findViewById<Button>(R.id.button_process_images).setOnClickListener {
            if (::image1Bitmap.isInitialized && ::image2Bitmap.isInitialized) {

                progressBar.visibility = View.VISIBLE

                coroutineScope.launch {
                    try {
                        val generativeModel = GenerativeModel(
                            modelName = "gemini-pro-vision",
                            apiKey = apiKey
                        )

                        // Create content with uploaded images
                        val inputContent = content {
                            image(image1Bitmap)
                            image(image2Bitmap)
                            text("What's in these pictures, and whats happening?, if you there is a disease? whats the diseases?,whats the treatment for the diseases?")
                        }

                        val response = generativeModel.generateContent(inputContent)
                        withContext(Dispatchers.Main) {
                            resultTextView.setText(response.text)
                            progressBar.visibility = View.GONE
                        }
                    } catch (e: Exception) {
                        println("An error occurred: ${e.message}")
                    }
                }
            } else {


                Toast.makeText(this@healthactivity, "Upload 2 images for better accuracy", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openImagePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    private fun getApiKey(context: Context): String {
        val properties = Properties()
        return try {
            // Loading api file from assets
            val input = context.assets.open("api.properties")
            properties.load(input)
            input.close()
            // Get the API key value
            properties.getProperty("API_Key_GeminiAI") ?: throw RuntimeException("API Key not found")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    // Handling the result of the image picker intent
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri = data.data ?: return
             val imageView: ImageView = if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                findViewById(R.id.image1)
            } else {
                findViewById(R.id.image2)
            }


            try {
                Picasso.get().load(imageUri).into(imageView, object : Callback {
                    override fun onSuccess() {
                        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                            image1Bitmap = bitmap
                        } else {
                            image2Bitmap = bitmap
                        }
                    }

                    override fun onError(e: Exception?) {
                        println("Error loading image: ${e?.message}")
                    }
                })
            } catch (e: IOException) {
                println("Error loading image: ${e.message}")
            }
        }
    }
}
