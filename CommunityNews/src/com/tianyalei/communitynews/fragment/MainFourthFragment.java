package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.view.PullScrollView;

/**
 * Created by 毅涵 on 15/1/15.
 */
public class MainFourthFragment extends LazyFragment implements PullScrollView.OnTurnListener {
    private Context mContext;
    private View mView;
    @ViewInject(R.id.scroll_view)
    private PullScrollView mScrollView;
    @ViewInject(R.id.background_img)
    private ImageView mHeadImg;
    @ViewInject(R.id.table_layout)
    private TableLayout mMainLayout;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.main_fourth_fragment, null);
        ViewUtils.inject(this, mView);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
        showTable();
        return mView;
    }

    @Override
    public void onTurn() {

    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(mContext);
            TextView textView = new TextView(mContext);
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }
}
