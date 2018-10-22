package com.example.myapp.myapp.data.source.news.detail;

import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.component.news.detail.NewsDetailContract;
import com.example.myapp.myapp.data.api.MobApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/10/12.
 */

public class NewsDetailRepository implements NewsDetailContract.NewsDetailSource {
    @Override
    public void requestNewsDetail(int cid, int page, int size, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        MobApi api = httpContext.createApi4(MobApi.class);
        httpContext.execute(api.getNewsList(AppFlag.MOB_KEY, cid, page, size), response);
    }
}
