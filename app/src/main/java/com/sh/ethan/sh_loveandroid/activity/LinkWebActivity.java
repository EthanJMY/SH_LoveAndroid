package com.sh.ethan.sh_loveandroid.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sh.ethan.sh_loveandroid.R;
import com.sh.ethan.sh_loveandroid.appUtils.ToastUtil;
import com.sh.ethan.sh_loveandroid.base.LoveAndroidActivity;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;

/**
 * Created by ethan on 2018/4/23.
 */
public class LinkWebActivity extends LoveAndroidActivity {

    @BindView(R.id.vv)
    WebView vv;
    @BindView(R.id.webloadding)
    AVLoadingIndicatorView webloadding;
    private String url = "";

    @Override
    protected int inflateLayout() {
        return R.layout.activity_link_web;
    }

    @Override
    protected void initIntentData(Bundle bundle) {
        url = this.getIntent().getExtras().getString("url");
    }

    @Override
    protected void doOperateOnResume() {
        WebSettings settings = vv.getSettings();
        settings.setJavaScriptEnabled(true);
        vv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //super.onPageFinished(view, url);
                webloadding.setVisibility(View.GONE);
            }
        });
        if (url!=null||!url.equals("")) {
            showWebView("www.baidu.com");
        }else {
            ToastUtil.showShort("链接不存在");
            finish();
        }
    }

    @Override
    protected void doOperateOnCreate() {

    }

    @Override
    public void onBackPressed() {
        if (vv.canGoBack()) {
            vv.goBack();
        } else {
            finish();
        }
    }

    private void showWebView(String html) {
        // 设置WevView要显示的网页
        vv.loadDataWithBaseURL(null, html, "text/html", "utf-8",
                null);
        vv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        vv.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        vv.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        //        luntanListview.getSettings().setBuiltInZoomControls(true); //页面添加缩放按钮
        //                luntanListview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);   //取消滚动条

        //                点击链接由自己处理，而不是新开Android的系统browser响应该链接。
        vv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置点击网页里面的链接还是在当前的webview里跳转
                view.loadUrl(url);
                return true;
            }
        });
    }
}
