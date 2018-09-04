package com.nuggetsera.activity.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuggetsera.R;
import com.nuggetsera.activity.invitefriend.InviteFriendActivity;
import com.nuggetsera.activity.my.MyActivity;
import com.nuggetsera.activity.persenalauthent.PersenalAuthentAct;
import com.nuggetsera.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity
        extends BaseActivity
        implements MainView {


    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_announcement)
    TextView mTvAnnouncement;
    @BindView(R.id.tv_gold_number)
    TextView mTvGoldNumber;
    @BindView(R.id.tv_force_number)
    TextView mTvForceNumber;
    @BindView(R.id.iv_invite_friends)
    ImageView mIvInviteFriends;
    @BindView(R.id.iv_obtain_force)
    ImageView mIvObtainForce;
    @BindView(R.id.iv_gold_act)
    ImageView mIvGoldAct;
    @BindView(R.id.iv_nuggets_secret)
    ImageView mIvNuggetsSecret;
    @BindView(R.id.ll_above)
    LinearLayout mLlAbove;
    @BindView(R.id.iv_homepage_ranking)
    ImageView mIvHomepageRanking;
    @BindView(R.id.tv_force_value)
    TextView mTvForceValue;
    @BindView(R.id.tv_gold_value)
    TextView mTvGoldValue;

    private MainPresenter mMainPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();

        mMainPresenter = new MainPresenterImpl(this);
    }

    @OnClick({R.id.tv_user_name,
            R.id.tv_announcement,
            R.id.iv_invite_friends,
            R.id.iv_obtain_force,
            R.id.iv_gold_act,
            R.id.iv_nuggets_secret,
            R.id.tv_force_value,
            R.id.tv_gold_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_name:     //我的
                goTo(MyActivity.class);

                break;
            case R.id.tv_announcement:  //公告

                break;
            case R.id.iv_invite_friends: //邀请好友
                goTo(InviteFriendActivity.class);
                break;
            case R.id.iv_obtain_force:   //获取原力

                break;
            case R.id.iv_gold_act:       //紫金活动

                break;
            case R.id.iv_nuggets_secret: //掘金秘籍
                goTo(PersenalAuthentAct.class);
                break;
            case R.id.tv_force_value:    //原力值

                break;
            case R.id.tv_gold_value:     //紫金

                break;
        }
    }
}
