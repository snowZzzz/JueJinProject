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


/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.widget
 *  @文件名:   FixedRightEdit
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/20 17:58
 *  @描述：    Edit输入框
 */

public class FixedEdit
        extends RelativeLayout
{
    @BindView(R.id.et_fixed)
    EditText mEtFixed;
    @BindView(R.id.tv_fixed)
    TextView mTvFixed;

    public FixedEdit(Context context) {
        this(context, null);
    }

    public FixedEdit(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_fixed__edit, this);
        ButterKnife.bind(this);

    }

    public EditText getEtFixed() {
        return mEtFixed;
    }

    public TextView getTvFixed() {
        return mTvFixed;
    }

}
