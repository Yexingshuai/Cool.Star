package com.example.myapp.myapp.component.movie.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.data.bean.Casts;
import com.example.myapp.myapp.data.bean.Directors;
import com.example.myapp.myapp.data.bean.FilmDetail;
import com.example.myapp.myapp.data.bean.FilmPeople;
import com.example.myapp.myapp.data.source.film.detail.FilmDetailRepository;
import com.example.myapp.myapp.ui.activity.WebActivity;
import com.example.myapp.myapp.ui.adapter.ActorAdapter;
import com.example.myapp.myapp.ui.adapter.SpaceItemDecoration;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.ui.view.BlurTransformation;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/5/2.
 */

public class FilmDetailActivity extends BaseActivity implements View.OnClickListener, FilmDetailContract.View {


    private KenBurnsView iv_film;
    private TextView tv_rating;
    private TextView tv_date_and_film_time;
    private TextView tv_film_country;
    private TextView tv_film_name_real;
    private String movieId;
    private TextView tv_description;
    private RecyclerView mRecyclerView;
    private Button bt_buy;
    private String alt;
    public static final String MOVIEID = "movieId";
    public static final String MOVIENAME = "movieName";
    private FilmDetailContract.Presenter mPresenter;
    private List<FilmPeople> list = new ArrayList<>();
    private LoadingStatusLayout loadingStatusLayout;
    private ImageView iv_background;
    private SimpleRatingBar ratingBar;
    private LinearLayout ll_film_type;

    @Override
    public void onBackPressed() {
        iv_film.pause();
        super.onBackPressed();
    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new FilmDetailPresenter(new FilmDetailRepository(), this);
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getStringExtra(MOVIEID);
            String movieName = intent.getStringExtra(MOVIENAME);
            setActionTitle(movieName);
        }

        loadingStatusLayout = getView(R.id.loadingStatusLayout);
        //电影图片
        iv_film = getView(R.id.iv_film);
        iv_film.setOnClickListener(this);
        //电影评分
        tv_rating = getView(R.id.tv_rating);
        //电影出品时间
        tv_date_and_film_time = getView(R.id.tv_date_and_film_time);
        //电影出品国家
        tv_film_country = getView(R.id.tv_film_country);
        //电影原名
        tv_film_name_real = getView(R.id.tv_film_name_real);
        //电影描述
        tv_description = getView(R.id.tv_description);
        iv_background = getView(R.id.iv_background);
        //RatingBar
        ratingBar = getView(R.id.ratingbar);
        //电影类型
        ll_film_type = getView(R.id.ll_film_type);
        //演员
        mRecyclerView = getView(R.id.rv);
        bt_buy = getView(R.id.bt_buy);
        bt_buy.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(40, 0, 0, 0));
    }

    @Override
    protected void initData() {
        mPresenter.getFilmDetail(movieId);
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    public void setFilmDetailInfo(FilmDetail filmDetail) {
        loadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
        if (filmDetail.getImages() != null && filmDetail.getImages().getLarge() != null) {
            Glide.with(FilmDetailActivity.this).load(filmDetail.getImages().getLarge()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv_film);
//            Glide.with(FilmDetailActivity.this).load(filmDetail.getImages().getLarge()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv_background);
            Glide.with(FilmDetailActivity.this)
                    .load(filmDetail.getImages().getLarge())
                    .transform(new BlurTransformation(this, 10))
                    .into(iv_background);
            iv_background.setAlpha(0.6f);
        }
        if (!TextUtils.isEmpty(filmDetail.getTitle())) {
        }
        if (filmDetail.getRating() != null) {
            ratingBar.setVisibility(View.VISIBLE);
            tv_rating.setText("评分" + filmDetail.getRating().getAverage());
            ratingBar.setRating(filmDetail.getRating().getAverage() / 2);
        }
        tv_date_and_film_time.setText(filmDetail.getYear() + "年  出品");
        if (filmDetail.getCountries() != null && filmDetail.getCountries().size() > 0) {
            tv_film_country.setText(filmDetail.getCountries().get(0));
        }
        if (filmDetail.getGenres() != null && filmDetail.getGenres().size() > 0) {
            for (int i = 0; i < filmDetail.getGenres().size(); i++) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i != 0) {
                    layoutParams.leftMargin = 8;
                }
                TextView textView = new TextView(this);
                textView.setText(filmDetail.getGenres().get(i));
                textView.setTextSize(10);
                textView.setTextColor(Color.WHITE);
                textView.setPadding(5, 3, 5, 3);
                textView.setBackgroundResource(R.drawable.film_category_text);
                textView.setLayoutParams(layoutParams);
                ll_film_type.addView(textView);
            }
        }
        tv_description.setText(filmDetail.getSummary());
        tv_film_name_real.setText(filmDetail.getOriginal_title() + " [原名]");
        //电影链接
        alt = filmDetail.getAlt();
        initActor(filmDetail);
    }

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
        mRecyclerView.setAdapter(actorAdapter);

    }

    @Override
    public void setPresenter(FilmDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_buy:
                Toast.makeText(FilmDetailActivity.this, "wait...", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_film:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(WebActivity.WEBURL, alt);
                startActivity(intent);
                break;

        }
    }
}
