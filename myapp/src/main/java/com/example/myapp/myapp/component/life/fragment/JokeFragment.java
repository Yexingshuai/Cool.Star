package com.example.myapp.myapp.component.life.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.data.bean.FavoriteResponse;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.layoutmanager.ScrollLinearManager;

import java.util.ArrayList;
import java.util.logging.Handler;

public class JokeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipe;
    private ScrollLinearManager scrollLinearManager;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragmet_joke;
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.recyclerView);
        mSwipe = getView(R.id.swipeRefreshLayout);
    }

    @Override
    public void initData() {
        scrollLinearManager = new ScrollLinearManager(mCtx, LinearLayoutManager.VERTICAL, false);
        scrollLinearManager.setCanScrollVertically(true);
        mRecyclerView.setLayoutManager(scrollLinearManager);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <50 ; i++) {
            list.add("");

        }
        RecyclerAdapter mAdapter = new RecyclerAdapter<String>(R.layout.item_favorite, list) {

            @Override
            protected void onBindHolder(RecyclerHolder holder, String model, int position) {
                holder.text(R.id.tv_title, "hahahhahahaa");
                holder.text(R.id.tv_collect_date, "");
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                },1000);

            }
        });
    }



}
