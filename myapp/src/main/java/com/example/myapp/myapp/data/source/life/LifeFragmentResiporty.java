package com.example.myapp.myapp.data.source.life;

import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.data.api.JokeApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/28.
 */

public class LifeFragmentResiporty implements LifeFragmentSource {
    @Override
    public void requestJokeInfo(int pageNum, int pageSize, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        JokeApi jokeApi = httpContext.createApi3(JokeApi.class);
        httpContext.execute(jokeApi.getJokeLive(pageNum, pageSize, AppFlag.JOKE), response);
    }
}
