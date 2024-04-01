package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farmguardian.Models.NewsAPIResponse;
import com.example.farmguardian.Models.NewsHeadlines;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsSelectListner, View.OnClickListener {


    RecyclerView recylerview;
    CustomAdapterNews adapter;
   ImageView back;
    ProgressBar progressD;
    Button btn1,btn2,btn3,btn4,btn5,btn6;
    SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        progressD = findViewById(R.id.load);
        searchview = findViewById(R.id.serachbar);
        back = findViewById(R.id.backNews);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( NewsActivity.this, HomeActivity.class));
            }
        });


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                RequestManagerNews manager = new RequestManagerNews(NewsActivity.this);
                showLoading();
                manager.getNewsHadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btn1 = findViewById(R.id.btnAgric);
        btn1.setOnClickListener(this);

        btn2 = findViewById(R.id.btnHealth);
        btn2.setOnClickListener(this);

        btn3 = findViewById(R.id.btnScince);
        btn3.setOnClickListener(this);

        btn4 = findViewById(R.id.btnTech);
        btn4.setOnClickListener(this);

        btn5 = findViewById(R.id.business);
        btn5.setOnClickListener(this);

        btn6 = findViewById(R.id.btngeneral);
        btn6.setOnClickListener(this);

        btn1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));
        btn2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));
        btn3.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));
        btn4.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));
        btn5.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));
        btn6.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));





        RequestManagerNews manager = new RequestManagerNews(this);
        showLoading();
        manager.getNewsHadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsAPIResponse> listener = new OnFetchDataListener<NewsAPIResponse>() {
        @Override
        public void onfetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty())
            {
                Toast.makeText(NewsActivity.this,"nothing from the search found", Toast.LENGTH_SHORT).show();
            }
            ShowNews(list);
            hideLoading();
        }

        @Override
        public void onError(String message) {
              hideLoading();
            Toast.makeText(NewsActivity.this, "error fetching data", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        Button button =  (Button) v;
         String category = button.getText().toString();

         //call category API
        RequestManagerNews manager = new RequestManagerNews(this);
        showLoading();
        manager.getNewsHadlines(listener, category, null);

    }
}