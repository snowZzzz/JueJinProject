package com.nuggetsera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuggetsera.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/19.
 */

public class FixedEditView extends RelativeLayout {
    @BindView(R.id.fix_edit)
    UnderLineEditText mFixEdit;
    @BindView(R.id.fix_text)
    TextView mFixText;

    public FixedEditView(Context context) {
        this(context, null);
    }

    public FixedEditView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_fixed_edittext, this);
        ButterKnife.bind(this);

    }

    public TextView getFixText() {
        return mFixText;
    }

    public UnderLineEditText getFixEdit() {
        return mFixEdit;
    }
}
