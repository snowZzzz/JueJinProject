package com.nuggetsera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuggetsera.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.widget
 *  @文件名:   ReturnView
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/20 14:09
 *  @描述：    返回组合键
 */

public class ReturnView
        extends RelativeLayout
{


    @BindView(R.id.tv_text)
    TextView       mTvText;
    @BindView(R.id.rl_return)
    RelativeLayout mRlReturn;

    public ReturnView(Context context) {
        this(context, null);
    }

    public ReturnView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_renturn, this);

        ButterKnife.bind(this);

    }

    public TextView getTvText() {
        return mTvText;
    }


}
