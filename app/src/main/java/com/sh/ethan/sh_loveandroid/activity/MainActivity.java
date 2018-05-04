package com.sh.ethan.sh_loveandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sh.ethan.sh_loveandroid.R;
import com.sh.ethan.sh_loveandroid.appUtils.ToastUtil;
import com.sh.ethan.sh_loveandroid.base.LoveAndroidActivity;
import com.sh.ethan.sh_loveandroid.beans.Home_Banner_Bean;
import com.sh.ethan.sh_loveandroid.constant.UrlContants;
import com.sh.ethan.sh_loveandroid.fragment.HomeNewsFragment;
import com.sh.ethan.sh_loveandroid.http.NoHttpManager;
import com.sh.ethan.sh_loveandroid.http.OnHttpRequestListener;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ethan on 2018/4/16.
 * 主页
 */
public class MainActivity extends LoveAndroidActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<Home_Banner_Bean> home_banner_beans = new ArrayList<>();
    private List<Fragment> loveAndroidFragments = new ArrayList<>();
    private String[] tabTitle = {"推荐", "UI", "闲谈", "iOS", "Android"};
    @BindView(R.id.home_banner)
    Banner home_banner;
    @BindView(R.id.home_tab)
    TabLayout home_tab;
    @BindView(R.id.home_viewpager)
    ViewPager home_viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.bannerLoadding)
    AVLoadingIndicatorView bannerLoadding;
    @BindView(R.id.nodata)
    ImageView nodata;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initIntentData(Bundle bundle) {

    }


    @Override
    protected void doOperateOnCreate() {
        initFragments();
        initHomeTab();
        showViewPager();
        getHome_Banner();
        initToolBar();
    }

    @Override
    protected void doOperateOnResume() {
        setBarColor(R.color.toolBar);
    }

    private void initToolBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void showViewPager() {
        FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return loveAndroidFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return loveAndroidFragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitle[position % tabTitle.length];
            }
        };
        home_viewpager.setAdapter(pagerAdapter);
        home_tab.setupWithViewPager(home_viewpager);
    }

    private void initHomeTab() {
        for (int i = 0; i < loveAndroidFragments.size(); i++) {
            home_tab.addTab(home_tab.newTab().setText(tabTitle[i]));
        }
        home_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initFragments() {
        loveAndroidFragments.add(new HomeNewsFragment());
        loveAndroidFragments.add(new HomeNewsFragment());
        loveAndroidFragments.add(new HomeNewsFragment());
        loveAndroidFragments.add(new HomeNewsFragment());
        loveAndroidFragments.add(new HomeNewsFragment());
    }

    /**
     * 配置并加载banner图
     */
    private void initBanner(List<Home_Banner_Bean> home_banner_beans) {
        if (!home_banner_beans.isEmpty()) {
            home_banner.setVisibility(View.VISIBLE);
            bannerLoadding.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            List<String> bannerTitles = new ArrayList<>();
            List<String> bannerImages = new ArrayList<>();
            for (int i = 0; i < home_banner_beans.size(); i++) {
                bannerTitles.add(i, home_banner_beans.get(i).getTitle());
                bannerImages.add(i, home_banner_beans.get(i).getImagePath());
            }
            home_banner.setBannerTitles(bannerTitles);
            home_banner.setImages(bannerImages);
            //设置banner样式
            home_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器
            home_banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    String url = (String) path;
                    Picasso.with(context).load(url).placeholder(R.mipmap.loadding_hor).error(R.mipmap.news_no_img).into(imageView);
                }
            });
            //设置banner动画效果
            home_banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            home_banner.isAutoPlay(true);
            //设置轮播时间
            home_banner.setDelayTime(10000);
            //设置指示器位置（当banner模式中有指示器时）
            home_banner.setIndicatorGravity(BannerConfig.CENTER);
            home_banner.start();
            home_banner.startAutoPlay();
        } else {
            home_banner.setVisibility(View.GONE);
            bannerLoadding.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取首页banner图
     */
    private void getHome_Banner() {
        NoHttpManager.getNoHttpManager().getHomeBanner(UrlContants.HOME_BANNER, new OnHttpRequestListener<String>() {
            @Override
            public void onRequestSuccess(String msg, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0 && home_banner_beans.size() > 0) {
                        home_banner_beans.clear();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Home_Banner_Bean home_banner_bean = new Home_Banner_Bean();
                        home_banner_bean.setDesc(object.getString("desc"));
                        home_banner_bean.setId(object.getString("id"));
                        home_banner_bean.setImagePath(object.getString("imagePath"));
                        int visible = object.getInt("isVisible");
                        if (visible == 1) {
                            home_banner_bean.setIsVisible(true);
                        } else {
                            home_banner_bean.setIsVisible(false);
                        }
                        home_banner_bean.setOrder(object.getInt("order"));
                        home_banner_bean.setTitle(object.getString("title"));
                        home_banner_bean.setType(object.getInt("type"));
                        home_banner_bean.setUrl(object.getString("url"));
                        if (home_banner_bean.getIsVisible()) {
                            home_banner_beans.add(home_banner_bean);
                        }
                    }
                    initBanner(home_banner_beans);
                } catch (JSONException e) {
                    e.printStackTrace();
                    home_banner.setVisibility(View.GONE);
                    bannerLoadding.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onRequestFail(String msg) {
                home_banner.setVisibility(View.GONE);
                bannerLoadding.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        home_banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        home_banner.stopAutoPlay();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myCollect) {
            ToastUtil.showShort("我的收藏");
            // Handle the camera action
        } else if (id == R.id.about) {
            ToastUtil.showShort("关于我们");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
