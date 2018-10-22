package com.example.myapp.myapp.data.api;

import com.example.myapp.myapp.data.bean.FortuneResponse;
import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.data.bean.NewsDetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yexing on 2018/10/11.
 */

public interface MobApi {

    @GET("wx/article/category/query")
    Observable<NewsCategoryResponse> getNewsCateGory(@Query("key") String key);

    /**
     * 根据type获取新闻列表
     */
    @GET("wx/article/search")
    Observable<NewsDetailResponse> getNewsList(@Query("key") String key, @Query("cid") int cid, @Query("page") int page, @Query("size") int size);


    /**
     * 根据手机号码获取运势
     *
     * @param key
     * @param mobile
     * @return
     */
    @GET("/appstore/lucky/mobile/query")
    Observable<FortuneResponse> getFortune(@Query("key") String key, @Query("mobile") String mobile);
}
