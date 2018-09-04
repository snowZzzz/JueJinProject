package com.nuggetsera.activity.registered.next;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.presenter
 *  @文件名:   RegisteredNextPresenter
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/21 10:16
 *  @描述：    注册第二个界面的presenter
 */

public interface RegisteredNextPresenter {
    void toRegister(String mobilePhone, String headPortrait, String nickname, String invitationCode, String password);
    void upLoadFile(String picUrl);
}
