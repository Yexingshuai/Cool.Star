package com.example.myapp.myapp.component.siri.helper;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * Fragment的调度
 */
public class NavHelper<T> {


    private SparseArray<Tab<T>> tabs = new SparseArray();

    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private OnTabChangedListener<T> mOnTabChangedListener;

    private Tab<T> mCurrentTab;

    public NavHelper(Context context, int containerId, FragmentManager fragmentManager, OnTabChangedListener<T> onTabChangedListener) {
        mContext = context;
        mContainerId = containerId;
        mFragmentManager = fragmentManager;
        mOnTabChangedListener = onTabChangedListener;
    }

    /**
     * 添加tab
     *
     * @return
     */
    public NavHelper<T> addTab(int tabId, Tab<T> tab) {
        tabs.put(tabId, tab);
        return this;
    }


    /**
     * 获取当前的Tab
     *
     * @return
     */
    public Tab<T> getCurrentTab() {
        return mCurrentTab;
    }

    /**
     * 执行点击菜单的操作
     *
     * @param menuId
     * @return
     */
    public boolean performClickMenu(int menuId) {
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    //进行选中操作
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (mCurrentTab != null) {
            oldTab = mCurrentTab;
            if (oldTab == tab) {
                //如果点击的就是当前的tab，那么不做处理
                notifyTabReselect(tab);
                return;
            }
        }
        //赋值
        mCurrentTab = tab;
        doTabChanged(mCurrentTab, oldTab);
    }


    //Fragment真实调度操作
    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (oldTab != null) {
            if (oldTab.fragment != null) {
                // 从界面移除，但缓存还在
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab != null) {
            if (newTab.fragment == null) {
                //首次创建   但是还在Fragment的缓存空间中
                newTab.fragment = Fragment.instantiate(mContext, newTab.clz.getName());
                //提交到ft
                ft.add(mContainerId, newTab.fragment, newTab.clz.getName());
            } else {
                ft.attach(newTab.fragment);
            }
        }
        // 提交事务
        ft.commit();
        // 通知回调
        notifyTabSelect(newTab, oldTab);
    }

    /**
     * 回调监听器
     *
     * @param newTab 新的Tab<T>
     * @param oldTab 旧的Tab<T>
     */
    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        if (mOnTabChangedListener != null) {
            mOnTabChangedListener.onTabChanged(newTab, oldTab);
        }
    }


    private void notifyTabReselect(Tab<T> tab) {
        // TODO 二次点击Tab所做的操作
    }


    //Tab的实体类
    public static class Tab<T> {

        public Class<?> clz;  //Fragment对应的class

        public T extra;  //额外的信息

        // 内部缓存的对应的Fragment，
        // Package权限，外部无法使用
        Fragment fragment;

        public Tab(Class clz, T extra) {
            this.clz = clz;
            this.extra = extra;
        }

    }


    /**
     * 定义事件处理完成后的回调接口
     */
    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}

