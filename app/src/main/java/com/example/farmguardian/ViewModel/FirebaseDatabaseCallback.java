package com.example.farmguardian.ViewModel;

public interface FirebaseDatabaseCallback<T> {
    void onSuccess(T result);

    void onFailure(String errorMessage);
}
