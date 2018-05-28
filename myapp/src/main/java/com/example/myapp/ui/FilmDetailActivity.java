package com.example.myapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ActorAdapter;
import adapter.SpaceItemDecoration;
import bean.Casts;
import bean.Directors;
import bean.FilmDetail;
import bean.FilmPeople;
import http.DoubanApi;
import http.HttpContext;
import utils.GlideContext;

/**
 * Created by daixiankade on 2018/5/2.
 */

public class FilmDetailActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_file_name;
    private ImageView iv_film;
    private TextView tv_rating;
    private TextView tv_rating_num;
    private TextView tv_date_and_film_time;
    private TextView tv_film_type;
    private TextView tv_film_country;
    private TextView tv_film_name_real;
    private String movieId;
    private TextView tv_description;
    private ImageView iv_back;
    private RecyclerView rv;
    private Button bt_buy;
    private String alt;
    private ProgressDialog progressDialog;

    @Override
    public int inflateContentView() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍等..");
        progressDialog.show();
        //电影名称 标题栏
        tv_file_name = getView(R.id.tv_file_name);
        //电影图片
        iv_film = getView(R.id.iv_film);
        iv_film.setOnClickListener(this);
        //电影评分
        tv_rating = getView(R.id.tv_rating);
        //电影评分人数
        tv_rating_num = getView(R.id.tv_rating_num);
        //电影出品时间
        tv_date_and_film_time = getView(R.id.tv_date_and_film_time);
        //电影类型
        tv_film_type = getView(R.id.tv_film_type);
        //电影出品国家
        tv_film_country = getView(R.id.tv_film_country);
        //电影原名
        tv_film_name_real = getView(R.id.tv_film_name_real);
        //电影描述
        tv_description = getView(R.id.tv_description);
        iv_back = getView(R.id.iv_back);
        //演员
        rv = getView(R.id.rv);
        bt_buy = getView(R.id.bt_buy);
        bt_buy.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new SpaceItemDecoration(40, 0, 0, 0));
        iv_back.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getStringExtra("movieId");
        }


    }

    @Override
    protected void initData() {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        if (movieId != null) {
            httpContext.execute(doubanApi.getFilmDetail(movieId), new HttpContext.Response<FilmDetail>() {

                @Override
                public void success(FilmDetail filmDetail) {
                    progressDialog.dismiss();
                    if (filmDetail.getImages() != null && filmDetail.getImages().getLarge() != null) {
                        GlideContext.loadCommon(FilmDetailActivity.this, filmDetail.getImages().getLarge(), iv_film);
                    }
                    if (!TextUtils.isEmpty(filmDetail.getTitle())) {
                        tv_file_name.setText(filmDetail.getTitle());
                    }
                    if (filmDetail.getRating() != null) {
                        tv_rating.setText("评分" + filmDetail.getRating().getAverage());
                    }
                    tv_rating_num.setText(filmDetail.getRatings_count() + "人评分");
                    tv_date_and_film_time.setText(filmDetail.getYear() + "年  出品");
                    if (filmDetail.getCountries() != null && filmDetail.getCountries().size() > 0) {
                        tv_film_country.setText(filmDetail.getCountries().get(0));
                    }
                    if (filmDetail.getGenres() != null && filmDetail.getGenres().size() > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : filmDetail.getGenres()) {
                            stringBuilder.append(s + "/");
                        }
                        tv_film_type.setText(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
                    }
                    tv_description.setText(filmDetail.getSummary());
                    tv_film_name_real.setText(filmDetail.getOriginal_title() + " [原名]");
                    //电影链接
                    alt = filmDetail.getAlt();
                    initActor(filmDetail);
                }

                @Override
                public void fail(String error) {
                    Toast.makeText(FilmDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private List<FilmPeople> list = new ArrayList<>();

    private void initActor(FilmDetail filmDetail) {
        if (filmDetail.getDirectors() != null && filmDetail.getDirectors().size() > 0) {
            for (int i = 0; i < filmDetail.getDirectors().size(); i++) {
                Directors directors = filmDetail.getDirectors().get(i);
                FilmPeople filmPeople = new FilmPeople();

                filmPeople.setAlt(directors.getAlt());
                filmPeople.setAvatars(directors.getAvatars());
                filmPeople.setId(directors.getId());
                filmPeople.setName(directors.getName());
                filmPeople.setType(1);
                list.add(filmPeople);
            }
        }
        if (filmDetail.getCasts() != null && filmDetail.getCasts().size() > 0) {
            for (int i = 0; i < filmDetail.getCasts().size(); i++) {
                Casts casts = filmDetail.getCasts().get(i);
                FilmPeople filmPeople = new FilmPeople();

                filmPeople.setAlt(casts.getAlt());
                filmPeople.setAvatars(casts.getAvatars());
                filmPeople.setId(casts.getId());
                filmPeople.setName(casts.getName());
                filmPeople.setType(2);
                list.add(filmPeople);
            }
        }
        ActorAdapter actorAdapter = new ActorAdapter(this, list);
        rv.setAdapter(actorAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_buy:
                Toast.makeText(FilmDetailActivity.this, "wait...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_film:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("webUrl", alt);
                startActivity(intent);
                break;

        }
    }
}
