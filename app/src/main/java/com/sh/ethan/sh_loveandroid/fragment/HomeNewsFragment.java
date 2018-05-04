package com.sh.ethan.sh_loveandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.sh.ethan.sh_loveandroid.R;
import com.sh.ethan.sh_loveandroid.activity.LinkWebActivity;
import com.sh.ethan.sh_loveandroid.adapter.HomeNewsAdapter;
import com.sh.ethan.sh_loveandroid.appUtils.ToastUtil;
import com.sh.ethan.sh_loveandroid.base.LoveAndroidFragment;
import com.sh.ethan.sh_loveandroid.beans.Home_News;
import com.sh.ethan.sh_loveandroid.constant.UrlContants;
import com.sh.ethan.sh_loveandroid.http.NoHttpManager;
import com.sh.ethan.sh_loveandroid.http.OnHttpRequestListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.saeid.fabloading.LoadingView;

/**
 * Created by ethan on 2018/4/22.
 */
public class HomeNewsFragment extends LoveAndroidFragment {

    private static HomeNewsFragment fragment;
    private List<Home_News> home_newsList = new ArrayList<>();
    private int page = 0;
    private HomeNewsAdapter homeNewsAdapter = null;
    @BindView(R.id.home_newId)
    RecyclerView home_newsRecycler;
    @BindView(R.id.goTop)
    FloatingActionButton goTop;
    @BindView(R.id.news_loadding)
    AVLoadingIndicatorView news_loadding;
    @BindView(R.id.nodata)
    ImageView nodata;
    @BindView(R.id.springView)
    SpringView springView;

    @OnClick({R.id.goTop})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goTop:
                home_newsRecycler.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    public static HomeNewsFragment getInstance() {
        if (fragment == null) {
            fragment = new HomeNewsFragment();
        }
        return fragment;
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_homenews_layout;
    }

    @Override
    protected void initView(View view) {
        springView.setHeader(new DefaultHeader(getParent()));
        springView.setFooter(new DefaultFooter(getParent()));
    }

    @Override
    protected void doOperateOnResume() {

    }

    @Override
    protected void doOperateOnViewCreated() {
        initHomeNewAdapter();
        getHomeNews(page, 0);
        initListener();
    }

    private void initListener() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                ToastUtil.showShort("刷新");
            }

            @Override
            public void onLoadmore() {
                ToastUtil.showShort("加载更多");
            }
        });
    }

    private void initHomeNewAdapter() {
//        home_newsRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布
        home_newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        if (homeNewsAdapter == null) {
            homeNewsAdapter = new HomeNewsAdapter(getContext(), home_newsList, new HomeNewsAdapter.OnClickToReadMore() {
                @Override
                public void toReadMore(int position) {
//                    ToastUtil.showShort(home_newsList.get(position).getTitle());
                    Intent intent = new Intent();
                    String url = home_newsList.get(position).getLink();
                    if (!url.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        intent.putExtras(bundle);
                        intent.setClass(getActivity(), LinkWebActivity.class);
                        getActivity().startActivity(intent);
                    }
                }
            });
        }
        home_newsRecycler.setAdapter(homeNewsAdapter);
    }

    /**
     * 获取首页新闻资讯
     *
     * @param nowpage 页码
     * @param type    （0，1）刷新or加载更多
     */
    private void getHomeNews(final int nowpage, final int type) {
        NoHttpManager.getNoHttpManager().getHomeNews(UrlContants.HOME_NEWS_LIST(nowpage), new OnHttpRequestListener<String>() {
            @Override
            public void onRequestSuccess(String msg, String s) {
                try {
                    List<Home_News> home_newss = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject object = jsonObject.getJSONObject("data");
                    page = object.getInt("curPage");
                    JSONArray jsonArray = object.getJSONArray("datas");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Home_News home_news = new Home_News();
                            JSONObject obj = jsonArray.getJSONObject(i);
                            home_news.setApkLink(obj.getString("apkLink"));
                            home_news.setAuthor(obj.getString("author"));
                            home_news.setChapterId(obj.getString("chapterId"));
                            home_news.setChapterName(obj.getString("chapterName"));
                            home_news.setCollect(obj.getBoolean("collect"));
                            home_news.setCourseId(obj.getString("courseId"));
                            home_news.setDesc(obj.getString("desc"));
                            home_news.setEnvelopePic(obj.getString("envelopePic"));
                            home_news.setFresh(obj.getBoolean("fresh"));
                            home_news.setId(obj.getString("id"));
                            home_news.setLink(obj.getString("link"));
                            home_news.setNiceDate(obj.getString("niceDate"));
                            home_news.setOrigin(obj.getString("origin"));
                            home_news.setProjectLink(obj.getString("projectLink"));
                            home_news.setPublishTime(obj.getString("publishTime"));
                            home_news.setSuperChapterId(obj.getString("superChapterId"));
                            home_news.setSuperChapterName(obj.getString("superChapterName"));
                            home_news.setTitle(obj.getString("title"));
                            home_news.setType(obj.getString("type"));
                            int visible = obj.getInt("visible");
                            if (visible == 1) {
                                home_news.setVisible(true);
                            } else {
                                home_news.setVisible(false);
                            }
                            home_news.setZan(obj.getInt("zan"));
                            if (home_news.isVisible()) {
                                home_newss.add(home_news);
                            }
                        }
                    }
                    if (type == 0) {
                        if (home_newsList.size() > 0) {
                            home_newsList.clear();
                        }
                        home_newsList.addAll(home_newss);
                    } else {
                        home_newsList.addAll(home_newss);
                    }
//                    if (getInstance().isAdded()){
                    if (!home_newsList.isEmpty()) {
                        home_newsRecycler.setVisibility(View.VISIBLE);
                        goTop.setVisibility(View.VISIBLE);
                        news_loadding.setVisibility(View.GONE);
                        nodata.setVisibility(View.GONE);
                    } else {
                        home_newsRecycler.setVisibility(View.GONE);
                        goTop.setVisibility(View.GONE);
                        news_loadding.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                    homeNewsAdapter.setDataList(home_newss);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (getInstance().isVisible()) {
                        home_newsRecycler.setVisibility(View.GONE);
                        goTop.setVisibility(View.GONE);
                        news_loadding.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onRequestFail(String msg) {
                if (getInstance().isVisible()) {
                    home_newsRecycler.setVisibility(View.GONE);
                    goTop.setVisibility(View.GONE);
                    news_loadding.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
