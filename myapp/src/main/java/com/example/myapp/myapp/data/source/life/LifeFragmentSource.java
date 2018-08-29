package com.example.myapp.myapp.data.source.life;

import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/28.
 */

public interface LifeFragmentSource {

    void requestJokeInfo(int pageNum, int pageSize, HttpContext.Response response);
}
