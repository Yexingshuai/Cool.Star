package com.example.myapp.myapp.component.movie.live;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.ui.adapter.FilmLiveAdapter;
import com.example.myapp.myapp.ui.adapter.SpaceItemDecoration;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.data.bean.FilmLive;
import com.example.myapp.myapp.data.bean.Subjects;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.utils.Utils;

import java.util.List;

/**
 * Created by Yx on 2018/5/2.
 */

public class FilmLiveFragment extends BaseFragment implements FilmLiveContract.View {


    private RecyclerView mRecyclerView;
    private FilmLiveAdapter filmLiveAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FilmLiveContract.Presenter mPresenter;
    private LoadingStatusLayout mLoadingStatusLayout;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.film_live;
    }

    @Override
    public void initData() {
        mPresenter.getLiveFilm();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        mLoadingStatusLayout.setErrorRetryListener(new LoadingStatusLayout.ErrorRetryListener() {
            @Override
            public void retry() {
                refreshData();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mCtx, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceItemDecoration spacesItemDecoration = new SpaceItemDecoration(Utils.dp2px(mCtx, 12), Utils.dp2px(mCtx, 12), Utils.dp2px(mCtx, 12), 0);
        mRecyclerView.addItemDecoration(spacesItemDecoration);
        filmLiveAdapter = new FilmLiveAdapter(mCtx);
        mRecyclerView.setAdapter(filmLiveAdapter);
        filmLiveAdapter.setOnItemClickListener(new FilmLiveAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, Object data) {
                Subjects subjects = (Subjects) data;
                String id = subjects.getId(); //电影ID
                UiHelper.skipFilmActivity(mCtx, view, id);
            }
        });
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.rv);
        mSwipeRefreshLayout = getView(R.id.swipe);
        mLoadingStatusLayout = getView(R.id.loadingStatusLayout);
    }


    @Override
    public void setPresenter(FilmLiveContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLiveFilmInfo(FilmLive result) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mLoadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
        List<Subjects> subjects = result.getSubjects();
        filmLiveAdapter.addFilmData(subjects);
    }

    /**
     * 数据请求失败
     *
     * @param errorMsg
     */
    @Override
    public void getLiveFilmFail(String errorMsg) {
        mLoadingStatusLayout.setStatus(LoadingStatusLayout.ERROR_STATUS);
        mLoadingStatusLayout.setErrorInfo(errorMsg);
    }

    public static FilmLiveFragment newInstance() {
        return new FilmLiveFragment();
    }
}
