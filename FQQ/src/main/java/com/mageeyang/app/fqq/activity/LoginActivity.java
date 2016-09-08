package com.mageeyang.app.fqq.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mageeyang.app.fqq.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity  {

    private Context mContext;
    private RelativeLayout rl_user;
    private Button mLogin;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

    }

    private void findView(){

    }

    private void init(){

    }
}

