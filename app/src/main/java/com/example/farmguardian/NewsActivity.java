package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.farmguardian.Models.NewsAPIResponse;
import com.example.farmguardian.Models.NewsHeadlines;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

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

        }

        @Override
        public void onError(String message) {

        }
    };
}