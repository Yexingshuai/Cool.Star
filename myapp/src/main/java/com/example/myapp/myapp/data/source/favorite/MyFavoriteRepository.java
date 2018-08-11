package com.example.myapp.myapp.data.source.favorite;

import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/11.
 */

public class MyFavoriteRepository implements MyFavoriteSource {
    @Override
    public void getFavoriteList(int id, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.favorite(id), response);
    }
}
