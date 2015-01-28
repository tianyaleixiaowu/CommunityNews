package com.tianyalei.communitynews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ab.util.AbSharedUtil;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * Created by wuwf on 2015/1/28.
 * 引导页
 */
public class GuideActivity extends FragmentActivity {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private Context mContext;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_guide);

        mContext = this;

        boolean isFirst = AbSharedUtil.getBoolean(mContext, "isFirst", true);
        if (!isFirst) {
            Intent intent = new Intent(mContext, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(adapter);
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        private int[] images = {R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3, R.drawable.welcome4};

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            convertView.setBackgroundResource(images[position]);
            if (position == images.length - 1) {
                convertView = inflate.inflate(R.layout.welcome_last_page, container, false);
                Button button = (Button) convertView.findViewById(R.id.welcome_last_btn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };
}
