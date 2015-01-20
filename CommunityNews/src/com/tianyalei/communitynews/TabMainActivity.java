package com.tianyalei.communitynews;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import com.ab.util.AbToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.tianyalei.communitynews.adapter.MainFragmentPagerAdapter;
import com.umeng.analytics.MobclickAgent;

/**
 * 带下导航栏
 */
public class TabMainActivity extends FragmentActivity {
	/**
	 * 主界面viewpager
	 */
	@ViewInject(R.id.tabmain_viewPager)
	private ViewPager mMainViewPager;
	/**
	 * 主界面viewpager
	 */
	@ViewInject(R.id.tabmain_indicator)
	private Indicator mButtonIndicator;

	@ViewInject(R.id.title_left)
	private ImageView mTitleLeftImage;
	@ViewInject(R.id.title_right)
	private ImageView mTitleRightImage;
	@ViewInject(R.id.rss_title)
	private TextView mTitleText;
	private boolean doubleBackToExitPressedOnce;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_tabmain);

		ViewUtils.inject(this);

		mTitleLeftImage.setImageResource(R.drawable.main_title_left);
		mTitleRightImage.setImageResource(R.drawable.main_title_right);
		mTitleText.setText("展览路社区");



		IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mButtonIndicator, mMainViewPager);
		indicatorViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
		// 禁止viewpager的滑动事件
		mMainViewPager.setCanScroll(false);
		// 设置viewpager保留界面不重新加载的页面数量
		mMainViewPager.setOffscreenPageLimit(4);
		// 默认是1,，自动预加载左右两边的界面。设置viewpager预加载数为0。只加载加载当前界面。
		mMainViewPager.setPrepareNumber(0);
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}


	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		AbToastUtil.showToast(this, "再按一次退出程序");

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}
}
