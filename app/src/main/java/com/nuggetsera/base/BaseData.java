package com.nuggetsera.base;

/**
 * Created by zhangzz on 2018/9/3
 * 登录信息保存
 */
public class BaseData {
    private String userName;
    private String token;

    private static BaseData instance;

    public static final BaseData getInstance(){
        if (instance == null) {
            instance = new BaseData();
        }
        return instance;
    }

    public BaseData() {
        this.userName = "";
        this.token = "";
    }

    public void setBaseData(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
