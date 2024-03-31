package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmguardian.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    NewsHeadlines headlines;
    TextView title,author,time,detail,content,UrlText;
    ImageView imgnews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        UrlText = findViewById(R.id.UrlText);
        UrlText.setOnClickListener(v -> {
            String url = UrlText.getText().toString();
            if (!url.isEmpty()) {
                openUrlInBrowser(url);
            } else {
                Toast.makeText(this, "URL is empty", Toast.LENGTH_SHORT).show();
            }
        });

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");
        title = findViewById(R.id.TextDetails);
        author = findViewById(R.id.DetaildAuthor);
         time= findViewById(R.id.DetailTime);
        detail = findViewById(R.id.DetailDetail);
        content= findViewById(R.id.DetailContent);
        imgnews = findViewById(R.id.ImgDetail);


        title.setText(headlines.getTitle());
        author.setText(headlines.getAuthor());
        time.setText(headlines.getPublishedAt());
        detail.setText(headlines.getDescription());
        UrlText.setText(headlines.getUrl());
        content.setText(headlines.getContent());

        Picasso.get().load(headlines.getUrlToImage()).into(imgnews);



    }//Open the link for more content, if no web browser copy link
    @SuppressLint("QueryPermissionsNeeded")
    private void openUrlInBrowser(String url) {
        if (!url.isEmpty()) {

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage("com.android.chrome");
            startActivity(intent);

            
        } else {
            Toast.makeText(this, "URL is empty", Toast.LENGTH_SHORT).show();
        }
    }

}