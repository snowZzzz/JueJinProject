package com.nuggetsera.activity.registered;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.presenter
 *  @文件名:   RegisteredPresenter
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/21 10:00
 *  @描述：    注册第一个界面的presenter接口
 */

public interface RegisteredPresenter {
    void toGetCode(String mobilePhone);
    void toCheckCode(String mobilePhone, String phoneCode, String type);//type 1注册 2登录 3修改密码 4 认证 5 修改手机 6其他
}
