package com.github.kassadin.savestate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.orhanobut.logger.Logger;

public class BActivity extends BaseActivity {

    public static void start(Context context, String param) {
        Intent starter = new Intent(context, BActivity.class);
        starter.putExtra("param", param);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        if (getIntent() != null) {
            String param = getIntent().getStringExtra("param");
            if (TextUtils.isEmpty(param)) {
                param = "empty";
            }
            Logger.d("param : %s", param);
        }

        App.getInstance().isOk = true;
    }

    public void startC(View view) {
        String param = "param from b";
        CActivity.start(this, param);
    }
}
