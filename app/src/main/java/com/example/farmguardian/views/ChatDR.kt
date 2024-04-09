package com.example.farmguardian.views

import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Context

import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.example.farmguardian.R
import com.google.ai.client.generativeai.GenerativeModel

import kotlinx.coroutines.*
import java.io.IOException
import java.util.Properties

data class Response(val text: String)

class ChatDR : AppCompatActivity() {

    private lateinit var send: Button
    private lateinit var prmt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_dr)
        send = findViewById(R.id.buttonSend)
        prmt = findViewById(R.id.editTextMessage)

        send.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen))

        CoroutineScope(Dispatchers.Main).launch {
            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = getApiKey(applicationContext)
            )
            val prompt = prmt.text.toString()
                val response = generateResponse(generativeModel, prompt)
            println(response.text)
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
