package com.tianyalei.communitynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.ab.adapter.AbCommonAdapter;
import com.ab.util.AbViewHolder;
import com.tianyalei.communitynews.R;

import java.util.List;

/**
 * Created by wuwf on 2015/1/14.
 */
public class MainFirstFragmentListAdapter extends AbCommonAdapter {

    public MainFirstFragmentListAdapter(Context context, List<?> objects) {
        super(context, objects);
    }

    @Override
    public void getView(int position, View convertView) {
        String s = mObjects.get(position).toString();
        TextView textView = AbViewHolder.get(convertView, R.id.listview_text);
        textView.setText(s);

    }

    @Override
    public int getLayoutId() {
        return R.layout.first_fragment_listview_item;
    }
}
