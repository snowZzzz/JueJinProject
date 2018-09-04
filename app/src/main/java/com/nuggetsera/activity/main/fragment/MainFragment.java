package com.nuggetsera.activity.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuggetsera.R;
import com.nuggetsera.base.BaseFragment;

/**
 * Created by zhangzz on 2018/8/31
 */
public class MainFragment extends BaseFragment {
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.activity_main,null);
    }

    @Override
    protected void initView() {

    }
}
