package com.sh.ethan.sh_loveandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.sh.ethan.sh_loveandroid.R;

/**
 * Created by ethan on 2018/4/22.
 */
public class WelComeActivity extends Activity {
    private MyHandler myHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myHandler=new MyHandler();
        myHandler.sendEmptyMessageDelayed(1,1500);
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    startActivity(new Intent(WelComeActivity.this, MainActivity.class));
                    finish();
                    break;
                    default:
                        break;
            }
        }
    }
}
