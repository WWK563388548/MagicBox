package com.example.wwk.functiondemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wwk.functiondemo.MainActivity;
import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.utils.SharedPreferencesUtils;
import com.example.wwk.functiondemo.utils.UtilTools;

/**
 * Created by wwk on 17/5/21.
 *
 * Interface of Launch
 */

public class LaunchActivity extends AppCompatActivity{

    // Estimate app that is it being launched for the first time
    public static final String SHARE_IS_FIRST = "isFirst";
    // Delay of launch
    public static final int HANDLER_LAUNCH = 1001;
    private TextView mLaunchView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_LAUNCH:
                    // Estimate app that is it being launched for the first time
                    if (isFirst()) {
                        startActivity(new Intent(LaunchActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        initializeView();
    }

    // Initialize view
    private void initializeView() {
        // Delay 2 second when launching the interface
        handler.sendEmptyMessageDelayed(HANDLER_LAUNCH, 2000);
        mLaunchView = (TextView) findViewById(R.id.launch_view);
        UtilTools.setFont(this, mLaunchView);
    }

    // Estimate app that is it being launched for the first time
    private boolean isFirst() {
        boolean isFirst = SharedPreferencesUtils.getBoolean(this, SHARE_IS_FIRST, true);

        if (isFirst) {
            SharedPreferencesUtils.putBoolean(this, SHARE_IS_FIRST, false);
            // It's first Launching
            return true;
        } else {
            return false;
        }
    }


}
