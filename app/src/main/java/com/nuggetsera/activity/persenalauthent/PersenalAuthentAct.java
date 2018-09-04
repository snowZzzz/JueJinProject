package com.nuggetsera.activity.persenalauthent;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.goldlibrary.utils.TabLayoutManager;
import com.nuggetsera.R;
import com.nuggetsera.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersenalAuthentAct extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.layout_tabs)
    TabLayout mLayoutTabs;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private List<String> mTitles;
    private List<CertificateFragment> mFragments;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_persenal_authent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.color_333333));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
    }

    @OnClick({R.id.layout_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back://返回按钮
                finish();
                break;
        }
    }

    private void initView() {
        mTvTitle.setText("个人认证");

        mTitles = new ArrayList<>();
        mTitles.add("中国大陆地区");
        mTitles.add("其他国家及地区");

        mFragments = new ArrayList<>();
        mFragments.add(new CertificateFragment());
        mFragments.add(new CertificateFragment());

        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setAdapter(new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

        mLayoutTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mLayoutTabs.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutManager.setIndicator(mLayoutTabs, 35, 35);
            }
        });
        mLayoutTabs.setupWithViewPager(mViewpager);
    }
}
