package com.nuggetsera.activity.changepassword;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.ui
 *  @文件名:   ChangePasswordActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 17:57
 *  @描述：    找回密码第一页
 */

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nuggetsera.R;
import com.nuggetsera.activity.changepassword.two.ChangePasswordTwoActivity;
import com.nuggetsera.activity.login.LoginActivity;
import com.nuggetsera.base.BaseActivity;
import com.nuggetsera.widget.AuthCodeView;
import com.nuggetsera.widget.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity
        extends BaseActivity
        implements ChangePasswordView
{

    @BindView(R.id.btn_next)
    Button       mBtnNext;
    @BindView(R.id.title)
    TitleView    mTitle;
    @BindView(R.id.et_phone_num)
    EditText     mEtPhoneNum;
    @BindView(R.id.et_auth_code)
    AuthCodeView mEtAuthCode;

    private ChangePasswordPresenter mChangePasswordPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void init() {
        super.init();

        mChangePasswordPresenter = new ChangePasswordPresenterImpl(this);

        //设置返回键
        setRuturnBtn();

        //点击获取验证码
        getAuthCode();


    }

    private void setRuturnBtn() {
        mTitle.getLlReturn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(LoginActivity.class);
            }
        });
    }

    public void getAuthCode() {
        mEtAuthCode.getAuthTvText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO:发送验证码

            }
        });
    }


    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        goTo(ChangePasswordTwoActivity.class);
    }



}
