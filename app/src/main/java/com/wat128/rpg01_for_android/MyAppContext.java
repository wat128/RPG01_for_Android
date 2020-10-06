package com.wat128.rpg01_for_android;

import android.app.Application;

// Context取得用
public class MyAppContext extends Application {

    private static MyAppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static MyAppContext getInstance() {

        if(instance == null)
            instance = new MyAppContext();

        return instance;
    }
}
