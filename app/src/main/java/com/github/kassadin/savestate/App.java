package com.github.kassadin.savestate;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by kassadin@foxmail.com on 16/4/22 11:35
 */
public class App extends Application {

    public boolean isOk = false;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init();
        Logger.d("App start !!!");
        mInstance = this;

        Context applicationContext = getApplicationContext();
        Logger.d("app %s", String.valueOf(applicationContext == this));
    }

    public static App getInstance() {
        return mInstance;
    }
}
