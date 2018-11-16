package com.example.myapp.myapp.component.news.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.data.bean.NewsDetailResponse;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/10/11.
 */

public class NewsDetailFragment extends BaseFragment implements NewsDetailContract.View {
    public static final String CID = "CID";
    private int mCid;
    private RecyclerView mRecyclerView;
    private LoadingStatusLayout mLoadingStatusLayout;
    /**
     * 默认size
     */
    public static final int DEFAULT_SIZE = 20;
    /**
     * 默认page
     */
    public static final int DEFAULT_PAGE = 1;
    private int mPageNum = DEFAULT_PAGE;
    private NewsDetailContract.Presenter mPresenter;
    private SmartRefreshLayout smartRefreshLayout;
    private List<NewsDetailResponse.ResultBean.ListBean> mList = new ArrayList();


    /**
     * 新闻条目点击
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsDetailResponse.ResultBean.ListBean listBean = mList.get(position);
            String sourceUrl = listBean.getSourceUrl();
            UiHelper.skipWebActivity(getActivity(), "", sourceUrl);
            AppFlag.isFromNews = true;

        }
    };
    private RecyclerAdapter<NewsDetailResponse.ResultBean.ListBean> mAdapter;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newsdetail;
    }

    public static NewsDetailFragment newInstance(int cid) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CID, cid);
        newsDetailFragment.setArguments(bundle);
        return newsDetailFragment;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        mCid = arguments.getInt(CID);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageNum++;
                mPresenter.requestNewsDetail(mCid, mPageNum, DEFAULT_SIZE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = DEFAULT_PAGE;
                mPresenter.requestNewsDetail(mCid, mPageNum, DEFAULT_SIZE);
            }
        });

        mAdapter = new RecyclerAdapter<NewsDetailResponse.ResultBean.ListBean>(R.layout.item_news_detail, mList, onItemClickListener) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, NewsDetailResponse.ResultBean.ListBean model, int position) {
                holder.text(R.id.title_news_item, model.getTitle());
                holder.text(R.id.title_time, model.getPubTime());//onFormatTime(model.getPubTime())
                holder.image(getActivity(), R.id.iv_news, model.getThumbnails());
            }
        };
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)); //添加默认分割线
        mRecyclerView.setAdapter(mAdapter);
        smartRefreshLayout.autoRefresh();
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.recyclerview);
        mLoadingStatusLayout = getView(R.id.loadingStatusLayout);
        smartRefreshLayout = getView(R.id.refreshLayout);
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void requestNewsSuccess(NewsDetailResponse response) {

        mLoadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
        NewsDetailResponse.ResultBean result = response.getResult();
        int curPage = result.getCurPage();
        List<NewsDetailResponse.ResultBean.ListBean> list = result.getList();
        //判断是下拉刷新 还是加载更多
        if (curPage == DEFAULT_PAGE) {
            smartRefreshLayout.finishRefresh();
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            smartRefreshLayout.finishLoadMore();
            mList.addAll(list);
            mAdapter.notifyItemRangeChanged(mList.size(), list.size());
        }

    }

    @Override
    public void requestNewsDetail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

}
