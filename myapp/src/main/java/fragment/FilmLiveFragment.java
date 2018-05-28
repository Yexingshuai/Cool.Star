package fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.example.myapp.R;
import com.example.myapp.ui.FilmDetailActivity;
import java.util.List;
import adapter.FilmLiveAdapter;
import adapter.SpaceItemDecoration;
import base.BaseFragment;
import bean.FilmLive;
import bean.Subjects;
import http.DoubanApi;
import http.HttpContext;
import utils.MyAnimationUtils;
import utils.Utils;

/**
 * Created by Yx on 2018/5/2.
 */

public class FilmLiveFragment extends BaseFragment {


    private RecyclerView rv;
    private FilmLiveAdapter filmLiveAdapter;
    private SwipeRefreshLayout swipe;

    @Override
    public int getLayoutId() {
        return R.layout.film_live;
    }

    @Override
    public void initData() {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        httpContext.execute(doubanApi.getLiveFilm(), new HttpContext.Response<FilmLive>() {
            @Override
            public void success(FilmLive result) {
                List<Subjects> subjects = result.getSubjects();
                checkDatas(subjects);
                filmLiveAdapter.addFilmData(subjects);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
            }

            @Override
            public void fail(String error) {
                Toast.makeText(mCtx,"免费API就是垃圾,下拉刷新试下吧,不然就等等...",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void initView() {
        rv = getView(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mCtx, 3);
        rv.setLayoutManager(gridLayoutManager);
        SpaceItemDecoration spacesItemDecoration=new SpaceItemDecoration(Utils.dp2px(mCtx,12),Utils.dp2px(mCtx,12),Utils.dp2px(mCtx,12),0);
        rv.addItemDecoration(spacesItemDecoration);
        swipe = getView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        filmLiveAdapter = new FilmLiveAdapter(mCtx);
        rv.setAdapter(filmLiveAdapter);
        filmLiveAdapter.setOnItemClickListener(new FilmLiveAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, Object data) {
                Subjects subjects = (Subjects) data;
                String id = subjects.getId(); //电影ID
                Bundle bannerBundle = MyAnimationUtils.makeSceneTransition((Activity) mCtx, view, "fileIv");
                Intent intent = new Intent(mCtx, FilmDetailActivity.class);
                intent.putExtra("movieId",id);
                startActivity(intent,bannerBundle);
            }
        });

    }
}
