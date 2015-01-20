package com.tianyalei.communitynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.ab.adapter.AbCommonAdapter;
import com.ab.util.AbViewHolder;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.utils.rssfeed.RSSItem;

import java.util.List;

/**
 * Created by 毅涵 on 15/1/20.
 */
public class RSSFeedListViewAdapter extends AbCommonAdapter {
    List<RSSItem> mList;

    public RSSFeedListViewAdapter(Context context, List<?> objects) {
        super(context, objects);
        mList = (List<RSSItem>) objects;
    }

    @Override
    public void getView(int position, View convertView) {
        TextView title = AbViewHolder.get(convertView, R.id.rss_title);
        TextView content = AbViewHolder.get(convertView, R.id.rss_content);
        title.setText(mList.get(position).getTitle());
        content.setText(mList.get(position).getDescription());
    }

    @Override
    public int getLayoutId() {
        return R.layout.rss_feed_list_view_item;
    }
}
