package com.example.myapp.myapp.data.source.news;

import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.component.news.NewsContract;
import com.example.myapp.myapp.data.api.MobApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/10/11.
 */

public class NewsFragmentRepository implements NewsContract.NewsFragmentSource {
    @Override
    public void requestCategory(HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        MobApi api = httpContext.createApi4(MobApi.class);
        httpContext.execute(api.getNewsCateGory(AppFlag.MOB_KEY), response);
    }


}
