package com.example.farmguardian.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmguardian.R
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

data class Response(val text: String)

class ChatDR : AppCompatActivity() {

    private lateinit var send: Button
    private lateinit var prmt: EditText
    private lateinit var responseTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private val chatAdapter = ChatAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_dr)

        // Find views
        send = findViewById(R.id.buttonSend)
        prmt = findViewById(R.id.editTextMessage)
        responseTextView = findViewById(R.id.textViewResponse)
        recyclerView = findViewById(R.id.recyclerViewChat)
        val progressBar = findViewById<ProgressBar>(R.id.progress)
        val backchat = findViewById<ImageView>(R.id.backchat)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        backchat.setOnClickListener {
            val intent = Intent(this, healthactivity::class.java)
            startActivity(intent)
            finish()
        }


        send.setOnClickListener {
            val prompt = prmt.text.toString()
            prmt.text.clear()
            progressBar.visibility = View.VISIBLE // Show progress bar

            // Launching coroutine for an API request
            CoroutineScope(Dispatchers.IO).launch {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-pro",
                    apiKey = getApiKey(applicationContext)
                )
                val response = generateResponse(generativeModel, prompt)

                withContext(Dispatchers.Main) {
                    // Hide progress bar
                    progressBar.visibility = View.GONE

                    // Add response to chat adapter
                    chatAdapter.addMessage("User", prompt)
                    chatAdapter.addMessage("Dr Bot", response.text)
                    recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }
    }


    private suspend fun generateResponse(generativeModel: GenerativeModel, prompt: String): Response {
        return withContext(Dispatchers.IO) {
            try {
                val modelResponse = generativeModel.generateContent(prompt)
                Response(modelResponse.text ?: "")
            } catch (e: Exception) {
                throw RuntimeException("Failed to generate response: ${e.message}")
            }
        }
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
}
