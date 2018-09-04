package com.nuggetsera.activity.start;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.view.activity
 *  @文件名:   StartActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 10:33
 *  @描述：    开始界面
 */

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.nuggetsera.R;
import com.nuggetsera.activity.login.LoginActivity;
import com.nuggetsera.base.BaseActivity;
import com.nuggetsera.utils.ThreadUtils;

public class StartActivity
        extends BaseActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
        super.init();

        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        goTo(LoginActivity.class);
                    }
                });
            }
        });
    }

}
