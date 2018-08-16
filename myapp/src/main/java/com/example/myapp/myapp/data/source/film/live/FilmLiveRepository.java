package com.example.myapp.myapp.data.source.film.live;

import com.example.myapp.myapp.data.api.DoubanApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/15.
 */

public class FilmLiveRepository implements FilmLiveSource {
    @Override
    public void getLiveFilm(HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        httpContext.execute(doubanApi.getLiveFilm(), response);

    }
}
