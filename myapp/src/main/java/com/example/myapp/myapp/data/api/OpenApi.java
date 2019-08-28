package com.example.myapp.myapp.data.api;

import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.data.bean.HomeItemBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenApi {


    /**
     * 获取段子信息
     */
    @GET("satinGodApi")
    Observable<JokeBean> getJokeInfo(@Query("type") int type, @Query("page") int page);
}
