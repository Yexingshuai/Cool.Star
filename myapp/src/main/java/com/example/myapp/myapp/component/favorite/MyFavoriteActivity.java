package com.example.myapp.myapp.component.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.data.bean.FavoriteResponse;
import com.example.myapp.myapp.data.source.favorite.MyFavoriteRepository;
import com.example.myapp.myapp.ui.activity.WebActivity;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/8/11.
 */

public class MyFavoriteActivity extends BaseActivity implements MyFavoriteContract.View {

    private RecyclerView mRecyclerView;
    private MyFavoriteContract.Presenter mPresenter;
    private List<FavoriteResponse.DataBean.DatasBean> mFavoriteList = new ArrayList<>();
    private RecyclerAdapter<FavoriteResponse.DataBean.DatasBean> mAdapter;
    private int pageNum = 0;
    private int removePosition;  //删除item的position
    private ProgressBar mProgressBar;
    private SmartRefreshLayout mRefreshLayout;
    /**
     * 默认的页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 0;
    /**
     * /**
     * 列表的Item 点击事件
     */
    private OnItemClickListener mRecyclerOnClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            UiHelper.skipWebActivity(MyFavoriteActivity.this, mFavoriteList.get(position).getTitle(), mFavoriteList.get(position).getLink());
        }
    };

    /**
     * 列表的子view点击事件
     */
    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            removePosition = position;
            switch (view.getId()) {
                case R.id.iv_del:
                    FavoriteResponse.DataBean.DatasBean data = mFavoriteList.get(position);
                    int id = data.getId();
                    String originId = data.getOriginId();
                    mPresenter.delCollectArtist(id, originId);
                    break;
            }
        }
    };
    private LoadingStatusLayout mLoadingStatusLayout;


    @Override
    public int inflateContentView() {
        return R.layout.activity_myfavorite;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new MyFavoritePresenter(new MyFavoriteRepository(), this);
        mRecyclerView = getView(R.id.recyclerview);
        mRefreshLayout = getView(R.id.refreshLayout);
        mProgressBar = getView(R.id.pb);
        mLoadingStatusLayout = getView(R.id.loadingStatusLayout);
    }

    @Override
    protected void initData() {
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = PAGE_NUMBER_DEFAULT;
                mPresenter.getFavoriteList(pageNum);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //加载更多功能的代码
                mPresenter.getMoreFavoreteList(++pageNum);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerAdapter<FavoriteResponse.DataBean.DatasBean>(R.layout.item_favorite, mFavoriteList, mRecyclerOnClickListener, new int[]{R.id.iv_del}, mOnItemClickListener) {

            @Override
            protected void onBindHolder(RecyclerHolder holder, FavoriteResponse.DataBean.DatasBean model, int position) {
                holder.text(R.id.tv_title, model.getTitle());
                holder.text(R.id.tv_collect_date, model.getNiceDate());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    public void setFavoriteList(FavoriteResponse response) {
        mLoadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
        mRefreshLayout.finishRefresh();
        FavoriteResponse.DataBean data = response.getData();
        List<FavoriteResponse.DataBean.DatasBean> datas = data.getDatas();
        mFavoriteList.clear();
        mFavoriteList.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 因服务器未返回分页加载数据，所以增加 加载更多接口
     *
     * @param response
     */
    @Override
    public void setMoreFavoriteList(FavoriteResponse response) {
        mRefreshLayout.finishLoadMore();
        FavoriteResponse.DataBean data = response.getData();
        List<FavoriteResponse.DataBean.DatasBean> datas = data.getDatas();
        int startSize = mFavoriteList.size();
        mFavoriteList.addAll(datas);
        mAdapter.notifyItemRangeInserted(startSize, datas.size());
    }

    /**
     * 请求失败
     *
     * @param errorMsg
     */
    @Override
    public void requestError(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    /**
     * 取消收藏成功
     */
    @Override
    public void delCollectArtistSuccess() {
        mFavoriteList.remove(removePosition);
        mAdapter.notifyItemRemoved(removePosition);
    }

    /**
     * 取消收藏失败
     *
     * @param errorMsg
     */
    @Override
    public void delCollectArtistFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    @Override
    public void setPresenter(MyFavoriteContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
