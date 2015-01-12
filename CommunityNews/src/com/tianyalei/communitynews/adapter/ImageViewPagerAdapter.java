package com.tianyalei.communitynews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 毅涵 on 15/1/12.
 */
public class ImageViewPagerAdapter extends PagerAdapter {
    private List<ImageView> mImageViewList;

    public ImageViewPagerAdapter(List<ImageView> list) {
        mImageViewList = list;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }
}
