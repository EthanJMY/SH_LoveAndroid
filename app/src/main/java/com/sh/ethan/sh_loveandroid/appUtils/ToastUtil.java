package com.sh.ethan.sh_loveandroid.appUtils;

import android.widget.Toast;

import com.sh.ethan.sh_loveandroid.application.LoveAndroidApplication;

/**
 * Author：     ethan
 * CreatTime：  2016/11/28
 * ContactInfo：
 * Description: Toast tools
 */
public class ToastUtil {

    private static LoveAndroidApplication app = LoveAndroidApplication.getInstance();

    public static void showLong(String text) {
        Toast.makeText(app, text, Toast.LENGTH_LONG).show();
    }

    public static void showShort(String text) {
        Toast.makeText(app, text, Toast.LENGTH_SHORT).show();
    }
}
