package com.example.myapp.myapp.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;
import com.example.myapp.myapp.component.study.StudyFragment;
import com.example.myapp.myapp.ui.activity.MainActivity;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.ui.view.StateLayout;
import com.example.myapp.myapp.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collection;

/**
 * Created by yexing on 2018/3/28.
 */

public abstract class BaseFragment extends Fragment {
    protected String TAG_LOG = this.getClass().getSimpleName();
    protected View mFragmentView;
    protected Context mCtx;
    private boolean mIsFirstVisible;
    private boolean mHasCreatedView;//是否创建完视图

    public static final int SHOWINDICATOR = 1003;

    private View mRoot;


    @Override
    public void onStart() {
        super.onStart();
        //EventBus
        if (isNeedToBeSubscriber()) {
            if (!EventBus.getDefault().isRegistered(this)) {//加上判断
                EventBus.getDefault().register(this);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCtx = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG_LOG, "------------------onCreate");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG_LOG, "------------------onDestroy");
    }

    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
        Log.e(TAG_LOG, "------------------onDestroyView");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG_LOG, "------------------onCreateView");

        if (mRoot == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            initView();
            mRoot = mFragmentView;
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }


        return mRoot;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //已经创建完视图
        mHasCreatedView = true;
        //是否是第一次可见
        mIsFirstVisible = true;
        if (mIsFirstVisible && getUserVisibleHint()) {
            refreshData();
            mIsFirstVisible = false;
        }
        //初始化数据
        initData();
    }

    /**
     * 这个方法在onCreateView之前
     * 当Fragment可见性发生改变时会调用这个方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (mIsFirstVisible && mHasCreatedView && getUserVisibleHint()) {
            onFragmentVisibleToUser(isVisibleToUser);

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {

    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    /**
     * 查找控件
     *
     * @return
     */
    public <T> T getView(int id) {
        return (T) mFragmentView.findViewById(id);
    }

    /**
     * Callback that Fragment visible to user.
     */
    protected void onFragmentVisibleToUser(boolean isVisibleToUser) {
    }


    /**
     * 是否需要接收广播
     *
     * @return
     */
    protected abstract boolean isNeedToBeSubscriber();

    /**
     * 刷新当前页面时所需要加载的数据
     * 当前页面第一次加载时需要的数据
     */
    protected void refreshData() {
    }

    ;

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initView();

}
