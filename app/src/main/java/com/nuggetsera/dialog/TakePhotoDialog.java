package com.nuggetsera.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nuggetsera.R;
import com.nuggetsera.base.Constants;

/**
 * Created by zhangzz on 2018/9/3
 * 拍照选择dialog
 */
public class TakePhotoDialog extends Dialog implements View.OnClickListener {
    public static final int FLAG_TAKEPHOTO = 1;
    public static final int FLAG_PICKPHOTO = 2;

    private Activity mContext;
    private OnPhotoDialogListener mOnPhotoDialogListener;

    public TakePhotoDialog(@NonNull Activity context, OnPhotoDialogListener onPhotoDialogListener) {
        super(context);
        mContext = context;
        mOnPhotoDialogListener = onPhotoDialogListener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout_take_photo, null);
        TextView tvCamera = view.findViewById(R.id.tvCamera);
        tvCamera.setOnClickListener(this);
        TextView tvPhoto = view.findViewById(R.id.tvPhoto);
        tvPhoto.setOnClickListener(this);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        this.setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9);//dialog宽度为屏幕宽度的90%
        dialogWindow.setAttributes(p);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCamera:
                sheetDialogDismiss();
                mOnPhotoDialogListener.takePhoto(FLAG_TAKEPHOTO, Constants.PHOTO_TAKE);
                break;
            case R.id.tvPhoto:
                sheetDialogDismiss();
                mOnPhotoDialogListener.pickPhoto(FLAG_PICKPHOTO, Constants.PHOTO_PICK);
                break;
            case R.id.tvCancel:
                sheetDialogDismiss();
                break;
        }
    }

    public void sheetDialogDismiss() {
        if (null != this && this.isShowing()) {
            this.dismiss();
        }
    }

    /**
     * 销毁Activity的引用 防止内存泄漏
     */
    public void onDestroyDialog(){
        sheetDialogDismiss();
        mContext = null;
    }

    public interface OnPhotoDialogListener {
        void takePhoto(int flag, int code);

        void pickPhoto(int flag, int code);
    }
}
