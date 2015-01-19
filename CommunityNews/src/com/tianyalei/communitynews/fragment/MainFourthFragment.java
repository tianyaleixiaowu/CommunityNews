package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.ab.view.pullscrollview.PullScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tianyalei.communitynews.R;

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

    @OnClick({R.id.feedback_text_view, R.id.update_text_view, R.id.about_text_view})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.feedback_text_view:
                Toast.makeText(mContext, "意见反馈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.update_text_view:
                Toast.makeText(mContext, "检查更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_text_view:
                Toast.makeText(mContext, "关于", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.activity_pull_scroll_view, null);
        ViewUtils.inject(this, mView);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
        return mView;
    }

    @Override
    public void onTurn() {

    }

}
