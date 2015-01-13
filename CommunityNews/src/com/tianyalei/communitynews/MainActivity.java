package com.tianyalei.communitynews;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ab.activity.AbActivity;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianyalei.communitynews.adapter.ImageViewPagerAdapter;
import com.tianyalei.communitynews.utils.BaiduPushUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AbActivity {
    /**
     * ViewPager
     */
    @ViewInject(R.id.vp)
    private ViewPager mViewPager;
    /**
     * 轮播图片集合
     */
    private List<ImageView> mImageViewList;
    /**
     * 标题集合
     */
    private String[] titles;
    /**
     * 图片ID集合
     */
    private int[] imageResId;
    /**
     * 标题textview
     */
    @ViewInject(R.id.tv_title)
    private TextView mTvTitle;
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
    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mContext = this;
        initPushService();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        imageResId = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        titles = new String[imageResId.length];
        titles[0] = "巩俐不低俗，我就不能低俗";
        titles[1] = "扑树又回来啦！再唱经典老歌引万人大合唱";
        titles[2] = "揭秘北京电影如何升级";
        titles[3] = "乐视网TV版大派送";
        titles[4] = "热血屌丝的反杀";

        mImageViewList = new ArrayList<ImageView>();

        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViewList.add(imageView);
        }
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.v_dot0));
        dots.add(findViewById(R.id.v_dot1));
        dots.add(findViewById(R.id.v_dot2));
        dots.add(findViewById(R.id.v_dot3));
        dots.add(findViewById(R.id.v_dot4));
        mTvTitle.setText(titles[0]);//
        mViewPager.setAdapter(new ImageViewPagerAdapter(mImageViewList));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    protected void onStart() {
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 当Activity不可见的时候停止切换
        mScheduledExecutorService.shutdown();
        super.onStop();
    }

    private void initPushService() {
        // 以下是您原先的代码实现，保持不变
        Resources resource = this.getResources();
        String pkgName = this.getPackageName();

        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                BaiduPushUtils.getMetaValue(this, "api_key"));
        // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
        // PushManager.enableLbs(getApplicationContext());

        // Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
        // 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
        // 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                getApplicationContext(), resource.getIdentifier(
                "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        PushManager.setNotificationBuilder(this, 1, cBuilder);
    }

    // 打开富媒体列表界面
    private void openRichMediaList() {
        // Push: 打开富媒体消息列表
        Intent sendIntent = new Intent();
        sendIntent.setClassName(getBaseContext(),
                "com.baidu.android.pushservice.richmedia.MediaListActivity");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(sendIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Hook();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    ///对于好多应用，会在程序中杀死 进程，这样会导致我们统计不到此时Activity结束的信息，
    ///对于这种情况需要调用 'MobclickAgent.onKillProcess( Context )'
    ///方法，保存一些页面调用的数据。正常的应用是不需要调用此方法的。
    private void Hook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("退出应用",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MobclickAgent.onKillProcess(mContext);

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                    }
                });
        builder.setNeutralButton("后退一下",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                });
        builder.setNegativeButton("点错了",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        builder.show();
    }

    /**
     * 执行切换任务
     */
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (mViewPager) {
                System.out.println("currentItem: " + currentItem);
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
            mTvTitle.setText(titles[position]);
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
