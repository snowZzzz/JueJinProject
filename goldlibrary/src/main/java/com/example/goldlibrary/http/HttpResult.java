package com.example.goldlibrary.http;

import com.example.goldlibrary.base.BaseModel;

/**
 * HTTP返回结果类
 *
 * Created by zhangzezhen on 18/8/29.
 */
public class HttpResult {
    private int requestCode;//请求码
    private BaseModel responseModel;//返回的解析对象
    private String resultContent;//返回的json内容
    private String responseCode;//返回码,如果返回null，则代表异常

    private HttpResult() {
    }

    public static HttpResult putSuccessResult(int requestCode, BaseModel responseModel, String resultContent, String responseCode) {
        HttpResult httpResult = new HttpResult();
        httpResult.requestCode = requestCode;
        httpResult.responseModel = responseModel;
        httpResult.resultContent = resultContent;
        httpResult.responseCode = responseCode;
        return httpResult;
    }

    public static HttpResult putFailResult(int requestCode, String resultContent) {
        HttpResult httpResult = new HttpResult();
        httpResult.requestCode = requestCode;
        httpResult.responseModel = null;
        httpResult.resultContent = resultContent;
        httpResult.responseCode = null;
        return httpResult;
    }


    public int getRequestCode() {
        return requestCode;
    }

    public BaseModel getResponseModel() {
        return responseModel;
    }

    public String getResultContent() {
        return resultContent;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public boolean isError() {
        return responseCode == null;
    }
}