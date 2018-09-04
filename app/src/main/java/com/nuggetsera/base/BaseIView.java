package com.nuggetsera.base;

import com.example.goldlibrary.base.BaseModel;

/**
 * Created by zhangzezhen on 18/9/3.
 * view基类 一般页面都有通用的网络请求
 */

public interface BaseIView {
    void setError(int reqCode, String errorMsg);//后台返回的失败信息回调返回
    void setSuccess(int reqCode, BaseModel baseModel, String resultData);//成功后台返回的model 回调返回
}
