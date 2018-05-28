package http;

import bean.BannerBean;
import bean.HomeItemBean;

import retrofit2.http.GET;
import retrofit2.http.Path;

import rx.Observable;

/**
 * Created by daixiankade on 2018/3/29.
 */

public interface HttpUtil {

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
}
