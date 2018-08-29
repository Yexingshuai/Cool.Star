package com.example.myapp.myapp.data.api;

import com.example.myapp.myapp.data.bean.JokeResponse;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yexing on 2018/8/28.
 */

public interface JokeApi {


    /**
     * joke
     *
     * @param page
     * @param pageSize
     * @param sort
     * @return
     */
    @GET("joke/content/list.php")
    Observable<JokeResponse> getJoke(@Query("page") int page, @Query("pageSize") int pageSize, @Query("sort") String sort, @Query("time") String timeStamp, @Query("key") String key);


    /**
     * joke
     *
     * @param page
     * @param pageSize
     * @returntext
     */
    @GET("joke/content/text.php")
    Observable<JokeResponse> getJokeLive(@Query("page") int page, @Query("pageSize") int pageSize, @Query("key") String key);
}
