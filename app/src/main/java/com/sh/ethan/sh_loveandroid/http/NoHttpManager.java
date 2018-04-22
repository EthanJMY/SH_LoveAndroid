package com.sh.ethan.sh_loveandroid.http;


import com.sh.ethan.sh_loveandroid.appUtils.LogUtils;
import com.sh.ethan.sh_loveandroid.application.LoveAndroidApplication;
import com.sh.ethan.sh_loveandroid.constant.UrlContants;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;


/**
 * Created by ethan on 2018/4/22.
 */
public class NoHttpManager {
    private static LoveAndroidApplication loveAndroidApplication;
    private static RequestQueue requestQueue;
    private static NoHttpManager manager;

    public NoHttpManager() {
        loveAndroidApplication = LoveAndroidApplication.getInstance();
        requestQueue = loveAndroidApplication.getRequestQueue();
    }

    public static NoHttpManager getNoHttpManager() {
        if (manager == null) {
            manager = new NoHttpManager();

        }
        return manager;
    }
    /**
     * 获取首页banner图
     */
    public void getHomeBanner(String url,final OnHttpRequestListener<String> listener) {
        Request<String> request = new StringRequest(UrlContants.HOME_BANNER,
                RequestMethod.GET);
        requestQueue.add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.i("banner:"+response.get());
                listener.onRequestSuccess("",response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
//                Message message = handler.obtainMessage();
//                message.obj = "验证码发送失败";
//                message.what = errorWhat;
//                handler.sendMessage(message);
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }
    /**
     * 获取首页新闻
     */
    public void getHomeNews(String url,final OnHttpRequestListener<String> listener) {
        Request<String> request = new StringRequest(url,
                RequestMethod.GET);
        requestQueue.add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                listener.onRequestSuccess("",response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
//                Message message = handler.obtainMessage();
//                message.obj = "验证码发送失败";
//                message.what = errorWhat;
//                handler.sendMessage(message);
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }
}
