package com.example.goldlibrary.contants;

import java.io.File;

/**
 * Created by zhangzezhen on 18/8/29.
 * library下的常量存放
 */

public class ConstantLibrary {
    /**
     * 请求类型-post请求
     */
    public static final int REQUEST_MOTHED_POST = 0;
    /**
     * 请求类型-get请求
     */
    public static final int REQUEST_MOTHED_GET = 1;

    /**
     * 请求类型-post上传文件请求
     */
    public static final int REQUEST_MOTHED_POST_FILE = 2;

    /**
     * 上传字符串的类型：json
     */
    public static final int TEXT_MEDIA_TYPE_JSON = 1;

    public static final String RESPONSE_CODE_SUCCESS = "200";//服务器响应码-返回成功
    public static final String RESPONSE_CODE__TOKEN_OUTTIME = "414";//服务器响应码-Token失效


    //发送广播的Action-Token失效
    public static final String BROADCAST_ACTION_TOKEN_TIMEOUT = "TOKEN_TIMEOUT";

    //Token失效的类型-Token失效
    public static final String TOKEN_INVALID_TYPE_OUT_TIME = "OUT_TIME";

    public static final String SYSTEM_ERROR="服务器异常,请联系管理员";
    public static final String TIME_OUT="请求超时";
    public static final String  DATD_ERROR="数据异常";
}
