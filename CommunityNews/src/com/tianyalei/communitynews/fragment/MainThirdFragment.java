package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lidroid.xutils.ViewUtils;
import com.tianyalei.communitynews.R;

/**
 * Created by 毅涵 on 15/1/16.
 */
public class MainThirdFragment extends LazyFragment {
    private Context mContext;
    private View mView;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.main_third_fragment, null);
        ViewUtils.inject(this, mView);
        return mView;
    }
}
