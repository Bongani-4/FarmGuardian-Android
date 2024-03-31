package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.farmguardian.Models.NewsAPIResponse;
import com.example.farmguardian.Models.NewsHeadlines;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsSelectListner {


    RecyclerView recylerview;
    CustomAdapterNews adapter;

    ProgressBar progressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        progressD = findViewById(R.id.load);



        RequestManager manager = new RequestManager(this);
        showLoading();
        manager.getNewsHadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsAPIResponse> listener = new OnFetchDataListener<NewsAPIResponse>() {
        @Override
        public void onfetchData(List<NewsHeadlines> list, String message) {
            ShowNews(list);
            hideLoading();
        }

        @Override
        public void onError(String message) {
              hideLoading();
        }
    };

    private void ShowNews(List<NewsHeadlines> list) {

        recylerview = findViewById(R.id.recycler_news);
        recylerview.setHasFixedSize(true);
        recylerview.setLayoutManager(new GridLayoutManager( this,1));

        adapter = new CustomAdapterNews(this,list, this);
        recylerview.setAdapter(adapter);
    }


    // Show the ProgressBar
    private void showLoading() {
        progressD.setVisibility(View.VISIBLE);
    }

    // Hide the ProgressBar
    private void hideLoading() {
        progressD.setVisibility(View.GONE);
    }

    @Override
    public void OnNewsSelected(NewsHeadlines newsheadlines) {


        startActivity(new Intent(NewsActivity.this, NewsDetailsActivity.class)
                .putExtra("data", newsheadlines));

    }
}