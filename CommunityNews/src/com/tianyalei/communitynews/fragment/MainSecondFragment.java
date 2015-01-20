package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.ab.http.wolf.GetDataCallBack;
import com.ab.view.bouncescrollview.AbBounceListView;
import com.ab.view.bouncescrollview.AbBounceScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.activity.WebViewActivity;
import com.tianyalei.communitynews.adapter.RSSFeedListViewAdapter;
import com.tianyalei.communitynews.utils.rssfeed.RSSFeedUtil;
import com.tianyalei.communitynews.utils.rssfeed.RSSItem;

import java.util.ArrayList;
import java.util.List;

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
    private RSSFeedListViewAdapter mRSSFeedListViewAdapter;
    private Intent mIntent;
    private List<RSSItem> list = new ArrayList<RSSItem>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mRSSFeedListViewAdapter = new RSSFeedListViewAdapter(mContext, list);
                    mAbBounceListView.setAdapter(mRSSFeedListViewAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.main_second_fragment, null);
        ViewUtils.inject(this, mView);
        mIntent = new Intent(mContext, WebViewActivity.class);
        mAbBounceScrollView.setCallBack(new AbBounceScrollView.Callback() {
            @Override
            public void callback() {
                Toast.makeText(mContext, "you can do something!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mAbBounceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent.putExtra("url", list.get(position).getLink());
                startActivity(mIntent);

            }
        });
        getRssFeed();
        return mView;
    }

    private void getRssFeed() {

        RSSFeedUtil.getRSSFeed(new GetDataCallBack() {
            @Override
            public void failure() {
                super.failure();
            }

            @Override
            public <T> void successBean(T bean) {
                list = (List<RSSItem>) bean;
                mHandler.sendEmptyMessage(0);
            }
        });
    }
}
