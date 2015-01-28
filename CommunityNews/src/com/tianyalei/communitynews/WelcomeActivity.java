package com.tianyalei.communitynews;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 欢迎页
 *
 * @author wuwf
 */
public class WelcomeActivity extends Activity {
    @ViewInject(R.id.linear)
    private RelativeLayout mLaunchLayout;

    private Animation mFadeIn;
    private Animation mFadeInScale;
    private Animation mFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ViewUtils.inject(this);

        setListener();
        loadData();
    }

    private void loadData() {

        mLaunchLayout.startAnimation(mFadeIn);
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				Intent i = new Intent(WelcomeActivity.this,
//						NewMainActivity.class);
//				startActivity(i);
//				WelcomeActivity.this.finish();
//			}
//		}, 800);

    }

    private void setListener() {
        mFadeIn = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.welcome_fade_in);
        mFadeIn.setDuration(500);
        mFadeInScale = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.welcome_fade_in_scale);
        mFadeInScale.setDuration(2000);
        mFadeOut = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.welcome_fade_out);
        mFadeOut.setDuration(500);
        mFadeOut.setFillAfter(true);

        /**
         * 动画切换原理:开始时是用第一个渐现动画,当第一个动画结束时开始第二个放大动画,当第二个动画结束时调用第三个渐隐动画,
         * 第三个动画结束时修改显示的内容并且重新调用第一个动画,从而达到循环效果
         */
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mLaunchLayout.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mLaunchLayout.startAnimation(mFadeOut);
            }
        });

        mFadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(WelcomeActivity.this,
                        TabMainActivity.class);
                startActivity(i);
                WelcomeActivity.this.finish();
            }
        });


    }
}

