package com.sh.ethan.sh_loveandroid.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


import com.sh.ethan.sh_loveandroid.application.LoveAndroidApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ethan on 2018/4/22.
 */
public abstract class LoveAndroidActivity extends AppCompatActivity {
    private Unbinder unbinder;
    private Window window;

    protected abstract int inflateLayout();//填充布局

    protected abstract void initIntentData(Bundle bundle);//初始化传递数据

    protected abstract void doOperateOnResume();        //进行业务处理(OnResume中)

    protected abstract void doOperateOnCreate();        //进行业务处理(OnCreate中)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initIntentData(savedInstanceState);//初始化intent数据
        window = this.getWindow();
        setContentView(inflateLayout());
        unbinder = ButterKnife.bind(this);
        LoveAndroidApplication.addActivity(this);
        doOperateOnCreate();
    }

    /**
     * 跳转activity
     *
     * @param target
     * @param bundle
     * @param finish
     */
    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (finish) {
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        doOperateOnResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        LoveAndroidApplication.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(LoveAndroidActivity.this, color));
        }
    }
    //将当前activity设置为全屏
    private void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
