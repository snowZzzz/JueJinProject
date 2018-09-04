package com.nuggetsera.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nuggetsera.R;

/**
 * Created by zhangzz on 2018/8/28
 * 项目登录按钮边框和字体渐变色的layout
 */

public class GradientBtnLayout extends LinearLayout {
    private String mText;
    private View view;

    public GradientBtnLayout(@NonNull Context context) {
        super(context);
    }

    public GradientBtnLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public GradientBtnLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientBtnLayout, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; ++i){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.GradientBtnLayout_btnText:
                    mText = a.getString(attr);
                    break;
            }
        }

        a.recycle();

        init();
    }

    private void init() {
//        view= View.inflate(getContext(),R.layout.gradient_btn_layout,this);
        this.setBackgroundColor(Color.TRANSPARENT);
        view= LayoutInflater.from(getContext()).inflate(R.layout.gradient_btn_layout, this,false);
        GradientColorTextView colorTextView = view.findViewById(R.id.color_tv);
        colorTextView.setText(mText);
        this.addView(view);
    }

}
