package com.example.myapp.myapp.data.source.film.top;

import com.example.myapp.myapp.data.api.DoubanApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/15.
 */

public class FilmTopRepository implements FilmTop100Source {

    public static final String APIKEY = "0df993c66c0c636e29ecbb5344252a4a";
    @Override
    public void getTop100Film(HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        DoubanApi doubanApi = httpContext.createApi2(DoubanApi.class);
        httpContext.execute(doubanApi.getTop100(0,100,APIKEY),response);
    }
}
