package com.nuggetsera.base;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.view.activity
 *  @文件名:   BaseActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 10:22
 *  @描述：
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nuggetsera.utils.AndroidWorkaround;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局ID
        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }

//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {        //适配华为手机虚拟键遮挡tab的问题
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));    //需要在setContentView()方法后面执行
//        }

        init();

    }

    //公共初始化方法
    protected void init() {
    }

    //获取到布局ID
    public abstract int getLayoutResId() ;

    //跳转界面
    protected void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
//        finish();
    }


}
