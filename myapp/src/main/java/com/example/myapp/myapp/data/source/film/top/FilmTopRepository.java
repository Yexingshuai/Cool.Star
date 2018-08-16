package com.example.myapp.myapp.data.source.film.top;

import com.example.myapp.myapp.data.api.DoubanApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/15.
 */

public class FilmTopRepository implements FilmTop100Source {
    @Override
    public void getTop100Film(HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        httpContext.execute(doubanApi.getTop100(0,100),response);
    }
}
