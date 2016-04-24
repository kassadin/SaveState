package com.github.kassadin.savestate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate, savedInstanceState == null ? %s", savedInstanceState == null ? true : savedInstanceState.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d("onRestart");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.d("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("onSaveInstanceState");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d("new Intent:%s", intent.getFlags());
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
