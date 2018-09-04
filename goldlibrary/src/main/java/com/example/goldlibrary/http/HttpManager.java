package com.example.goldlibrary.http;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.example.goldlibrary.R;
import com.example.goldlibrary.contants.ConstantLibrary;
import com.example.goldlibrary.utils.CommonUtil;
import com.example.goldlibrary.utils.StringUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import okhttp3.Response;


/**
 * Created by zhangzezhen on 18/8/29.
 * <p>
 * 网络请求：请求、返回-》成功或者失败处理类
 */

public abstract class HttpManager implements HttpManagerInterface {
    public static final int REQUEST_MOTHED_POST = 0;//请求类型-post请求

    public static final int REQUEST_MOTHED_GET = 1;//请求类型-get请求

    public static final int REQUEST_MOTHED_POST_FILE = 2;//请求类型-post文件请求

    public final String TAG = this.getClass().getSimpleName();

    private Context context;

    public HttpManager(Context context) {
        this.context = context;
    }

    /**
     * 同步执行网络请求
     *
     * @param requestCode   请求码
     * @param requestMothed 请求的类型:Post
     * @throws NetworkErrorException 无网络异常
     */
    public Response executeSynchRequestMap(int requestCode, int requestMothed, String url, HashMap<String, String> map) throws NetworkErrorException, IOException {
        return executeSynchRequest(requestCode, requestMothed, url, null, 0, map);
    }

    /**
     * 同步执行网络请求
     */
    private Response executeSynchRequest(int requestCode, int requestMothed, String url, String content, int textMediaType,
                                         HashMap<String, String> map) throws NetworkErrorException, IOException {
        if (!CommonUtil.isNetworkAvailable(context)) {
            throw new NetworkErrorException(context.getResources().getString(R.string.msg_network_is_not_available));
        }

        Request request;
        if (requestMothed == REQUEST_MOTHED_GET) {
            request = executeAsynchGet(requestCode, url, map);
        } else {
            request = executeAsynchPost(requestCode, url, content, textMediaType, map, requestMothed);
        }

        return request.execute();
    }


    /**
     * 异步执行网络请求
     */
    public void executeAsynchRequestMap(int requestCode, int requestMothed, String url, Map<String, String> paramList, AbsCallback callback) throws NetworkErrorException {
        executeAsynchRequest(requestCode, requestMothed, url, null, 0, paramList, callback);
    }

    /**
     * 异步执行网络请求
     *
     * @param requestCode   请求码
     * @param requestMothed 请求的类型:Post（@see REQUEST_MOTHED_POST）、get（@see REQUEST_MOTHED_GET）
     * @param url           请求的URL
     * @param content       上传的文本
     * @param textMediaType 文本类型：普通文本（@see TEXT_MEDIA_TYPE_PLAIN）、json（@see TEXT_MEDIA_TYPE_JSON）
     * @param paramList     参数列表
     * @param callback      返回监听
     * @throws NetworkErrorException 无网络异常
     */
    private void executeAsynchRequest(int requestCode, int requestMothed, String url, String content, int textMediaType,
                                      Map<String, String> paramList, AbsCallback callback) throws NetworkErrorException {
        if (!CommonUtil.isNetworkAvailable(context)) {
            throw new NetworkErrorException(context.getResources().getString(R.string.msg_network_is_not_available));
        }

        Request request;
        if (requestMothed == REQUEST_MOTHED_GET) {
            request = executeAsynchGet(requestCode, url, paramList);
        } else{
            request = executeAsynchPost(requestCode, url, content, textMediaType, paramList, requestMothed);
        }
        request.execute(callback);
    }

    /**
     * 异步执行Post方法
     *
     * @param requestCode   请求码
     * @param url           接口url
     * @param content       上传的文本
     * @param textMediaType 文本类型：普通文本（@see TEXT_MEDIA_TYPE_PLAIN）、json（@see TEXT_MEDIA_TYPE_JSON）
     */
    private Request executeAsynchPost(int requestCode, String url, String content, int textMediaType, Map<String, String> map, int requestMothed) {
        KLog.e("HTTP_POST：", "请求地址:\n" + url);
        PostRequest request = OkGo.post(url);
        request.headers(getHeaders());
        request.tag(requestCode);
        if (requestMothed == REQUEST_MOTHED_POST_FILE){
            request.params("file", new File(map.get("pic_url")));
        } else {
            if (map != null) {
                String jsonStr = new Gson().toJson(map);
                request.upJson(jsonStr);
//            request.params(paramList, false);
                KLog.json("HTTP_POST：", "请求入参:\n" + jsonStr);
            } else {
                if (textMediaType == ConstantLibrary.TEXT_MEDIA_TYPE_JSON) {
                    request.upJson(content);
                } else {
                    request.upString(content);
                }
            }
        }
        return request;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders heads = new HttpHeaders();
        heads.put("token", "");
        KLog.e("HTTP_POST：", "请求headers:\n" + heads.toString());
        return heads;
    }

    /**
     * 异步执行Get方法
     *
     * @param requestCode 请求码
     * @param url         接口url
     * @param paramList   参数列表
     */
    private Request executeAsynchGet(int requestCode, String url, Map<String, String> paramList) {
        return OkGo.get(getUrl(url, paramList)).tag(requestCode);
    }


    /**
     * get请求时，拼装URL
     */
    private String getUrl(String url, Map<String, String> paramList) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.getNoBlankString(url));
        if (paramList != null) {
            Set<String> set = paramList.keySet();
            int index = 0;
            for (String key : set) {
                sb.append(index <= 0 ? "?" : "&");
                sb.append(StringUtil.getTrimedString(key));
                sb.append("=");
                sb.append(StringUtil.getTrimedString(paramList.get(key)));
                index++;
            }
        }
        return sb.toString();
    }
}
