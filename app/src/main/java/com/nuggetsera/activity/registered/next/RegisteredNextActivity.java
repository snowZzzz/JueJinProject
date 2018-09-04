package com.nuggetsera.activity.registered.next;

/*
 *  @项目名：  NuggetsEra
 *  @包名：    com.nuggetsera.ui
 *  @文件名:   RegisteredNextActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 17:35
 *  @描述：    注册第二个页面
 */

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.goldlibrary.base.BaseModel;
import com.nuggetsera.R;
import com.nuggetsera.activity.login.LoginPresenter;
import com.nuggetsera.activity.login.LoginPresenterImpl;
import com.nuggetsera.activity.login.LoginView;
import com.nuggetsera.activity.main.MainActivity;
import com.nuggetsera.activity.takephoto.TakePhotoActivity;
import com.nuggetsera.base.BaseActivity;
import com.nuggetsera.base.Constants;
import com.nuggetsera.dialog.TakePhotoDialog;
import com.nuggetsera.utils.ToastUtils;
import com.nuggetsera.widget.FixedEditView;
import com.nuggetsera.widget.GradientBtnLayout;
import com.nuggetsera.widget.ReturnView;
import com.nuggetsera.widget.UnderLineEditText;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisteredNextActivity
        extends BaseActivity
        implements RegisteredNextView, LoginView {
    @BindView(R.id.tv_return)
    ReturnView mTvReturn;
    @BindView(R.id.btn_complete_register)
    GradientBtnLayout mBtnComplete;
    @BindView(R.id.iv_update_icon)
    ImageView mIvUpdateIcon;
    @BindView(R.id.et_user_name)
    FixedEditView mEtUserName;
    @BindView(R.id.et_invite_code)
    FixedEditView mEtInviteCode;
    @BindView(R.id.et_password)
    FixedEditView mEtPassword;
    @BindView(R.id.et_next_password)
    FixedEditView mEtNextPassword;

    private RegisteredNextPresenter mRegisteredNextPresenter;
    private LoginPresenter mLoginPresenter;
    private String mobilePhone;
    private TakePhotoDialog mTakePhotoDialog;
    private String picUrl = "";//头像图片选择

    @Override
    public int getLayoutResId() {
        return R.layout.activity_registered_next;
    }

    @Override
    protected void init() {
        super.init();

        mRegisteredNextPresenter = new RegisteredNextPresenterImpl(this);
        mLoginPresenter = new LoginPresenterImpl(this);
        mobilePhone = getIntent().getStringExtra("mobilePhone");

        mTvReturn.getTvText().setText("上一步");

        //设置昵称输入框
        setEtUserName();

        //设置邀请码输入框
        setEtInviteCode();

        //设置密码输入框
        setEtPassword();

        //设置确认密码输入框
        setEtNextPassword();

        initEvent();
    }

    private void initEvent() {
        mTakePhotoDialog = new TakePhotoDialog(this, new TakePhotoDialog.OnPhotoDialogListener() {
            @Override
            public void takePhoto(int flag, int code) {
                startActForResult(TakePhotoDialog.FLAG_TAKEPHOTO, Constants.PHOTO_TAKE);
            }

            @Override
            public void pickPhoto(int flag, int code) {
                startActForResult(TakePhotoDialog.FLAG_PICKPHOTO, Constants.PHOTO_PICK);
            }
        });
    }

    private void startActForResult(int flag, int requestCode) {
        startActivityForResult(new Intent(this, TakePhotoActivity.class).putExtra("flag", flag), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        String picUrlAddress = "";
        if (requestCode == Constants.PHOTO_TAKE) {
            picUrlAddress = data.getStringExtra("data");
            Log.v("WZ", picUrlAddress);
        } else if (requestCode == Constants.PHOTO_TAKE) {
            picUrlAddress = data.getStringExtra("data");
            Log.v("WZ", picUrlAddress);
        }

        if (!TextUtils.isEmpty(picUrlAddress)) {
            mRegisteredNextPresenter.upLoadFile(picUrlAddress);
        }
    }

    @OnClick({R.id.tv_return,
            R.id.btn_complete_register,
            R.id.iv_update_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_return:        //上一步
//                goTo(RegisteredActivity.class);
                finish();
                break;
            case R.id.btn_complete_register:     //完成注册
                mRegisteredNextPresenter.toRegister(mobilePhone, picUrl, mEtUserName.getFixEdit().getText().toString().trim(), mEtPassword.getFixEdit().getText().toString().trim(), mEtNextPassword.getFixEdit().getText().toString().trim());
                break;
            case R.id.iv_update_icon:   //上传头像
                mTakePhotoDialog.show();
                break;
        }
    }


    private void setEtUserName() {
        mEtUserName.getFixText().setText("昵" + "\u3000\u3000" + "称");
        UnderLineEditText fixEdit = mEtUserName.getFixEdit();
        fixEdit.setHint("请输入昵称");
    }

    private void setEtInviteCode() {
        mEtInviteCode.getFixText().setText("邀  请  码");
        UnderLineEditText fixEdit = mEtInviteCode.getFixEdit();
        fixEdit.setHint("请输入邀请码");
    }

    private void setEtPassword() {
        mEtPassword.getFixText().setText("设置密码");
        UnderLineEditText fixEdit = mEtPassword.getFixEdit();
        fixEdit.setHint("请输入登录密码");
        //设置输入内容为密码不可见
        fixEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    private void setEtNextPassword() {
        mEtNextPassword.getFixText().setText("确认密码");
        UnderLineEditText fixEdit = mEtNextPassword.getFixEdit();
        fixEdit.setHint("请重新输入登录密码");
        //设置输入内容为密码不可见
        fixEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void setError(int reqCode, String errorMsg) {
        ToastUtils.showCenter(this, errorMsg);
    }

    @Override
    public void setSuccess(int reqCode, BaseModel baseModel, String resultData) {
        if (reqCode == Constants.REQ_REGISTER) {
            mLoginPresenter.toLogin(mobilePhone, mEtPassword.getFixEdit().getText().toString().trim());
        } else if (reqCode == Constants.REQ_LOGIN) {
            goTo(MainActivity.class);
        } else if (reqCode == Constants.REQ_FILE_UPLOAD) {
            picUrl = ((FileUploadBean) baseModel).getFileUrl();
            ToastUtils.showCenter(this, "头像上传成功");
        }
    }

    @Override
    public void showLoading(int requsetCode) {

    }

    @Override
    public void hideLoading(int requsetCode) {

    }

    @Override
    protected void onDestroy() {
        mTakePhotoDialog.onDestroyDialog();
        mTakePhotoDialog = null;
        super.onDestroy();
    }
}
