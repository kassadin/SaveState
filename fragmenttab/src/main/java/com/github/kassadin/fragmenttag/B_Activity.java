package com.github.kassadin.fragmenttag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class B_Activity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, B_Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_);
    }
}
