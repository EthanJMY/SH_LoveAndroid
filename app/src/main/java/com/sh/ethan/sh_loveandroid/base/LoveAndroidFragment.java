package com.sh.ethan.sh_loveandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sh.ethan.sh_loveandroid.appUtils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ethan on 2018/4/22.
 */
public abstract class LoveAndroidFragment extends Fragment {

    protected abstract int inflateLayout();//填充布局

    private Unbinder unbinder;

    protected abstract void initView(View view);

    protected abstract void doOperateOnResume();

    protected abstract void doOperateOnViewCreated();

    protected LoveAndroidActivity bindActivity;
    private View rootView;
    protected boolean isPrepared;
    protected boolean isFirstVisible;

    private WindowManager windowManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(inflateLayout(), container, false);
            isPrepared = true;
            isFirstVisible = true;
        } else {
            isFirstVisible = false;
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        unbinder = ButterKnife.bind(this, rootView);
        bindActivity = (LoveAndroidActivity) getActivity();
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        doOperateOnViewCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        doOperateOnResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected LoveAndroidActivity getParent() {
        return bindActivity;
    }

    protected FragmentTransaction getTranscation() {
        return bindActivity.getSupportFragmentManager().beginTransaction();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
