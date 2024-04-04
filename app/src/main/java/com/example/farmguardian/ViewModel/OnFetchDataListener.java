package com.example.farmguardian.ViewModel;

import com.example.farmguardian.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsAPIResponse> {
    void onfetchData(List<NewsHeadlines> list, String message);
    void onError(String message);


}
