package com.example.myapp.myapp.data.source.study;

import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.room.RoomServer;
import com.example.myapp.myapp.room.search.SearchDataSource;
import com.example.myapp.myapp.room.search.entity.SearchHistory;

import retrofit2.http.HTTP;

/**
 * Created by yexing on 2018/7/16.
 */

public interface StudyFragmentSource {

    void requestBannerAndStutyInfo(int index, HttpContext.Response response);

    void requestStudyInfo(int index, HttpContext.Response response);

    void collect(int id, HttpContext.Response response);

    void unCollect(int id, HttpContext.Response response);

    void searchKeyWord(String message, HttpContext.Response response);

    void insertOne(RoomServer roomServer,SearchDataSource searchDataSource, SearchHistory searchHistory, RoomServer.Response response);

    void queryAll(RoomServer roomServer,SearchDataSource searchDataSource, RoomServer.Response response);

    void deleteAll(RoomServer roomServer,SearchDataSource searchDataSource,RoomServer.Response response);
}
