package com.example.farmguardian;
import android.content.Context;
import android.widget.Toast;

import com.example.farmguardian.Models.NewsAPIResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public void getNewsHadlines(OnFetchDataListener listener, String category, String query)
    {
        // Load the API key from its  file
        String apiKey = getApiKey(context);

        CallNewsApi callnewsapi = retrofit.create(CallNewsApi.class);
        Call<NewsAPIResponse> call = callnewsapi.callheadlines("us", category,query,apiKey);

        try{
             call.enqueue(new Callback<NewsAPIResponse>() {
                 @Override
                 public void onResponse(Call<NewsAPIResponse> call, Response<NewsAPIResponse> response) {

                  if (!response.isSuccessful())
                  {
                      Toast.makeText(context, "Error on Getting News", Toast.LENGTH_SHORT).show();
                      
                  }

                  listener.onfetchData(response.body().getArticles(), response.message());

                     
                 }

                 @Override
                 public void onFailure(Call<NewsAPIResponse> call, Throwable throwable) {

                     listener.onError("Request Failed");
                 }
             });

        }
        catch ( Exception e)
        {
             e.printStackTrace();

        }
    }
    public RequestManager(Context context) { this.context = context;

    }


    private static String getApiKey(Context context) {
        Properties properties = new Properties();
        try {
            // Load the api file from assets
            InputStream input = context.getAssets().open("api.properties");
            properties.load(input);
            input.close();
            // Get the API key value
            return properties.getProperty("API_KEY");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface CallNewsApi{
        @GET("top-headlines")
        Call<NewsAPIResponse> callheadlines(
           @Query("country")   String country,
           @Query("category") String category,
           @Query ("q") String query,
           @Query("apiKey") String api_Key
        );
    }
}
