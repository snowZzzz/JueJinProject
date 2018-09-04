package com.nuggetsera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nuggetsera.R;

/**
 * Created by zhangzz on 2018/8/28
 */
public class GradientButton extends android.support.v7.widget.AppCompatTextView {
    public GradientButton(Context context) {
        super(context);
    }

    public GradientButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GradientButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gradient_color_tv));
        this.setTextSize(16);
        this.setTextColor(context.getResources().getColor(R.color.white_color));
    }
}
