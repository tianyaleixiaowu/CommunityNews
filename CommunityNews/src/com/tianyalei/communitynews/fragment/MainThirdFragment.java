package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ab.util.AbToastUtil;
import com.ab.view.metroimageview.MetroImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.R;

/**
 * Created by 毅涵 on 15/1/16.
 */
public class MainThirdFragment extends LazyFragment {
    private Context mContext;
    private View mView;

    @ViewInject(R.id.c_joke)
    private MetroImageView joke;
    @ViewInject(R.id.c_idea)
    private MetroImageView idea;
    @ViewInject(R.id.c_constellation)
    private MetroImageView constellation;
    @ViewInject(R.id.c_recommend)
    private MetroImageView recommend;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.main_third_fragment, null);
        ViewUtils.inject(this, mView);
        joke.setOnClickIntent(new MetroImageView.OnViewClickListener() {
            @Override
            public void onViewClick(MetroImageView view) {
                AbToastUtil.showToast(mContext, "joke");
            }
        });
        return mView;
    }
}
