package com.example.myapp.myapp.data.source.film.detail;

import com.example.myapp.myapp.data.api.DoubanApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/16.
 */

public class FilmDetailRepository implements FilmDetailSource {

    public static final String APIKEY = "0df993c66c0c636e29ecbb5344252a4a";

    @Override
    public void getFilmDetail(String movieId, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        httpContext.execute(doubanApi.getFilmDetail(movieId, APIKEY), response);
    }
}
