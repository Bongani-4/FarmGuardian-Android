package com.example.farmguardian;

public interface FirebaseDatabaseCallback<T> {
    void onSuccess(T result);

    void onFailure(String errorMessage);
}
