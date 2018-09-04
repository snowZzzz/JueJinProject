package com.nuggetsera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuggetsera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.widget
 *  @文件名:   GetCodeView
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/20 11:01
 *  @描述：    获取验证码的输入框
 */

public class GetCodeEditView
        extends RelativeLayout
{
    @BindView(R.id.fix_edit)
    UnderLineEditText mFixEdit;
    @BindView(R.id.fix_right_text)
    TextView          mFixRightText;

    public GetCodeEditView(Context context) {
        this(context, null);
    }

    public GetCodeEditView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_get_code, this);

        ButterKnife.bind(this);

    }

    public TextView getFixRightText() {
        return mFixRightText;
    }

    public UnderLineEditText getFixEdit() {
        return mFixEdit;
    }

    @OnClick(R.id.fix_right_text)
    public void onViewClicked(View view) {
//        mOnGetCodeClike.clickCode();
    }

//    private OnGetCodeClike mOnGetCodeClike;
//    public interface OnGetCodeClike{
//        void clickCode();
//    }
//
//    public void setOnGetCodeClike(OnGetCodeClike onGetCodeClike){
//        this.mOnGetCodeClike = onGetCodeClike;
//    }
}
