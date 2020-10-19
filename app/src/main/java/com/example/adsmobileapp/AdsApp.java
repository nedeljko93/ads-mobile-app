package com.example.adsmobileapp;

import android.app.Application;

public class AdsApp extends Application {

    private static AdsApp mInstance;
    private static LocalData localData;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        localData = getLocalData();

    }

    public static AdsApp getmInstance() {
        return mInstance;
    }

    public static LocalData getLocalData() {
        if (localData == null) {
            return new LocalData();
        }
        return localData;
    }
}
