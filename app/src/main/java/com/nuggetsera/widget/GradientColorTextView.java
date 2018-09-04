package com.nuggetsera.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.nuggetsera.R;

/**
 * Created by zhangzz on 2018/8/27
 * 渐变色的TextView
 */
public class GradientColorTextView extends android.support.v7.widget.AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();
    private int beginColor;
    private int centerColor;
    private int endColor;


    public GradientColorTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientColorTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        if (n>0) {
            for (int i = 0; i < n; ++i) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.GradientColorTextView_beginColor:
                        beginColor = a.getColor(attr, getResources().getColor(R.color.color_7F6B3F13));
                        break;
                    case R.styleable.GradientColorTextView_centerColor:
                        centerColor = a.getColor(attr, getResources().getColor(R.color.color_FFECE098));
                        break;
                    case R.styleable.GradientColorTextView_endColor:
                        endColor = a.getColor(attr, getResources().getColor(R.color.color_7F6B3F13));
                        break;
                }
            }

            a.recycle();
        } else {
            beginColor = getResources().getColor(R.color.color_7F6B3F13);
            centerColor = getResources().getColor(R.color.color_FFECE098);
            endColor = getResources().getColor(R.color.color_7F6B3F13);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setBackgroundColor(Color.TRANSPARENT);
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[] {beginColor, centerColor, endColor},
                new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 +   mTextBound.height()/2, mPaint);
    }
}
