package com.example.goldlibrary.http;


import com.example.goldlibrary.base.BaseModel;

/**
 * 数据返回的监听
 * Created by zhangzezhen on 18/8/29.
 */
public interface OnHttpResponseListener {
    /**
     * 网络请求成功
     *
     * @param requestCode 请求码
     * @param responseModel 解析的结果，可以为空
     * @param resultData  如果没有解析，则返回响应的json，否则返回null
     */
    void onHttpRequestSuccess(int requestCode, BaseModel responseModel, String resultData);

    /**
     * 网络请求失败，或者服务器处理失败
     *
     * @param requestCode 请求码
     * @param errorMsg   OKHTTP中请求异常
     */
    void onHttpRequestError(int requestCode, String errorMsg);
}