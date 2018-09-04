package com.nuggetsera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuggetsera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.widget
 *  @文件名:   title_view
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/20 10:30
 *  @描述：    顶部的title
 */

public class TitleView
        extends RelativeLayout
{
    @BindView(R.id.tv_title_return)
    TextView     mTvTitleReturn;
    @BindView(R.id.tv_title_label)
    TextView     mTvTitleLabel;
    @BindView(R.id.ll_return)
    LinearLayout mLlReturn;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_title, this);
        ButterKnife.bind(this);

    }

    public TextView getTvTitleLabel() {
        return mTvTitleLabel;
    }

    public TextView getTvTitleReturn() {
        return mTvTitleReturn;
    }

    public LinearLayout getLlReturn() {
        return mLlReturn;
    }

    @OnClick(R.id.ll_return)
    public void onViewClicked() {



    }
}
