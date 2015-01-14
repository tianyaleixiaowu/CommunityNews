package com.tianyalei.communitynews.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.ab.http.AbRequestParams;
import com.ab.view.pullview.AbPullToRefreshView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.adapter.ImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuwf on 2015/1/13.
 */
public class MainFirstFragment extends LazyFragment {
    private Activity mActivity = null;
    //    private List<Article> mList = null;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    private ListView mListView = null;
    private int currentPage = 1;
    //    private ArticleListAdapter myListViewAdapter = null;
    private int total = 50;
    private int pageSize = 5;
    /**
     * ViewPager
     */
    @ViewInject(R.id.banner_viewpager)
    private ViewPager mViewPager;
    /**
     * 轮播图片集合
     */
    private List<ImageView> mImageViewList;

    /**
     * 图片ID集合
     */
    private int[] imageResId;
    /**
     * 当前图片索引
     */
    private int currentItem = 0;
    /**
     * 我也不知道是啥....
     */
    private ScheduledExecutorService mScheduledExecutorService;
    /**
     * 小点
     */
    private List<View> dots;
    private Context mContext;
    private View mView;
    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View doCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mActivity = this.getActivity();

        mView = inflater.inflate(R.layout.pull_to_refresh_list, null);
        // 获取ListView对象
        ViewUtils.inject(this, mView);

        mAbPullToRefreshView = (AbPullToRefreshView) mView
                .findViewById(R.id.mPullRefreshView);
        mListView = (ListView) mView.findViewById(R.id.mListView);

        // 设置监听器
        mAbPullToRefreshView
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        refreshTask();
                    }
                });
        mAbPullToRefreshView
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        loadMoreTask();

                    }
                });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        initBanner();
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        // ListView数据
//        mList = new ArrayList<Article>();
//
//        // 使用自定义的Adapter
//        myListViewAdapter = new ArticleListAdapter(mActivity, mList);
//        mListView.setAdapter(myListViewAdapter);
//        // item被点击事件
//        mListView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//            }
//        });

        // 加载数据必须
//        this.setAbFragmentOnLoadListener(new AbFragmentOnLoadListener() {
//
//            @Override
//            public void onLoad() {
//                // 第一次下载数据
//                refreshTask();
//            }

//        });

        return mView;
    }


    /**
     * 初始化
     */
    private void initBanner() {
        imageResId = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        mImageViewList = new ArrayList<ImageView>();
        // 初始化图片资源
        for (int anImageResId : imageResId) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setImageResource(anImageResId);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 首页banner图片广告点击响应
                }
            });
            mImageViewList.add(imageView);
        }
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.v_dot0));
        dots.add(mView.findViewById(R.id.v_dot1));
        dots.add(mView.findViewById(R.id.v_dot2));
        dots.add(mView.findViewById(R.id.v_dot3));
        dots.add(mView.findViewById(R.id.v_dot4));
        mViewPager.setAdapter(new ImageViewPagerAdapter(mImageViewList));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 下载数据
     */
    public void refreshTask() {
        currentPage = 1;
        // 绑定参数
        AbRequestParams params = new AbRequestParams();
        params.put("cityCode", "11");
        params.put("pageSize", String.valueOf(pageSize));
        params.put("toPageNo", String.valueOf(currentPage));
        // 下载网络数据
//        NetworkWeb web = NetworkWeb.newInstance(mActivity);
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                mList.clear();
//                if (newList != null && newList.size() > 0) {
//                    mList.addAll((List<Article>) newList);
//                    myListViewAdapter.notifyDataSetChanged();
//                    newList.clear();
//                }
//                mAbPullToRefreshView.onHeaderRefreshFinish();
//
//                // 模拟用，真是开发中需要直接调用run内的内容
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // 显示内容
//                showContentView();
//            }
//
//        }, 3000);
//
//            }
//
//            @Override
//            public void onFailure(String content) {
//                AbToastUtil.showToast(mActivity, content);
//                // 显示重试的框
//                showRefreshView();
//            }
//
//        });
    }

    public void loadMoreTask() {
        currentPage++;
        // 绑定参数
        AbRequestParams params = new AbRequestParams();
        params.put("cityCode", "11");
        params.put("pageSize", String.valueOf(pageSize));
        params.put("toPageNo", String.valueOf(currentPage));
        // 下载网络数据
//        NetworkWeb web = NetworkWeb.newInstance(this.getActivity());
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                if (newList != null && newList.size() > 0) {
//                    mList.addAll((List<Article>) newList);
//                    myListViewAdapter.notifyDataSetChanged();
//                    newList.clear();
//                }
//                mAbPullToRefreshView.onFooterLoadFinish();
//            }
//
//            @Override
//            public void onFailure(String content) {
//
//            }
//
//        });
    }

    /**
     * 执行切换任务
     */
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (mViewPager) {
                currentItem = (currentItem + 1) % mImageViewList.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }
}
