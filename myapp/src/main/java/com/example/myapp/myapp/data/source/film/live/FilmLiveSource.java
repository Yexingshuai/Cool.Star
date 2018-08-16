package com.example.myapp.myapp.data.source.film.live;

import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/15.
 */

public interface FilmLiveSource {

    void getLiveFilm(HttpContext.Response response);
}
