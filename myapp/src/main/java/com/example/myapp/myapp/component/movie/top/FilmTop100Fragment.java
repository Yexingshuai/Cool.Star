package com.example.myapp.myapp.component.movie.top;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.component.movie.detail.FilmDetailActivity;

import com.example.myapp.myapp.ui.adapter.BasicAdapter;
import com.example.myapp.myapp.ui.adapter.MovieTop100Adapter;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.data.bean.Root;
import com.example.myapp.myapp.data.bean.Subjects;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.utils.ToastUtil;

/**
 * Created by yexing on 2018/5/2.
 */

public class FilmTop100Fragment extends BaseFragment implements FilmTop100Contract.View {

    private RecyclerView mRecyclerView;
    private MovieTop100Adapter top250Adapter;
    private FilmTop100Contract.Presenter mPresenter;
    private LoadingStatusLayout mLoadingStatusLayout;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    protected void onFragmentVisibleToUser(boolean isVisibleToUser) {
        super.onFragmentVisibleToUser(isVisibleToUser);
//        mPresenter.getTop100();
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.rv);
        mLoadingStatusLayout = getView(R.id.loadingStatusLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_top250;
    }

    @Override
    public void initData() {
        mPresenter.getTop100();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        top250Adapter = new MovieTop100Adapter(mCtx);
        top250Adapter.setOnItemClickListener(new BasicAdapter.OnItemClickListener<Subjects>() {

            @Override
            public void onClick(View view, int position, Subjects subjects) {
                String id = subjects.getId(); //电影ID
                String title = subjects.getTitle();
                Intent intent = new Intent(mCtx, FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.MOVIEID, id);
                intent.putExtra(FilmDetailActivity.MOVIENAME, title);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(top250Adapter);
    }


    @Override
    public void setPresenter(FilmTop100Contract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTop100Data(Root root) {
        mLoadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
        top250Adapter.addDatas(root.getSubjects());
    }

    @Override
    public void getTop100fail(String errorMsg) {
        mLoadingStatusLayout.setStatus(LoadingStatusLayout.ERROR_STATUS);
        ToastUtil.showApp(errorMsg);
    }

    /**
     * 创建实例
     *
     * @return
     */
    public static FilmTop100Fragment newInstance() {
        return new FilmTop100Fragment();
    }
}
