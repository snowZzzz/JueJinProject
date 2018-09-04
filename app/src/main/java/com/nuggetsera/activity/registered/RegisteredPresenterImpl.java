package com.nuggetsera.activity.registered;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.presenter.impl
 *  @文件名:   RegisteredPresenterImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/21 10:01
 *  @描述：    注册第一个界面presenter接口的实现类
 */

import com.example.goldlibrary.base.BaseModel;
import com.example.goldlibrary.contants.APIConfig;
import com.example.goldlibrary.http.OnHttpResponseListener;
import com.example.goldlibrary.http.SEHttpManager;
import com.nuggetsera.base.Constants;

import java.util.HashMap;

public class RegisteredPresenterImpl implements RegisteredPresenter {

    private RegisteredView mRegisterdView;

    public RegisteredPresenterImpl(RegisteredView registeredView) {
        mRegisterdView = registeredView;
    }

    @Override
    public void toGetCode(String mobilePhone) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilePhone", mobilePhone);

        SEHttpManager.getInstance().executePostMothed(map, APIConfig.getApiConfig(APIConfig.EnumAPI.GETCODE), Constants.REQ_GETCODE, PhoneCodeBean.class, new OnHttpResponseListener() {
            @Override
            public void onHttpRequestSuccess(int requestCode, BaseModel responseModel, String resultData) {
                if (mRegisterdView != null) {
                    mRegisterdView.setSuccess(requestCode, responseModel, resultData);
                }
            }

            @Override
            public void onHttpRequestError(int requestCode, String errorMsg) {
                if (mRegisterdView != null) {
                    mRegisterdView.setError(requestCode, errorMsg);
                }
            }
        });
    }

    @Override
    public void toCheckCode(String mobilePhone, String phoneCode, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilePhone", mobilePhone);
        map.put("phoneCode", phoneCode);
        map.put("type", type);

        SEHttpManager.getInstance().executePostMothed(map, APIConfig.getApiConfig(APIConfig.EnumAPI.GETCODE), Constants.REQ_CHECKCODE, null, new OnHttpResponseListener() {
            @Override
            public void onHttpRequestSuccess(int requestCode, BaseModel responseModel, String resultData) {
                if (mRegisterdView != null) {
                    mRegisterdView.setSuccess(requestCode, responseModel, resultData);
                }
            }

            @Override
            public void onHttpRequestError(int requestCode, String errorMsg) {
                if (mRegisterdView != null) {
                    mRegisterdView.setError(requestCode, errorMsg);
                }
            }
        });
    }
}
