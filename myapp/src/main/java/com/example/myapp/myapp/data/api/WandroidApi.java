package com.example.myapp.myapp.data.api;

import java.util.Map;

import com.example.myapp.myapp.data.bean.BannerBean;
import com.example.myapp.myapp.data.bean.HomeItemBean;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by daixiankade on 2018/3/29.
 */

public interface WandroidApi {

    /**
     * 轮播图
     *
     * @return
     */
    @GET(AppUrl.BANNER)
    Observable<BannerBean> getBanner();

    /**
     * 首页
     */
    @GET("article/list/{id}/json")
    Observable<HomeItemBean> getHome(@Path("id") int id);


    //get 请求用QueryMap,  post请求用FieldMap,还要加上注解  @FormUrlEncoded

    //下载操作
    @Streaming
    @GET
    Call<ResponseBody> downLoad(@Url String url, @QueryMap Map<String, String> params);

    /**
     * 上传操作
     */

    @Multipart
    @POST
    Call<String> upLoad(@Url String url, @Part MultipartBody.Part file);
}
