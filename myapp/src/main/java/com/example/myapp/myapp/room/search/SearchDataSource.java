package com.example.myapp.myapp.room.search;

import com.example.myapp.myapp.room.search.entity.SearchHistory;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yexing on 2018/10/26.
 */

public interface SearchDataSource {

    Flowable<List<SearchHistory>> getAll();

    Flowable<Long> insertOne(SearchHistory searchHistory);


}
