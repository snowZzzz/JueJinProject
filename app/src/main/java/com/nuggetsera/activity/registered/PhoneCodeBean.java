package com.nuggetsera.activity.registered;

import com.example.goldlibrary.base.BaseModel;

/**
 * Created by zhangzz on 2018/9/3
 * 获取验证码接口返回bean
 */
public class PhoneCodeBean extends BaseModel {
    private String phoneCode;
    private String failureTime;

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }
}
