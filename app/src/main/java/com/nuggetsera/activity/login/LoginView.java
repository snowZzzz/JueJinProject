package com.nuggetsera.activity.login;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.view
 *  @文件名:   LoginView
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 16:01
 *  @描述：    登录界面的view
 */

import com.nuggetsera.base.BaseIView;

public interface LoginView extends BaseIView{
    void showLoading(int requsetCode);
    void hideLoading(int requsetCode);
}
