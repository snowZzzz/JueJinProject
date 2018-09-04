package com.example.goldlibrary.http;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.example.goldlibrary.base.BaseApplication;
import com.example.goldlibrary.base.BaseModel;
import com.example.goldlibrary.contants.ConstantLibrary;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.exception.OkGoException;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by zhangzezhen on 18/8/29.
 * 网络请求外部使用类 目前只写了get 和 post方法 其他需要再添加
 */

public class SEHttpManager extends HttpManager {
    public final String TAG = this.getClass().getSimpleName();
    //单例
    private static SEHttpManager instance;

    private SEHttpManager(Context context) {
        super(context);
    }

    public static SEHttpManager getInstance() {
        if (instance == null) {
            instance = new SEHttpManager(BaseApplication.getContext());
        }
        return instance;
    }

    /**
     * post 同步执行
     * @param map         参数列表
     * @param url         接口url
     * @param requestCode 请求码
     * @param tClass      返回的数据解析成的对象，可以为null，如果为null则是自己解析数据
     */
    @Override
    public HttpResult executeSynchrPostMothed(HashMap<String, String> map, String url, int requestCode, Class<? extends BaseModel> tClass) {
        return executeSynch(map, url, requestCode, ConstantLibrary.REQUEST_MOTHED_POST, tClass);
    }

    /* 同步执行
     *
     * @param paramList     参数列表
     * @param url           接口url
     * @param requestCode   请求码
     * @param requestMothed 请求的类型:Post（@see REQUEST_MOTHED_POST）、get（@see REQUEST_MOTHED_GET）
     * @param tClass        返回的数据解析成的对象，可以为null，如果为null则是自己解析数据
     */
    private HttpResult executeSynch(HashMap<String, String> map, String url, final int requestCode, int requestMothed, final Class<? extends BaseModel> tClass) {
        Response response = null;
        HttpResult result = null;
        try {
            response = executeSynchRequestMap(requestCode, requestMothed, url, map);
            String responseContent = response.isSuccessful() ? response.body().string() : null;
            result = httpExecuteComplete(requestCode, responseContent, tClass, null);
        } catch (NetworkErrorException e) {
            result = HttpResult.putFailResult(requestCode, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            result = HttpResult.putFailResult(requestCode, e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return result;
    }


    /**
     * 异步网络操作
     **/
    @Override
    public void executePostMothed(HashMap<String, String> map, String url, int requestCode, Class<? extends BaseModel> tClass, OnHttpResponseListener listener) {
        executeAsynch(map, url, requestCode, ConstantLibrary.REQUEST_MOTHED_POST, tClass, listener);
    }

    @Override
    public void executePostFileMothed(final HashMap<String, String> map, String url, int requestCode, Class<? extends BaseModel> tClass, OnHttpResponseListener listener) {
        executeAsynch(map, url, requestCode, ConstantLibrary.REQUEST_MOTHED_POST_FILE, tClass, listener);
    }

    /**
     * 异步执行
     *
     * @param url           接口url
     * @param requestCode   请求码
     * @param requestMothed 请求的类型:Post（@see REQUEST_MOTHED_POST）、get（@see REQUEST_MOTHED_GET）
     * @param tClass        返回的数据解析成的对象，可以为null，如果为null则是自己解析数据
     * @param listener      监听
     */
    private void executeAsynch(HashMap<String, String> map, String url, final int requestCode, int requestMothed, final Class<? extends BaseModel> tClass, final OnHttpResponseListener listener) {
        try {
            executeAsynchRequestMap(requestCode, requestMothed, url, map, new StringCallback() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                    if (response.isSuccessful()) {
                        httpExecuteComplete(requestCode, response.body(), tClass, listener);
                    } else {
                        if (response.code() >= 400) {
                            listener.onHttpRequestError(requestCode, ConstantLibrary.SYSTEM_ERROR);
                        }
                    }
                }

                @Override
                public void onError(com.lzy.okgo.model.Response<String> response) {
                    String errorString = response.getException().getMessage();
                    KLog.e("httperror", errorString);
                    if (response.getException() != null && response.getException() instanceof OkGoException) {
                        errorString = response.getException().getMessage();
                    } else if (response.getException() != null && response.getException() instanceof SocketTimeoutException) {
                        listener.onHttpRequestError(requestCode, ConstantLibrary.TIME_OUT);
                    }

                    if (listener != null) {
                        listener.onHttpRequestError(requestCode, errorString);
                    }
                }
            });
        } catch (NetworkErrorException e) {
            if (listener != null) {
                listener.onHttpRequestError(requestCode, e.getMessage());
            }
        }
    }

    /**
     * 请求执行完成处理
     */
    public HttpResult httpExecuteComplete(int requestCode, String responseContent, Class<? extends BaseModel> tClass, OnHttpResponseListener listener) {
        KLog.e("HTTP_POST", "请求返回:\n" + responseContent);
        if (TextUtils.isEmpty(responseContent)) {
            if (listener != null) {
                listener.onHttpRequestError(requestCode, responseContent);
            }
            return HttpResult.putFailResult(requestCode, responseContent + " == null");
        }

        try {
            JSONObject jsonObject = new JSONObject(responseContent);

            String status = jsonObject.getString("message");
            String responseCode = jsonObject.getString("code");
            if (ConstantLibrary.RESPONSE_CODE_SUCCESS.equals(responseCode)) {
                if (tClass == null) {//不需要解析
                    if (listener != null) {
                        listener.onHttpRequestSuccess(requestCode, null, responseContent);
                    }
                    return HttpResult.putSuccessResult(requestCode, null, responseContent, responseCode);
                }
                //响应成功
                Gson gson = new Gson();
                JSONObject info = jsonObject.getJSONObject("data");
                BaseModel model = null;
                if (info != null) {
                    try {
                        model = gson.fromJson(info.toString(), tClass);
                        if (listener != null) {
                            listener.onHttpRequestSuccess(requestCode, model, responseContent);
                        }
                    } catch (JsonSyntaxException e) {
                        KLog.file(TAG, Environment.getExternalStorageDirectory(), "test.txt", responseContent);
                        if (listener != null) {
                            listener.onHttpRequestError(requestCode, ConstantLibrary.DATD_ERROR);
                        }
                        return HttpResult.putFailResult(requestCode, responseContent);
                    }
                    return HttpResult.putSuccessResult(requestCode, model, responseContent, responseCode);
                } else {
                    if (listener != null) {
                        listener.onHttpRequestError(requestCode, status);
                    }
                    return HttpResult.putFailResult(requestCode, responseContent);
                }
            } else {//请求码失败
                handleFailRequest(responseCode);

                if (listener != null) {
                    listener.onHttpRequestError(requestCode, status);
                }
                return HttpResult.putFailResult(requestCode, responseContent);
            }
        } catch (Exception e) {
            if (listener != null) {
                listener.onHttpRequestError(requestCode, ConstantLibrary.DATD_ERROR);
            }
            e.printStackTrace();
            return HttpResult.putFailResult(requestCode, responseContent + " \n" + e.toString());
        }
    }

    /**
     * 处理失败
     */
    private void handleFailRequest(String responseCode) {
        if (ConstantLibrary.RESPONSE_CODE__TOKEN_OUTTIME.equals(responseCode)) { //服务器响应码-Token过期 处理 具体看要求

        }
    }
}
