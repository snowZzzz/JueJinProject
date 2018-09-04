package com.nuggetsera.activity.invitefriend;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.goldlibrary.utils.DataCacheUtil;
import com.nuggetsera.R;
import com.nuggetsera.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzz on 2018/8/27.
 * 邀请好友页面
 *
 */

public class InviteFriendActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_invite_friend;
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
        mTvTitle.setText("邀请好友");
    }
}
