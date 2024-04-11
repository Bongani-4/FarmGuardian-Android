package com.example.farmguardian.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farmguardian.R

// Adapter class for the AI PEt chat RecyclerView
class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // List to store the chat messages
    private val messages = mutableListOf<Pair<String, String>>()

    // Function to add a new message to the chat
    fun addMessage(sender: String, message: String) {
        messages.add(Pair(sender, message))
        notifyItemInserted(messages.size - 1)
    }

    // Inflates the layout for each item view in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(itemView)
    }

    // Binds the data to the views in each item view
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val (sender, message) = messages[position]
        holder.bind(sender, message)
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int {
        return messages.size
    }

    // ViewHolder class to hold the views for each item in the RecyclerView
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val senderTextView: TextView = itemView.findViewById(R.id.senderTextView)
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)

        // Binds the data to the views
        fun bind(sender: String, message: String) {
            senderTextView.text = sender
            messageTextView.text = message
        }
    }
}
