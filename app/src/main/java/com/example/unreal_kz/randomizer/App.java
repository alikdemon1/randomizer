package com.example.unreal_kz.randomizer;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Unreal_KZ on 17.12.2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "YQc1U6dBf8v2iHg3IFNfGd9UovrjCHCApdfMOvRn", "dy7VNLhPHiKNNGmne23I9BV4OHqEIeKorfveZ1gH");
    }
}
