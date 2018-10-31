package com.example.myapp.myapp.data.source.study;

import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.room.RoomServer;
import com.example.myapp.myapp.room.search.SearchDataSource;
import com.example.myapp.myapp.room.search.entity.SearchHistory;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by yexing on 2018/7/16.
 */

public class StudyFragmentRepository implements StudyFragmentSource {
    @Override
    public void requestBannerAndStutyInfo(int index, final HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        /**
         * 改用RxJava合并方式
         */
        httpContext.execute(rx.Observable.merge(api.getBanner(), api.getHome(index)), response);
    }

    @Override
    public void requestStudyInfo(int index, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.getHome(index), response);
    }

    @Override
    public void collect(int id, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.collect(id), response);
    }

    /**
     * 取消收藏
     */
    @Override
    public void unCollect(int id, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.unCollect(id), response);
    }

    @Override
    public void searchKeyWord(String message, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.searchKeyWord(0, message), response);
    }


    /**
     * 插入一条记录到数据库
     *
     * @param searchHistory
     */
    @Override
    public void insertOne(RoomServer roomServer, final SearchDataSource searchDataSource, final SearchHistory searchHistory, final RoomServer.Response response) {
        Flowable<Long> flowable = Flowable.create(new FlowableOnSubscribe<Long>() {
            @Override
            public void subscribe(FlowableEmitter<Long> emitter) throws Exception {
                Long aLong = searchDataSource.insertOne(searchHistory);
                emitter.onNext(aLong);
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        roomServer.execute(flowable, response);
    }

    /**
     * 查询数据库的数量
     *
     * @param searchDataSource
     * @param response
     */
    @Override
    public void queryAll(RoomServer roomServer, SearchDataSource searchDataSource, RoomServer.Response response) {
        roomServer.execute(searchDataSource.getAll(), response);
    }

    @Override
    public void deleteAll(RoomServer roomServer, final SearchDataSource searchDataSource, RoomServer.Response response) {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                int i = searchDataSource.deleteAll();
                emitter.onNext(i);
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        roomServer.execute(flowable, response);
    }


}
