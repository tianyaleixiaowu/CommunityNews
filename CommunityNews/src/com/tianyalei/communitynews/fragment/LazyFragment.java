package com.tianyalei.communitynews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuwf on 2015/1/14.
 * 延迟加载的fragment，只有获取用户焦点后才获取数据 ，布局不变而只修改数据
 */
public abstract class LazyFragment extends Fragment {
    /**
     * 是否是创建布局
     */
    private boolean isInit;

    /**
     * 是否可见
     */
    protected boolean isVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isInit = true;
        return doCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            doVisible();
        } else {
            isVisible = false;
            doInVisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 判断当前fragment是否显示  
        if (getUserVisibleHint()) {
            doVisible();
        }
    }

    /**
     * 界面可见时做相应的处理
     */
    private void doVisible() {
        if (isInit) {
            isInit = false;//加载数据完成
            lazyLoad();
        }
    }

    /**
     * 界面不可见时，做的操作
     */
    protected void doInVisible() {

    }

    /**
     * 界面可见时加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 填充布局的抽象方法
     */
    protected abstract View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
