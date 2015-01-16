package com.tianyalei.communitynews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.ab.http.AbRequestParams;
import com.ab.image.AbImageLoader;
import com.ab.view.pullview.AbPullToRefreshView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.adapter.ImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuwf on 2015/1/13.
 */
public class MainFirstFragment extends LazyFragment {
    private Context mContext;
    private int currentPage = 1;
    private int total = 50;
    private int pageSize = 5;
    /**
     * ViewPager
     */
    @ViewInject(R.id.banner_viewpager)
    private ViewPager mViewPager;
    /**
     * AbPullToRefreshView
     */
    @ViewInject(R.id.mPullRefreshView)
    private AbPullToRefreshView mAbPullToRefreshView;
    /**
     * Listview
     */
    @ViewInject(R.id.mListView)
    private ListView mListView;
    /**
     * 轮播图片集合
     */
    private List<ImageView> mImageViewList;

    /**
     * 图片ID集合
     */
    private String[] imageUrls;
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
    private View mView;
    private AbImageLoader mAbImageLoader;
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
        mContext = getActivity();
        mAbImageLoader = new AbImageLoader(mContext);
        mAbImageLoader.setEmptyImage(R.drawable.a);
        mAbImageLoader.setErrorImage(R.drawable.a);
        mAbImageLoader.setLoadingImage(R.drawable.a);
        mView = inflater.inflate(R.layout.main_first_fragment, null);
        // 获取ListView对象
        ViewUtils.inject(this, mView);

        // 设置监听器
        mAbPullToRefreshView
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        refreshTask();
                    }
                });
        mAbPullToRefreshView.setLoadMoreEnable(false);

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
//        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));


        initBanner();

        // ListView数据
//        List mList = new ArrayList<String>();
//        mList.add("1");
//        mList.add("1");
//        mList.add("1");
//        mList.add("1");
//        AbCommonAdapter adapter = new MainFirstFragmentListAdapter(mContext, mList);
//        mListView.setAdapter(adapter);
        mListView.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, new ArrayList<String>(
                Arrays.asList("Hello", "World", "Welcome", "Java",
                        "Android", "Lucene", "C++", "C#", "HTML",
                        "Welcome", "Java", "Android", "Lucene", "C++",
                        "C#", "HTML"))));

        return mView;
    }


    /**
     * 初始化
     */
    private void initBanner() {
        imageUrls = new String[]{"http://img5.imgtn.bdimg.com/it/u=2501461001,3027115012&fm=23&gp=0.jpg", "http://img4.imgtn.bdimg.com/it/u=1903407360,396224632&fm=90&gp=0.jpg", "http://img0.imgtn.bdimg.com/it/u=1709132177,2851691885&fm=23&gp=0.jpg", "http://img5.imgtn.bdimg.com/it/u=943320339,2732010194&fm=23&gp=0.jpg", "http://img1.imgtn.bdimg.com/it/u=3701920778,67538327&fm=21&gp=0.jpg"};
        mImageViewList = new ArrayList<ImageView>();
        // 初始化图片资源
        for (String imageUrl : imageUrls) {
            ImageView imageView = new ImageView(mContext);
            mAbImageLoader.display(imageView, imageUrl);
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


        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
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
