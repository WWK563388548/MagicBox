package com.example.wwk.functiondemo.application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by wwk on 17/5/23.
 */

public class BaseApplication extends Application {

    // Bmob's Key
    public static final String BMOB_APP_ID = "4d345003be01bddf52e5e1bfbe0f2ea2";

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Bmob
        Bmob.initialize(this, BMOB_APP_ID);
    }
}
