package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.farmguardian.Models.NewsAPIResponse;
import com.example.farmguardian.Models.NewsHeadlines;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    RecyclerView recylerview;
    CustomAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        RequestManager manager = new RequestManager(this);
        manager.getNewsHadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsAPIResponse> listener = new OnFetchDataListener<NewsAPIResponse>() {
        @Override
        public void onfetchData(List<NewsHeadlines> list, String message) {
            ShowNews(list);
        }

        @Override
        public void onError(String message) {

        }
    };

    private void ShowNews(List<NewsHeadlines> list) {

        recylerview = findViewById(R.id.recycler_news);
        recylerview.setHasFixedSize(true);
        recylerview.setLayoutManager(new GridLayoutManager( this,1));

        adapter = new CustomAdapter(this,list);
        recylerview.setAdapter(adapter);
    }
}