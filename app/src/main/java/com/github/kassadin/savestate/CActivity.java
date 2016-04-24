package com.github.kassadin.savestate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

public class CActivity extends BaseActivity {

    public static void start(Context context, String param) {
        Intent starter = new Intent(context, CActivity.class);
        starter.putExtra("param", param);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        if (savedInstanceState != null) {
            // 恢复
            if (!App.getInstance().isOk) {
                AActivity.start(this);
                this.finish();
                return;
            }
        }

        if (getIntent() != null) {
            String param = getIntent().getStringExtra("param");
            if (TextUtils.isEmpty(param)) {
                param = "empty";
            }
            Logger.d("param : %s", param);
        }


    }
}
