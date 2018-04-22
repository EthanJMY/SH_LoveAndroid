package com.sh.ethan.sh_loveandroid.application;

import android.app.Application;

import com.sh.ethan.sh_loveandroid.R;
import com.sh.ethan.sh_loveandroid.appUtils.LogUtils;
import com.sh.ethan.sh_loveandroid.appUtils.ToastUtil;
import com.sh.ethan.sh_loveandroid.base.LoveAndroidActivity;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.util.Stack;

/**
 * Created by ethan on 2018/4/22.
 */
public class LoveAndroidApplication extends Application {
    private static LoveAndroidApplication loveAndroidApplication;
    private static RequestQueue requestQueue;
    private static Stack<LoveAndroidActivity> activityList = new Stack<>();//保存activity集合

    @Override
    public void onCreate() {
        super.onCreate();
        initializeNoHttp();
        loveAndroidApplication = this;
        LogUtils.setLogIsDebug(true);
    }

    public static LoveAndroidApplication getInstance() {
        return loveAndroidApplication;
    }

    /**
     * 配置NoHttp
     */
    private void initializeNoHttp() {
        NoHttp.initialize(this); // NoHttp默认初始化。
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(30 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(30 * 1000) // 全局服务器响应超时时间，单位毫秒。
        );
        requestQueue = NoHttp.newRequestQueue();
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。
    }


    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    //在集合中添加activity
    public static void addActivity(LoveAndroidActivity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    //讲将当前activity从历史集合中移除
    public static void removeActivity(LoveAndroidActivity activity) {
        if (activity != null) {
            activityList.remove(activity);
        }
    }

    //关闭所有的activity
    private static void finishAllAActivity() {
        if (activityList.size() > 0) {
            for (LoveAndroidActivity activity : activityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } else {
            ToastUtil.showLong(getStringFromXML(R.string.tip_noActivity));
        }
    }

    public static void AppExit() {
        finishAllAActivity();
    }

    public static String getStringFromXML(int id) {
        return loveAndroidApplication.getResources().getString(id);
    }

}
