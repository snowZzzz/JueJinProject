package com.nuggetsera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuggetsera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.widget
 *  @文件名:   AuthCodeView
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/20 16:18
 *  @描述：    获取验证码第二种view
 */

public class AuthCodeView
        extends RelativeLayout
{


    @BindView(R.id.auth_und_edit)
    EditText mAuthUndEdit;
    @BindView(R.id.auth_tv_text)
    TextView mAuthTvText;

    public AuthCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_auth_code, this);

        ButterKnife.bind(this);

    }


    @OnClick(R.id.auth_tv_text)
    public void onViewClicked() {
    }

    public TextView getAuthTvText() {
        return mAuthTvText;
    }

    public EditText getAuthUndEdit() {
        return mAuthUndEdit;
    }
}
