package fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.ui.FilmDetailActivity;

import java.util.List;

import adapter.BasicAdapter;
import adapter.MovieTop250Adapter;
import base.BaseFragment;
import bean.FilmLive;
import bean.Root;
import bean.Subjects;
import http.DoubanApi;
import http.HttpContext;

/**
 * Created by daixiankade on 2018/5/2.
 */

public class FilmTop250Fragment extends BaseFragment {

    private RecyclerView rv;
    private MovieTop250Adapter top250Adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_top250;
    }

    @Override
    public void initData() {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        httpContext.execute(doubanApi.getTop250(0, 100), new HttpContext.Response<Root>() {
            @Override
            public void success(Root result) {
                checkDatas(result.getSubjects());
                top250Adapter.addDatas(result.getSubjects());
            }
            @Override
            public void fail(String error) {
                Toast.makeText(mCtx, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() {
        rv = getView(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        top250Adapter = new MovieTop250Adapter(mCtx);
        top250Adapter.setOnItemClickListener(new BasicAdapter.OnItemClickListener<Subjects>() {

            @Override
            public void onClick(View view, int position, Subjects subjects) {
                String id = subjects.getId(); //电影ID
                Intent intent = new Intent(mCtx, FilmDetailActivity.class);
                intent.putExtra("movieId", id);
                startActivity(intent);
            }
        });
        rv.setAdapter(top250Adapter);
    }
}
