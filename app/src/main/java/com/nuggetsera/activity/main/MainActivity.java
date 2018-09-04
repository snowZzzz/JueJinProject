package com.nuggetsera.activity.main;

import android.Manifest;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dialoglibrary.dilaog.AppDialog;
import com.example.updateapplibrary.AppUpdater;
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
    private String mUrl = "https://raw.githubusercontent.com/jenly1314/AppUpdater/master/app/release/app-release.apk";

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
                updateMethod();
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

    private void updateMethod() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText("自定义弹框升级");
        TextView tvContent = view.findViewById(R.id.tvContent);
        tvContent.setText("1、新增某某功能、\n2、修改某某问题、\n3、优化某某BUG、");

        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog.INSTANCE.dismissDialog();
            }
        });
        Button btnOK = view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AppUpdater.Builder()
                        .serUrl(mUrl)
                        .setFilename("AppUpdater.apk")
                        .build(MainActivity.this)
                        .start();
                AppDialog.INSTANCE.dismissDialog();
            }
        });

        AppDialog.INSTANCE.showDialog(this, view);
    }
}
