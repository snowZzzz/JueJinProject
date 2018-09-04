package com.nuggetsera.activity.login;

import com.example.goldlibrary.base.BaseModel;

/**
 * Created by zhangzz on 2018/8/30
 */
public class LoginBean extends BaseModel {
    /**
     * id : 1
     * gender : 0
     * idNo : 890277288
     * mobilePhone : 18813918090
     * nickname : KaKaXi
     * username : 18813918090
     * headPortrait : d:/file/20180831_104251_hPzH.jpg
     * token : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTM1OTM5OTY5LCJzdWIiOiJ5dW55bEpKU0QiLCJpc3MiOiIxODgxMzkxODA5MCIsImV4cCI6MTUzNjI5OTk2OX0.8vX-XlV54UuMKmm2AKkio-tCRxqjTQD7xfpgvFcueM8
     * auditstatus : null
     */

    private int id;
    private int gender;
    private String idNo;
    private String mobilePhone;
    private String nickname;
    private String username;
    private String headPortrait;
    private String token;
    private Object auditstatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(Object auditstatus) {
        this.auditstatus = auditstatus;
    }
}
