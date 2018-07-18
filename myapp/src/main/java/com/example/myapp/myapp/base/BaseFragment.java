package com.example.myapp.myapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;
import com.example.myapp.myapp.ui.view.StateLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;

/**
 * Created by daixiankade on 2018/3/28.
 */

public abstract class BaseFragment extends Fragment {
    protected String TAG_LOG = this.getClass().getSimpleName();
    protected View mFragmentView;
    protected Context mCtx;
    protected StateLayout stateLayout;


    @Override
    public void onStart() {
        super.onStart();
        //EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
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
        stateLayout = (StateLayout) inflater.inflate(R.layout.state_layout, container, false);
        mFragmentView = inflater.inflate(getLayoutId(), container, false);
        stateLayout.setConentView(mFragmentView);
        initView();
        return stateLayout;
//        return mFragmentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化控件
        initData();
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
     * 根据集合中的数据决定显示哪一个状态的View，如果数据是OK的，则返回true
     *
     * @param datas
     * @return
     */
    public boolean checkDatas(Collection<?> datas) {
        boolean result = false;
        if (datas == null) {
            stateLayout.showFailView();
        } else if (datas.isEmpty()) {
            stateLayout.showEmptyView();
        } else {
            stateLayout.showContentView();
            result = true;
        }
        return result;
    }

    /**
     * 展示自己正常界面
     *
     * @return
     */
    public void showContentView() {
        stateLayout.showContentView();
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initView();

}
