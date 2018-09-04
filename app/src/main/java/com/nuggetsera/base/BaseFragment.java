package com.nuggetsera.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangzz on 2018/8/31
 */
public abstract class BaseFragment extends Fragment {
    protected View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = initContentView(inflater, container, savedInstanceState);
        if (null == fragmentView) {
            throw new RuntimeException("根布局不能为空!");
        }
        initView();
        return fragmentView;

    }

    /**
     * 设置界面layout
     */
    protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initView();
}
