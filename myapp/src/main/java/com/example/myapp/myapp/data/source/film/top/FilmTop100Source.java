package com.example.myapp.myapp.data.source.film.top;

import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/15.
 */

public interface FilmTop100Source {

    void getTop100Film(HttpContext.Response response);
}
