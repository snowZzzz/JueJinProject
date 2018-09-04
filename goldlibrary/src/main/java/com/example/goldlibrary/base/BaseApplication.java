package com.example.goldlibrary.base;

import android.app.Application;
import android.content.Context;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by zhangzezhen on 18/8/29.
 */

public abstract class BaseApplication extends Application {

    public static Context getContext() {
        return context;
    }

    /**
     * 全局context的上下文
     */
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getBaseContext();

        initOkGo();
    }

    /**
     * Okgo初始化
     */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //超时时间设置，默认30秒
        builder.readTimeout(30000L, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(30000L, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(10000L, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);
    }


}
