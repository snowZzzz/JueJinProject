package com.nuggetsera.activity.my;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.presenter.impl
 *  @文件名:   MyPresenterImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/21 10:37
 *  @描述：    "我的"页面presenter接口实现类
 */


public class MyPresenterImpl implements MyPresenter {

    private MyActivity mMyActivity;

    public MyPresenterImpl(MyActivity myActivity) {
        mMyActivity = myActivity;
    }
}
