package com.example.myapp.myapp.data.source.film.detail;

import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/16.
 */

public interface FilmDetailSource {

    void getFilmDetail(String movieId, HttpContext.Response response);
}
