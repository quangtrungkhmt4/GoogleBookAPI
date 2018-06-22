package com.example.quang.library.utils;

public interface OnEventListener<T> {
    void onSuccess(T object);
    void onFailure(Exception e);
}
