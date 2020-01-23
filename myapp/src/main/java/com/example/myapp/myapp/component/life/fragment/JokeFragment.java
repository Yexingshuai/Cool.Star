package com.example.myapp.myapp.component.life.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.life.LifeFragment3;
import com.example.myapp.myapp.data.bean.JokeBean;
import com.example.myapp.myapp.data.source.joke.JokeFragmentRepository;
import com.example.myapp.myapp.ui.layoutmanager.ScrollLinearManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;


public class JokeFragment extends BaseFragment implements JokeFragmentContract.View, BaseActivity.TurnBackListener {

    private RecyclerView mRecyclerView;
    private ScrollLinearManager scrollLinearManager;
    private List<JokeBean.DataBean> mlist = new ArrayList();
    private JokeFragmentContract.Presenter mPresenter;
    public static final String FG_TYPE = "fg_type";
    private String mFgType;
    private MultiTypeAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private int mPageNum;

    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return true;
    }


    public static JokeFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(FG_TYPE, type);
        JokeFragment jokeFragment = new JokeFragment();
        jokeFragment.setArguments(bundle);
        return jokeFragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BaseActivity activity = (BaseActivity) context;
        activity.addOnTurnBackListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragmet_joke;
    }

    @Override
    public void initView() {
        new JokeFragmentPresenter(new JokeFragmentRepository(), this);
        mFgType = getArguments().getString(FG_TYPE);
        mRecyclerView = getView(R.id.recyclerView);
        mRefreshLayout = getView(R.id.refreshLayout);
    }

    @Override
    public void initData() {

        mRefreshLayout.autoRefresh();
        scrollLinearManager = new ScrollLinearManager(mCtx, LinearLayoutManager.VERTICAL, false);
        scrollLinearManager.setCanScrollVertically(true);
        mRecyclerView.setLayoutManager(scrollLinearManager);

        mAdapter = new MultiTypeAdapter(mCtx, mlist);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                Jzvd.releaseAllVideos();
            }
        });

        mPresenter.requestJokeInfo(mPageNum, mFgType);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = PAGE_NUMBER_DEFAULT;
                mPresenter.requestJokeInfo(mPageNum, mFgType);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestMoreJokeInfo(mPageNum++, mFgType);

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 刷新
     *
     * @param jokeBean
     */
    @Override
    public void setJokeInfo(JokeBean jokeBean) {
        mRefreshLayout.finishRefresh();

        mlist.clear();

        List<JokeBean.DataBean> jokeBeanData = jokeBean.getData();


        if (jokeBeanData != null) {
            if (jokeBeanData.size() == 0) return;
            mlist.addAll(jokeBeanData);
        }
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 加载更多
     *
     * @param jokeBean
     */
    @Override
    public void setMoreJokeInfo(JokeBean jokeBean) {
        mRefreshLayout.finishLoadMore();
        mlist.addAll(jokeBean.getData());
        mAdapter.notifyItemRangeChanged(mlist.size(), 20);
    }

    @Override
    public void loadFail() {
        mRefreshLayout.finishRefresh();
    }


    @Override
    public void setPresenter(JokeFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean onTurnBack() {
        return Jzvd.backPress();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (msg.what == LifeFragment3.RECYCLERVIEW_TOP) {
//            RecyclerViewScrollHelper.scrollToPosition(mRecyclerView, 0);
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);
        }

    }
}
