package com.example.myapp.myapp.data.api;



import com.example.myapp.myapp.data.bean.FilmDetail;
import com.example.myapp.myapp.data.bean.FilmLive;
import com.example.myapp.myapp.data.bean.Root;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
public interface DoubanApi {
    /**
     * 热映中
     * @return
     */
    @GET("v2/movie/in_theaters")
    Observable<FilmLive> getLiveFilm();

    /**
     * 北美榜单
     * @return
     */

//    @GET("v2/movie/us_box")
//    Observable<FilmUsBox> getUsBox();

    /**
     * 获取top250
     * @param start
     * @param count
     * @return
     */

    @GET("v2/movie/top250")
    Observable<Root> getTop100(@Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情
     * @param id
     * @return
     */

    @GET("v2/movie/subject/{id}")
    Observable<FilmDetail> getFilmDetail(@Path("id") String id);


    /**
     * 根据tag获取图书
     * @param tag
     * @return
     */

//    @GET("v2/book/search")
//    Observable<BookRoot> searchBookByTag(@Query("tag") String tag);

//    @GET("v2/book/{id}")
//    Observable<Books> getBookDetail(@Path("id") String id);

    /**
     * 根据tag获取music｀
     * @param tag
     * @return
     */

//    @GET("v2/music/search")
//    Observable<MusicRoot> searchMusicByTag(@Query("tag") String tag);

//    @GET("v2/music/{id}")
//    Observable<Musics> getMusicDetail(@Path("id") String id);
}
