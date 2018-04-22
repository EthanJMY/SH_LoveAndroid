package com.sh.ethan.sh_loveandroid.constant;

/**
 * Created by ethan on 2018/4/22.
 */
public class UrlContants {
    public UrlContants() {
        throw new UnsupportedOperationException("UrlContants cannot be instantiated");
    }

    private static final String DOMAIN = "http://www.wanandroid.com";

    /**
     * 首页文章列表
     */
    public static final String HOME_NEWS_LIST(int page) {
        return DOMAIN + "/article/list/" + page + "/json";
    }

    /**
     * 首页Banner图
     */
    public static final String HOME_BANNER = DOMAIN + "/banner/json";

}
