package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.ab.view.bouncescrollview.AbBounceListView;
import com.ab.view.bouncescrollview.AbBounceScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 毅涵 on 15/1/16.
 */
public class MainSecondFragment extends LazyFragment {
    @ViewInject(R.id.id_scrollView)
    private AbBounceScrollView mAbBounceScrollView;
    @ViewInject(R.id.mListView)
    private AbBounceListView mAbBounceListView;
    private Context mContext;
    private View mView;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.main_second_fragment, null);
        ViewUtils.inject(this, mView);
        mAbBounceScrollView.setCallBack(new AbBounceScrollView.Callback() {
            @Override
            public void callback() {
                Toast.makeText(mContext, "you can do something!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mAbBounceListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList("Hello", "World", "Welcome", "Java", "Android", "Lucene", "C++", "C#", "HTML", "Welcome", "Java", "Android", "Lucene", "C++", "C#", "HTML"))));
        return mView;
    }
}
