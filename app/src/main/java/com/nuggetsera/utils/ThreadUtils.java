package com.nuggetsera.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Leon on 2017/1/7.
 */

public class ThreadUtils {

    private static Handler sHandler = new Handler(Looper.getMainLooper());//绑定主线程Looper,handler处理消息就在主线程

    //单线程线程池
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    /**
     * 在子线程执行
     * @param runnable
     */
    public static void runOnBackgroundThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    /**
     * 在主线程执行runnable
     * @param runnable
     */
    public static void runOnMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }
}