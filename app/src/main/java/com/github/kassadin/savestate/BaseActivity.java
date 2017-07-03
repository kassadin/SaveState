package com.github.kassadin.savestate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("onCreate, savedInstanceState == null ? %s", savedInstanceState == null ? true : savedInstanceState.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i("onRestart");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.i("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.i("onSaveInstanceState");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("new Intent:%s", intent.getFlags());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
