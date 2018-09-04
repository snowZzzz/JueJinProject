package com.nuggetsera.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/8/19.
 */

public class UnderLineEditText extends EditText {

    private Paint linePaint;
    private int paperColor;

    public UnderLineEditText(Context context, AttributeSet paramAttributeSet) {
        super(context,paramAttributeSet);
        this.linePaint = new Paint();
    }

    protected void onDraw(Canvas paramCanvas) {
        paramCanvas.drawColor(this.paperColor); //设置背景色
        int i = getLineCount();
        int j = getHeight();
        int k = getLineHeight();
        int m = 1 + j / k;
        if (i < m) i = m;
        int n = getCompoundPaddingTop();

        for (int i2 = 0;; i2++) {
            if (i2 >= i) {
                super.onDraw(paramCanvas);
                paramCanvas.restore();
                return;
            }

            n += k;
            paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
            paramCanvas.save();
        }
    }


}
