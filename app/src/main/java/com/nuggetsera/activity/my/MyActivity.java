package com.nuggetsera.activity.my;

/*
 *  @项目名：  NuggetsEra 
 *  @包名：    com.nuggetsera.ui
 *  @文件名:   MyActivity
 *  @创建者:   Administrator
 *  @创建时间:  2018/8/17 13:45
 *  @描述：    "我的"页面
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuggetsera.R;
import com.nuggetsera.activity.main.MainActivity;
import com.nuggetsera.base.BaseActivity;
import com.nuggetsera.widget.ReturnView;

import butterknife.BindView;
import butterknife.OnClick;

public class MyActivity extends BaseActivity
        implements MyView
{
    @BindView(R.id.tv_return)
    ReturnView mTvReturn;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_gender)
    ImageView mIvGender;
    @BindView(R.id.tv_user_id)
    TextView mTvUserId;
    @BindView(R.id.ll_label_set)
    LinearLayout mLlLabelSet;
    @BindView(R.id.tv_nuggets_number)
    TextView mTvNuggetsNumber;
    @BindView(R.id.tv_nuggets_ranking)
    TextView mTvNuggetsRanking;
    @BindView(R.id.ll_nuggets)
    LinearLayout mLlNuggets;
    @BindView(R.id.tv_gold_number)
    TextView mTvGoldNumber;
    @BindView(R.id.tv_gold_ranking)
    TextView mTvGoldRanking;
    @BindView(R.id.ll_gold)
    LinearLayout mLlGold;
    @BindView(R.id.tv_gold_sum)
    TextView mTvGoldSum;
    @BindView(R.id.tv_gold_sum_ranking)
    TextView mTvGoldSumRanking;
    @BindView(R.id.ll_gold_sum)
    LinearLayout mLlGoldSum;
    @BindView(R.id.rl_personal_data)
    RelativeLayout mRlPersonalData;
    @BindView(R.id.rl_account_manage)
    RelativeLayout mRlAccountManage;
    @BindView(R.id.rl_my_task)
    RelativeLayout mRlMyTask;
    @BindView(R.id.rl_game_introduction)
    RelativeLayout mRlGameIntroduction;
    @BindView(R.id.tv_version_code)
    TextView mTvVersionCode;
    @BindView(R.id.rl_version_update)
    RelativeLayout mRlVersionUpdate;
    @BindView(R.id.tv_service_tel)
    TextView mTvServiceTel;
    @BindView(R.id.rl_contact_us)
    RelativeLayout mRlContactUs;

    private MyPresenter mMyPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my;
    }

    @Override
    protected void init() {
        super.init();

        mMyPresenter = new MyPresenterImpl(this);

    }

    @OnClick({R.id.tv_return, R.id.ll_nuggets, R.id.ll_gold, R.id.ll_gold_sum, R.id.rl_personal_data, R.id.rl_account_manage, R.id.rl_my_task, R.id.rl_game_introduction, R.id.rl_version_update, R.id.rl_contact_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_return:    //返回首页
                goTo(MainActivity.class);
                break;
            case R.id.ll_nuggets:   //掘金力
                break;
            case R.id.ll_gold:      //紫金量
                break;
            case R.id.ll_gold_sum:  //紫金总量
                break;
            case R.id.rl_personal_data: //个人资料
                break;
            case R.id.rl_account_manage:    //账号管理
                break;
            case R.id.rl_my_task:           //我的任务
                break;
            case R.id.rl_game_introduction: //游戏介绍
                break;
            case R.id.rl_version_update:    //版本更新
                break;
            case R.id.rl_contact_us:        //联系我们
                break;
        }
    }
}
