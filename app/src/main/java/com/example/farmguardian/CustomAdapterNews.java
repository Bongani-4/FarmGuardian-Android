package com.example.farmguardian;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import  com.squareup.picasso.Picasso;

import com.example.farmguardian.Models.NewsHeadlines;

import java.util.List;

public class CustomAdapterNews extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    private NewsSelectListner Listener;

    public CustomAdapterNews(Context context, List<NewsHeadlines> headlines,NewsSelectListner Listener) {
        this.context = context;
        this.headlines = headlines;
        this.Listener = Listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.TextTitle.setText(headlines.get(position).getTitle());
        holder.Textsource.setText(headlines.get(position).getSource().getName());
        if(headlines.get(position).getUrlToImage() != null)
        {
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.imageHeadline);

            
        }
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listener.OnNewsSelected(headlines.get(position));
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
