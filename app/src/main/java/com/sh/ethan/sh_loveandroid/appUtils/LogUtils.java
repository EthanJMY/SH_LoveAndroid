package com.sh.ethan.sh_loveandroid.appUtils;

import android.util.Log;



/**
 * Author：     ethan
 * CreatTime：  2017/10/19
 * ContactInfo：
 * Description: Log工具
 */
public class LogUtils {
    private static String className;
    private static String methodName;
    private static int lineNumber;
    private static boolean isDebug = false;

    private LogUtils() {
        throw new UnsupportedOperationException("LogUtils cannot be instantiated");
    }

    public static  void setLogIsDebug(boolean isDebugMode) {
        isDebug = isDebugMode;
    }

    private static void getMethodName(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    private static String buildLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    public static void d(String message) {
        if (isDebug) {
            getMethodName(new Throwable().getStackTrace());
            Log.d(className, buildLog(message));
        }
    }

    public static void i(String message) {
        if (isDebug) {
            getMethodName(new Throwable().getStackTrace());
            Log.i(className, buildLog(message));
        }
    }

    public static void w(String message) {
        if (isDebug) {
            getMethodName(new Throwable().getStackTrace());
            Log.w(className, buildLog(message));
        }
    }

    public static void e(String message) {
        if (isDebug) {
            getMethodName(new Throwable().getStackTrace());
            Log.e(className, buildLog(message));
        }
    }
}
