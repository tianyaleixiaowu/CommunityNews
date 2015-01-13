package com.tianyalei.communitynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.application.MyApplication;
import com.tianyalei.communitynews.fragment.BaseFragment;
import com.tianyalei.communitynews.fragment.MainFirstFragment;

/**
 * Created by wuwf on 2015/1/13.
 */
public class MainFragmentPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private String[] tabNames = {"主页", "消息", "发现", "我"};
    private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
            R.drawable.maintab_4_selector};
    private LayoutInflater inflater;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        inflater = LayoutInflater.from(MyApplication.getInstance());
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
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainFirstFragment();

                break;
            default:
                break;
        }
//        MainFragment mainFragment = new MainFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(MainFragment.INTENT_STRING_TABNAME, tabNames[position]);
//        bundle.putInt(MainFragment.INTENT_INT_INDEX, position);
//        mainFragment.setArguments(bundle);
        return fragment;
    }
}
