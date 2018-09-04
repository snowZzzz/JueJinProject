package com.nuggetsera.activity.mygoldpower;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nuggetsera.R;
import com.nuggetsera.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangzz on 2018/8/31
 */
public class MyGoldPowerAct extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_mygold_power_friend;
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
        mTvTitle.setText("我的掘金力");
    }
}
