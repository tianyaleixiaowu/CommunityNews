package com.tianyalei.communitynews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorFragmentPagerAdapter;

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


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_tabmain);

		ViewUtils.inject(this);
		IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mButtonIndicator, mMainViewPager);
		indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		// 禁止viewpager的滑动事件
		mMainViewPager.setCanScroll(false);
		// 设置viewpager保留界面不重新加载的页面数量
		mMainViewPager.setOffscreenPageLimit(4);
		// 默认是1,，自动预加载左右两边的界面。设置viewpager预加载数为0。只加载加载当前界面。
		mMainViewPager.setPrepareNumber(0);
	}

	private class MyAdapter extends IndicatorFragmentPagerAdapter {
		private String[] tabNames = { "主页", "消息", "发现", "我" };
		private int[] tabIcons = { R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
				R.drawable.maintab_4_selector };
		private LayoutInflater inflater;

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			inflater = LayoutInflater.from(getApplicationContext());
		}

		@Override
		public int getCount() {
			return tabNames.length;
		}

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {
			if (convertView == null) {
				convertView = (TextView) inflater.inflate(R.layout.tab_bottom_text, container, false);
			}
			TextView textView = (TextView) convertView;
			textView.setText(tabNames[position]);
			textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
			return textView;
		}

		@Override
		public Fragment getFragmentForPage(int position) {
			MainFragment mainFragment = new MainFragment();
			Bundle bundle = new Bundle();
			bundle.putString(MainFragment.INTENT_STRING_TABNAME, tabNames[position]);
			bundle.putInt(MainFragment.INTENT_INT_INDEX, position);
			mainFragment.setArguments(bundle);
			return mainFragment;
		}
	}
}
