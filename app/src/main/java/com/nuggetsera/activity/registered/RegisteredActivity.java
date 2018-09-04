package com.nuggetsera.activity.registered;

/*
 *  @项目名：  NuggetsEra
 *  @包名：    com.nuggetsera.ui
 *  @文件名:   RegisteredActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/16 16:52
 *  @描述：    注册第一个页面
 */

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import com.example.goldlibrary.base.BaseModel;
import com.nuggetsera.R;
import com.nuggetsera.activity.registered.next.RegisteredNextActivity;
import com.nuggetsera.base.BaseActivity;
import com.nuggetsera.base.Constants;
import com.nuggetsera.utils.ToastUtils;
import com.nuggetsera.widget.FixedEditView;
import com.nuggetsera.widget.GetCodeEditView;
import com.nuggetsera.widget.GradientBtnLayout;
import com.nuggetsera.widget.ReturnView;
import com.nuggetsera.widget.UnderLineEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity
        implements RegisteredView {

    @BindView(R.id.btn_nextstep)
    GradientBtnLayout mBtnNext;
    @BindView(R.id.et_register_tel)
    FixedEditView mEtRegisterTel;
    @BindView(R.id.et_auth_code)
    GetCodeEditView mEtAuthCode;
    @BindView(R.id.tv_return)
    ReturnView mTvReturn;

    private RegisteredPresenter mRegisteredPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_registered;
    }


    @Override
    protected void init() {
        super.init();
        mRegisteredPresenter = new RegisteredPresenterImpl(this);

        mTvReturn.getTvText().setText("登录");

        //设置号码输入框
        setRegisterTel();

        //设置验证码输入框
        setAuthCode();

        mEtAuthCode.getFixRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobilePhone = mEtRegisterTel.getFixEdit().getText().toString().trim();
                if (!TextUtils.isEmpty(mobilePhone)) {
                    mRegisteredPresenter.toGetCode(mobilePhone);
                } else {
                    ToastUtils.showCenter(RegisteredActivity.this, "请输入手机号");
                }
            }
        });
    }

    @OnClick({R.id.tv_return,
            R.id.btn_nextstep})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_return:            //返回登录界面
                finish();
                break;
            case R.id.btn_nextstep:             //下一步
                mRegisteredPresenter.toCheckCode(mEtRegisterTel.getFixEdit().getText().toString().trim(), mEtAuthCode.getFixEdit().getText().toString().trim(), "1");
                break;
        }
    }


    private void setRegisterTel() {
        UnderLineEditText fixEdit = mEtRegisterTel.getFixEdit();
        fixEdit.setInputType(InputType.TYPE_CLASS_PHONE);

    }

    private void setAuthCode() {
        UnderLineEditText fixEdit = mEtAuthCode.getFixEdit();
    }

    @Override
    public void setError(int reqCode, String errorMsg) {
        ToastUtils.showCenter(this, errorMsg);
    }

    @Override
    public void setSuccess(int reqCode, BaseModel baseModel, String resultData) {
        if (reqCode == Constants.REQ_GETCODE) {
            mEtAuthCode.getFixEdit().setText(((PhoneCodeBean) baseModel).getPhoneCode());
            ToastUtils.showCenter(RegisteredActivity.this, "您本次验证码为： " + ((PhoneCodeBean) baseModel).getPhoneCode());
        } else {
            Intent intent = new Intent(this, RegisteredNextActivity.class);
            intent.putExtra("mobilePhone", mEtRegisterTel.getFixEdit().getText().toString().trim());
            startActivity(intent);
        }
    }
}
