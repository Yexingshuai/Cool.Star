package com.example.myapp.myapp.component.life.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.component.life.viewholder.TextRVHolder;
import com.example.myapp.myapp.data.bean.FavoriteResponse;
import com.example.myapp.myapp.data.source.joke.JokeFragmentRepository;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.layoutmanager.ScrollLinearManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;

public class JokeFragment extends BaseFragment implements JokeFragmentContract.View {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipe;
    private ScrollLinearManager scrollLinearManager;
    private List<JokeBean.DataBean> mlist = new ArrayList();
    private JokeFragmentContract.Presenter mPresenter;
    public static final String FG_TYPE = "fg_type";
    private int mFgType;
    private MultiTypeAdapter mAdapter;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    public static JokeFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(FG_TYPE, type);
        JokeFragment jokeFragment = new JokeFragment();
        jokeFragment.setArguments(bundle);
        return jokeFragment;

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragmet_joke;
    }

    @Override
    public void initView() {
        new JokeFragmentPresenter(new JokeFragmentRepository(), this);
        mFgType = getArguments().getInt(FG_TYPE);
        mRecyclerView = getView(R.id.recyclerView);
        mSwipe = getView(R.id.swipeRefreshLayout);
    }

    @Override
    public void initData() {
        mSwipe.setRefreshing(true);
        scrollLinearManager = new ScrollLinearManager(mCtx, LinearLayoutManager.VERTICAL, false);
        scrollLinearManager.setCanScrollVertically(true);
        mRecyclerView.setLayoutManager(scrollLinearManager);

        mAdapter = new MultiTypeAdapter(mCtx, mlist);
        mRecyclerView.setAdapter(mAdapter);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestJokeInfo(mFgType);
            }
        });
        mPresenter.requestJokeInfo(mFgType);
    }


    @Override
    public void setJokeInfo(JokeBean jokeBean) {
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        mlist.clear();

        List<JokeBean.DataBean> jokeBeanData = jokeBean.getData();
        mlist.addAll(jokeBeanData);
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 去除掉空数据
     *
     * @param jokeBeanData
     */
    private List<JokeBean.DataBean> dataCollation(List<JokeBean.DataBean> jokeBeanData) {
        Iterator<JokeBean.DataBean> iterator = jokeBeanData.iterator();
        while (iterator.hasNext()) {
            if (TextUtils.equals(iterator.next().type, "text")) {
                JokeBean.DataBean dataBean = iterator.next();
                if (TextUtils.isEmpty(dataBean.text)) {
                    iterator.remove();
                }
            }
        }
        return jokeBeanData;

    }

    @Override
    public void setPresenter(JokeFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
