package com.example.farmguardian.views

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        send = findViewById(R.id.buttonSend)
        prmt = findViewById(R.id.editTextMessage)
        responseTextView = findViewById(R.id.textViewResponse)
        recyclerView = findViewById(R.id.recyclerViewChat)

        send.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen))

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        send.setOnClickListener {
            val prompt = prmt.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-pro",
                    apiKey = getApiKey(applicationContext)
                )
                val response = generateResponse(generativeModel, prompt)
                println(response.text)

                // Display response
                responseTextView.text = response.text

                // Add response to chat adapter
                chatAdapter.addMessage("User", prompt)
                chatAdapter.addMessage("Dr Bot", response.text)
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
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
