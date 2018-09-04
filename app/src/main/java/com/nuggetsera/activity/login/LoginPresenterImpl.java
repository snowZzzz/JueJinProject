package com.nuggetsera.activity.login;

/*
 *  @项目名：  NuggetsEra
 *  @包名：    com.nuggetsera.presenter.impl
 *  @文件名:   LoginPresenterImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 16:13
 *  @描述：    登录界面的Presenter接口的实现类
 */

import com.example.goldlibrary.base.BaseModel;
import com.example.goldlibrary.contants.APIConfig;
import com.example.goldlibrary.http.OnHttpResponseListener;
import com.example.goldlibrary.http.SEHttpManager;
import com.nuggetsera.base.BaseData;
import com.nuggetsera.base.Constants;

import java.util.HashMap;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void toLogin(String mobilePhone, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilePhone", mobilePhone);
        map.put("password", password);

        SEHttpManager.getInstance().executePostMothed(map, APIConfig.getApiConfig(APIConfig.EnumAPI.LOGIN), Constants.REQ_LOGIN, LoginBean.class, new OnHttpResponseListener() {
            @Override
            public void onHttpRequestSuccess(int requestCode, BaseModel responseModel, String resultData) {
                if (mLoginView != null) {
                    mLoginView.setSuccess(requestCode, responseModel, resultData);
                    if (responseModel != null) {
                        BaseData.getInstance().setBaseData(((LoginBean) responseModel).getUsername(), ((LoginBean) responseModel).getToken());
                    }
                }
            }

            @Override
            public void onHttpRequestError(int requestCode, String errorMsg) {
                mLoginView.setError(requestCode, errorMsg);
            }
        });
    }
}
