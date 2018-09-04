package com.nuggetsera.activity.registered.next;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.presenter.impl
 *  @文件名:   RegisteredNextPresenterImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/21 10:17
 *  @描述：    注册第二个界面presenter接口的实现类
 */

import com.example.goldlibrary.base.BaseModel;
import com.example.goldlibrary.contants.APIConfig;
import com.example.goldlibrary.http.OnHttpResponseListener;
import com.example.goldlibrary.http.SEHttpManager;
import com.nuggetsera.base.Constants;

import java.util.HashMap;

public class RegisteredNextPresenterImpl
        implements RegisteredNextPresenter
{
    private RegisteredNextView mRegisteredNextView;

    public RegisteredNextPresenterImpl(RegisteredNextView registeredNextView) {
        mRegisteredNextView = registeredNextView;
    }

    @Override
    public void toRegister(String mobilePhone, String headPortrait, String nickname, String invitationCode, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilePhone", mobilePhone);
        map.put("headPortrait", headPortrait);
        map.put("nickname", nickname);
        map.put("invitationCode", invitationCode);
        map.put("password", password);

        SEHttpManager.getInstance().executePostMothed(map, APIConfig.getApiConfig(APIConfig.EnumAPI.REGISTER), Constants.REQ_REGISTER, null, new OnHttpResponseListener() {
            @Override
            public void onHttpRequestSuccess(int requestCode, BaseModel responseModel, String resultData) {
                if (mRegisteredNextView != null) {
                    mRegisteredNextView.setSuccess(requestCode, responseModel, resultData);
                }
            }

            @Override
            public void onHttpRequestError(int requestCode, String errorMsg) {
                if (mRegisteredNextView != null) {
                    mRegisteredNextView.setError(requestCode, errorMsg);
                }
            }
        });
    }

    /**
     * 头像图片上传调用
     * @param picUrl
     */
    @Override
    public void upLoadFile(String picUrl) {
        HashMap<String, String> map = new HashMap<>();
        map.put("pic_url", picUrl);

        SEHttpManager.getInstance().executePostFileMothed(map, APIConfig.getApiConfig(APIConfig.EnumAPI.UPLOADFILE), Constants.REQ_FILE_UPLOAD, FileUploadBean.class, new OnHttpResponseListener() {
            @Override
            public void onHttpRequestSuccess(int requestCode, BaseModel responseModel, String resultData) {
                if (mRegisteredNextView != null) {
                    mRegisteredNextView.setSuccess(requestCode, responseModel, resultData);
                }
            }

            @Override
            public void onHttpRequestError(int requestCode, String errorMsg) {
                if (mRegisteredNextView != null) {
                    mRegisteredNextView.setError(requestCode, errorMsg);
                }
            }
        });
    }
}
