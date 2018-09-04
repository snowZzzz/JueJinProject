package com.nuggetsera.activity.login;

/*
 *  @项目名：  NuggetsEra
 *  @包名：    com.nuggetsera.ui
 *  @文件名:   LoginActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 16:15
 *  @描述：    登录界面
 */

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.goldlibrary.base.BaseModel;
import com.example.goldlibrary.utils.DataCacheUtil;
import com.nuggetsera.R;
import com.nuggetsera.activity.changepassword.ChangePasswordActivity;
import com.nuggetsera.activity.main.MainActivity;
import com.nuggetsera.activity.registered.RegisteredActivity;
import com.nuggetsera.base.BaseActivity;
import com.nuggetsera.utils.ToastUtils;
import com.nuggetsera.widget.FixedEditView;
import com.nuggetsera.widget.GradientBtnLayout;
import com.nuggetsera.widget.UnderLineEditText;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.tv_retrieve_password)
    TextView mTvRetrievePassword;
    @BindView(R.id.tv_registered)
    TextView mTvRegistered;
    @BindView(R.id.btn_login)
    GradientBtnLayout mBtnLogin;
    @BindView(R.id.et_phone_num)
    FixedEditView mEtPhoneNum;
    @BindView(R.id.et_password)
    FixedEditView mEtPassword;
    private LoginPresenter mLoginPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();

        mLoginPresenter = new LoginPresenterImpl(this);

        //设置电话号码的输入框
        setPhoneEdit();

        //设置密码的输入框
        setPasswordEdit();

    }

    private void setPhoneEdit() {
        UnderLineEditText fixEdit = mEtPhoneNum.getFixEdit();
        fixEdit.setInputType(InputType.TYPE_CLASS_PHONE);


    }

    private void setPasswordEdit() {

        TextView fixText = mEtPassword.getFixText();
        UnderLineEditText fixEdit = mEtPassword.getFixEdit();

        fixText.setText("密码");
        fixEdit.setHint("请输入登录密码");

        //设置输入内容为密码不可见
        fixEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    @OnClick({R.id.tv_retrieve_password,
            R.id.tv_registered, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_retrieve_password:     //修改密码
                goTo(ChangePasswordActivity.class);
                break;
            case R.id.tv_registered:            //注册
                goTo(RegisteredActivity.class);
                break;
            case R.id.btn_login:                //登录成功
                mLoginPresenter.toLogin("18813918090", "111111");//登录网络请求
                break;
        }
    }

    @Override
    public void showLoading(int requsetCode) {

    }

    @Override
    public void hideLoading(int requsetCode) {

    }

    @Override
    public void setError(int reqCode, String errorMsg) {

    }

    @Override
    public void setSuccess(int reqCode, BaseModel baseModel, String resultData) {
        DataCacheUtil.getInstance(getApplicationContext()).saveString("userinfo", ((LoginBean)baseModel).getMobilePhone());
        goTo(MainActivity.class);
    }
}
