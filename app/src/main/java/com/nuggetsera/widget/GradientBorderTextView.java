package com.nuggetsera.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.Layout;
import android.util.AttributeSet;

/**
 * Created by zzz on 2018/8/27.
 * 渐变色带边框的TextView
 */

public class GradientBorderTextView extends android.support.v7.widget.AppCompatTextView {
    private String TAG = "BorderTextView";
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private Rect mRect;

    public GradientBorderTextView(Context context) {
        this(context, null);
    }

    public GradientBorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientBorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        mPaint = getPaint();
        mRect = new Rect();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mRect);
        float withd = Layout.getDesiredWidth(getText(), getPaint());
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, mViewHeight,
                new int[]{0x7F6B3F13, 0xFFECE098, 0x7F6B3F13},
                null, Shader.TileMode.MIRROR);
        mPaint.setShader(mLinearGradient);

        mPaint.setTextSize(sp2px(getContext(), 16));

        canvas.drawText(mTipText,
                getMeasuredWidth() / 2 - (mRect.width() + sp2px(getContext(), 16)) / 2,
                getMeasuredHeight() / 2 + mRect.height() / 2, mPaint);

        canvas.restore();

        canvas.save();
        mPaint = new Paint();
        mRect = canvas.getClipBounds();
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        mPaint.setStrokeWidth(dip2px(getContext(), 2));
        mPaint.setShader(mLinearGradient);
        RectF rectF = new RectF(mRect);
        rectF.left =  rectF.left +3;
        rectF.right = rectF.right -3;
        rectF.top = rectF.top + 3;
        rectF.bottom = rectF.bottom -3;
        canvas.drawRoundRect(rectF, dip2px(getContext(), 60-2.5f), dip2px(getContext(), 60-2.5f), mPaint);
        canvas.restore();
    }

    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
