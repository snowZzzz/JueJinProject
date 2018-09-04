package com.example.goldlibrary.http;

import com.example.goldlibrary.base.BaseModel;

import java.util.HashMap;

/**
 * Created by zhangzezhen on  18/8/29.
 */

public interface HttpManagerInterface {
    /**
     * post 执行
     *
     * @param map     参数列表
     * @param url           接口url
     * @param requestCode   请求码
     * @param listener      监听
     * @param tClass        返回的数据解析成的对象，可以为null，如果为null则是自己解析数据
     */
    void executePostMothed(final HashMap<String, String> map, final String url, final int requestCode, Class<? extends BaseModel> tClass, final OnHttpResponseListener listener);

    void executePostFileMothed(final HashMap<String, String> map, final String url, final int requestCode, Class<? extends BaseModel> tClass, final OnHttpResponseListener listener);


    /**
     * 同步执行请求
     */
    HttpResult executeSynchrPostMothed(HashMap<String, String> map, String url, int requestCode, Class<? extends BaseModel> tClass);

}
