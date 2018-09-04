package com.example.goldlibrary.contants;


import com.example.goldlibrary.BuildConfig;

/**
 * Created by zhangzezhen on 18/8/29.
 */

public class APIConfig {
    private static final String SERVER_URL_UAT = "http://192.168.2.112:8000/";//测试环境
    private static final String SERVER_URL_RELEASE = "http://192.168.2.112:8000/";//发布环境

    public static String getApiConfig(EnumAPI enumAPI) {
        switch (enumAPI) {
            case LOGIN:
                return getServerUrl() + "jjsd/user/login";//登录接口
            case GETCODE:
                return getServerUrl() + "jjsd/sms/phoneCode";//获取验证码接口
            case REGISTER:
                return getServerUrl() + "jjsd/user/register";//注册接口
            case CHECKCODE:
                return getServerUrl() + "jjsd/sms/phoneCodeCheck";//检验验证码接口
            case UPLOADFILE:
                return getServerUrl() + "jjsd/file/upload";//文件上传接口
            default:
                return null;
        }

    }

    /**
     * 得到服务器的URL
     *
     * @return
     */
    public static String getServerUrl() {
        switch (BuildConfig.EVN) {
            case "evn_release":
                return SERVER_URL_RELEASE;
            case "evn_uat":
                return SERVER_URL_UAT;
            default:
                return SERVER_URL_UAT;
        }
    }

    public enum EnumAPI {
        LOGIN,
        GETCODE,
        REGISTER,
        CHECKCODE,
        UPLOADFILE,
    }
}
