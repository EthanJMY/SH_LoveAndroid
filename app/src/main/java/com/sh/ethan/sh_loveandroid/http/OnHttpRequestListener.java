package com.sh.ethan.sh_loveandroid.http;

/**
 * Created by ethan on 2018/1/30.
 */

public interface OnHttpRequestListener<T> {

    /**
     * 请求成功
     *
     * @param t
     */
    void onRequestSuccess(String msg, T t);

    /**
     * 请求失败
     *
     * @param msg
     */
    void onRequestFail(String msg);

}
