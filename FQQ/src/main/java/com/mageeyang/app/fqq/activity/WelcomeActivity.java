package com.mageeyang.app.fqq.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mageeyang.android.common.util.SharedPreferencesUtils;
import com.mageeyang.app.fqq.R;

public class WelcomeActivity extends Activity {

    private static final String TAG = "com.mageeyang.app.fqq.activity.WelcomeActivity";

    private Context mContext;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        findView();
        init();
    }

    private void findView(){
        mImageView = (ImageView) findViewById(R.id.welcome_imageview);
    }

    private void init(){
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirst = SharedPreferencesUtils.getBoolean(mContext,"isFirst",false);
                if(isFirst){
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);
    }
}
