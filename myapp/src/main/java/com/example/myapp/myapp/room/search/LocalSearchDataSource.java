package com.example.myapp.myapp.room.search;

import com.example.myapp.myapp.room.search.dao.SearchHistoryDao;
import com.example.myapp.myapp.room.search.entity.SearchHistory;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yexing on 2018/10/26.
 */

public class LocalSearchDataSource implements SearchDataSource {

    private SearchHistoryDao mSearchDao;

    public LocalSearchDataSource(SearchHistoryDao searchDao) {
        mSearchDao = searchDao;
    }

    @Override
    public Flowable<List<SearchHistory>> getAll() {
        return mSearchDao.getAll();
    }

    @Override
    public Flowable<Long> insertOne(SearchHistory searchHistory) {
        return mSearchDao.insert(searchHistory);
    }
}
