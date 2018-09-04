package com.example.updateapplibrary.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.example.updateapplibrary.R;
import com.example.updateapplibrary.UpdateConfig;
import com.example.updateapplibrary.callback.UpdateCallback;
import com.example.updateapplibrary.constant.UpdateAppConstants;
import com.example.updateapplibrary.http.AppUpHttpManager;
import com.example.updateapplibrary.http.IAPPUPHttpManager;
import com.example.updateapplibrary.util.AppUtils;

import java.io.File;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DownloadService extends Service {

    private DownloadBinder mDownloadBinder = new DownloadBinder();
    /**
     * 是否在下载，防止重复下载。
     */
    private boolean isDownloading;
    /**
     * 最后更新进度，用来降频刷新
     */
    private int mLastProgress = -1;
    /**
     * 最后进度更新时间，用来降频刷新
     */
    private long mLastTime;
    /**
     * 失败后重新下载次数
     */
    private int mCount = 0;

    private Context getContext(){
        return this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null){
            if(!isDownloading){
                boolean isStop = intent.getBooleanExtra(UpdateAppConstants.KEY_STOP_DOWNLOAD_SERVICE,false);
                if(isStop){
                    stopService();
                }else{
                    //是否实通过通知栏触发重复下载
                    boolean isReDownload = intent.getBooleanExtra(UpdateAppConstants.KEY_RE_DOWNLOAD,false);
                    if(isReDownload){
                        mCount++;
                    }
                    //获取配置信息
                    UpdateConfig config =  intent.getParcelableExtra(UpdateAppConstants.KEY_UPDATE_CONFIG);
                    startDownload(config,null,null);
                }
            }else{
                Log.w(UpdateAppConstants.TAG,"已经在下载中,请勿重复下载。");
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }


    //----------------------------------------

    /**
     * 开始下载
     * @param config
     * @param httpManager
     * @param callback
     */
    public void startDownload(UpdateConfig config, IAPPUPHttpManager httpManager, UpdateCallback callback){

        if(config == null){
            return;
        }

        if(callback!=null){
            callback.onDownloading(isDownloading);
        }

        if(isDownloading){
            Log.w(UpdateAppConstants.TAG,"已经在下载中,请勿重复下载。");
            return;
        }

        String url = config.getUrl();
        String path = config.getPath();
        String filename = config.getFilename();

        //如果保存路径为空则使用缓存路径
        if(TextUtils.isEmpty(path)){
            path = getDiskCacheDir(getContext());
        }
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

        //如果文件名为空则使用路径
        if(TextUtils.isEmpty(filename)){
            filename = AppUtils.INSTANCE.getAppFullName(getContext(),url,getResources().getString(R.string.app_name));
        }

        File file = new File(path,filename);
        if(file.exists()){
            file.delete();
        }

        if(httpManager != null){
            httpManager.download(url,path,filename,new AppDownloadCallback(config,callback));
        }else{
            AppUpHttpManager.getInstance().download(url,path,filename,new AppDownloadCallback(config,callback));
        }


    }

    /**
     * 获取缓存路径
     * @param context
     * @return
     */
    public String getDiskCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return UpdateAppConstants.DEFAULT_DIR_PATH;
        }

        return context.getCacheDir().getAbsolutePath();
    }

    private void stopService(){
        mCount = 0;
        stopSelf();
    }


    //---------------------------------------- DownloadCallback

    public class AppDownloadCallback implements IAPPUPHttpManager.DownloadCallback{

        public UpdateConfig config;

        private boolean isShowNotification;

        private int notifyId;

        private String channelId;

        private String channelName;

        private int notificationIcon;

        private boolean isInstallApk;

        private String authority;

        private boolean isShowPercentage;

        private boolean isReDownload;

        private UpdateCallback callback;


        private AppDownloadCallback(UpdateConfig config,UpdateCallback callback){
            this.config = config;
            this.callback = callback;
            this.isShowNotification = config.isShowNotification();
            this.notifyId = config.getNotificationId();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                this.channelId = TextUtils.isEmpty(config.getChannelId()) ? UpdateAppConstants.DEFAULT_NOTIFICATION_CHANNEL_ID : config.getChannelId();
                this.channelName = TextUtils.isEmpty(config.getChannelName()) ? UpdateAppConstants.DEFAULT_NOTIFICATION_CHANNEL_NAME : config.getChannelName();
            }
            if(config.getNotificationIcon()<=0){
                this.notificationIcon = AppUtils.INSTANCE.getAppIcon(getContext());
            }else{
                this.notificationIcon = config.getNotificationIcon();
            }

            this.isInstallApk = config.isInstallApk();
            if(TextUtils.isEmpty(config.getAuthority())){//如果为空默认
                authority = getContext().getPackageName() + ".fileProvider";
            }else{
                this.authority = config.getAuthority();
            }

            this.isShowPercentage = config.isShowPercentage();
            this.isReDownload = config.isReDownload();

        }


        @Override
        public void onStart(String url) {
            isDownloading = true;
            mLastProgress = 0;
            if(isShowNotification){
                showStartNotification(notifyId,channelId,channelName,notificationIcon,getString(R.string.app_updater_start_notification_title),getString(R.string.app_updater_start_notification_content));
            }

            if(callback!=null){
                callback.onStart(url);
            }
        }

        @Override
        public void onProgress(int progress, int total) {

            boolean isChange = false;
            long curTime = System.currentTimeMillis();
            if(mLastTime + 200 < curTime) {//降低更新频率
                mLastTime = curTime;

                int currProgress = Math.round(progress * 1.0f / total * 100.0f);
                if(currProgress!=mLastProgress){//百分比改变了才更新
                    isChange = true;
                    String percentage = currProgress + "%";
                    if(isShowNotification) {
                        mLastProgress = currProgress;
                        String content = getString(R.string.app_updater_progress_notification_content);
                        if (isShowPercentage) {
                            content += percentage;
                        }

                        showProgressNotification(notifyId, channelId, notificationIcon, getString(R.string.app_updater_progress_notification_title), content, progress, total);

                    }
                }
            }

            if(callback!=null){
                callback.onProgress(progress,total,isChange);
            }
        }

        @Override
        public void onFinish(File file) {
            isDownloading = false;
            showFinishNotification(notifyId,channelId,notificationIcon,getString(R.string.app_updater_finish_notification_title),getString(R.string.app_updater_finish_notification_content),file,authority);
            if(isInstallApk){
                AppUtils.INSTANCE.installApk(getContext(),file,authority);
            }
            if(callback!=null){
                callback.onFinish(file);
            }
            stopService();
        }

        @Override
        public void onError(Exception e) {
            isDownloading = false;
            //支持下载失败重新并最多支持失败下载3次
            boolean isReDownload = this.isReDownload && mCount < 3;
            String content = isReDownload ? getString(R.string.app_updater_error_notification_content_re_download) : getString(R.string.app_updater_error_notification_content);
            showErrorNotification(notifyId,channelId,notificationIcon,getString(R.string.app_updater_error_notification_title),content,isReDownload,config);

            if(callback!=null){
                callback.onError(e);
            }
            if(!isReDownload){
                stopService();
            }

        }

        @Override
        public void onCancel() {
            isDownloading = false;
            cancelNotification(notifyId);
            if(callback!=null){
                callback.onCancel();
            }
            stopService();
        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(UpdateAppConstants.TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        isDownloading = false;
        Log.d(UpdateAppConstants.TAG,"onDestroy");
        super.onDestroy();
    }

    //---------------------------------------- Notification

    /**
     * 显示开始下载是的通知
     * @param notifyId
     * @param channelId
     * @param channelName
     * @param icon
     * @param title
     * @param content
     */
    private void showStartNotification(int notifyId, String channelId, String channelName, @DrawableRes int icon, CharSequence title, CharSequence content){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(channelId,channelName);
        }
        NotificationCompat.Builder builder = buildNotification(channelId,icon,title,content);
        builder.setAutoCancel(false);
        notifyNotification(notifyId,builder.build());
    }

    /**
     * 显示下载中的通知（更新进度）
     * @param notifyId
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @param progress
     * @param size
     */
    private void showProgressNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, int progress, int size){
        NotificationCompat.Builder builder = buildNotification(channelId,icon,title,content,progress,size);
        builder.setAutoCancel(false);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
        notifyNotification(notifyId,notification);
    }

    /**
     * 显示下载完成时的通知（点击安装）
     * @param notifyId
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @param file
     */
    private void showFinishNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, File file, String authority){
        cancelNotification(notifyId);
        NotificationCompat.Builder builder = buildNotification(channelId,icon,title,content);
        builder.setAutoCancel(true);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uriData;
        String type = "application/vnd.android.package-archive";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uriData = FileProvider.getUriForFile(getContext(), authority, file);
        }else{
            uriData = Uri.fromFile(file);
        }
        intent.setDataAndType(uriData, type);
        PendingIntent clickIntent = PendingIntent.getActivity(getContext(), notifyId,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(clickIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notifyNotification(notifyId,notification);
    }

    private void showErrorNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, boolean isReDownload, UpdateConfig config){
        NotificationCompat.Builder builder = buildNotification(channelId,icon,title,content);
        builder.setAutoCancel(true);
        if(isReDownload){//重新下载
            Intent intent  = new Intent(getContext(),DownloadService.class);
            intent.putExtra(UpdateAppConstants.KEY_RE_DOWNLOAD,true);
            intent.putExtra(UpdateAppConstants.KEY_UPDATE_CONFIG,config);
            PendingIntent clickIntent = PendingIntent.getService(getContext(), notifyId,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(clickIntent);
        }else{
            PendingIntent clickIntent = PendingIntent.getService(getContext(), notifyId,new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(clickIntent);
        }

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notifyNotification(notifyId,notification);
    }


    /**
     * 显示通知信息（非第一次）
     * @param notifyId
     * @param channelId
     * @param icon
     * @param title
     * @param content
     */
    private void showNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, boolean isAutoCancel){
        NotificationCompat.Builder builder = buildNotification(channelId,icon,title,content);
        builder.setAutoCancel(isAutoCancel);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notifyNotification(notifyId,notification);
    }

    /**
     *
     * @param notifyId
     */
    private void cancelNotification(int notifyId){
        getNotificationManager().cancel(notifyId);
    }


    /**
     * 获取通知管理器
     * @return
     */
    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 创建一个通知渠道（兼容0以上版本）
     * @param channelId
     * @param channelName
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName){
        NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT);
        getNotificationManager().createNotificationChannel(channel);

    }

    /**
     * 构建一个通知构建器
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @return
     */
    private NotificationCompat.Builder buildNotification(String channelId, @DrawableRes int icon, CharSequence title, CharSequence content){
        return buildNotification(channelId,icon,title,content,UpdateAppConstants.NONE,UpdateAppConstants.NONE);
    }

    /**
     * 构建一个通知构建器
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @param progress
     * @param size
     * @return
     */
    private NotificationCompat.Builder buildNotification(String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, int progress, int size){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),channelId);
        builder.setSmallIcon(icon);

        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setOngoing(true);

        if(progress!= UpdateAppConstants.NONE && size!=UpdateAppConstants.NONE){
            builder.setProgress(size,progress,false);
        }

        return builder;
    }

    /**
     * 更新通知栏
     * @param id
     * @param notification
     */
    private void notifyNotification(int id, Notification notification){
        getNotificationManager().notify(id,notification);
    }


    //---------------------------------------- Binder

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }

    /**
     * 提供绑定服务的方式下载
     */
    public class DownloadBinder extends Binder {

        public void start(UpdateConfig config){
            start(config,null,null);
        }

        public void start(UpdateConfig config,UpdateCallback callback){
            start(config,null,callback);
        }

        public void start(UpdateConfig config, IAPPUPHttpManager httpManager, UpdateCallback callback){
            startDownload(config,httpManager,callback);
        }

    }


}
