package com.example.myapp.myapp.component.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.data.bean.FavoriteResponse;
import com.example.myapp.myapp.data.source.favorite.MyFavoriteRepository;
import com.example.myapp.myapp.ui.activity.WebActivity;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.utils.ToastUtil;

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

    /**
     * 列表的Item 点击事件
     */
    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(MyFavoriteActivity.this, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", mFavoriteList.get(position).getTitle());
            bundle.putString("webUrl", mFavoriteList.get(position).getLink());
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };


    @Override
    public int inflateContentView() {
        return R.layout.activity_myfavorite;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new MyFavoritePresenter(new MyFavoriteRepository(), this);
        mRecyclerView = getView(R.id.recyclerview);
        mPresenter.getFavoriteList(pageNum);

    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerAdapter<FavoriteResponse.DataBean.DatasBean>(R.layout.item_favorite, mFavoriteList, mOnItemClickListener) {

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
        FavoriteResponse.DataBean data = response.getData();
        List<FavoriteResponse.DataBean.DatasBean> datas = data.getDatas();
        mFavoriteList.addAll(datas);
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void setPresenter(MyFavoriteContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
