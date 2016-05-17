package com.example.administrator.myapplication;

import android.app.Application;

import com.bumptech.glide.Glide;

/**
 * gxj
 * 2016/4/19
 */
public class MyAplication extends Application {
    private static MyAplication myAplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myAplication = this;
    }

    public static MyAplication getInstance() {
        return myAplication;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(getApplicationContext()).clearMemory();
    }
}
