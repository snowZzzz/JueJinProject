package com.nuggetsera.activity.registered.next;

import com.example.goldlibrary.base.BaseModel;

/**
 * Created by zhangzz on 2018/9/3
 * 上传图片返回数据bean
 */
public class FileUploadBean extends BaseModel {
    private String fileUrl;
    private String result;//成功：success/失败:fail

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
