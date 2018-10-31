package com.example.myapp.myapp.room.search;

import com.example.myapp.myapp.room.search.dao.SearchHistoryDao;
import com.example.myapp.myapp.room.search.entity.SearchHistory;

import org.reactivestreams.Subscriber;

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
    public Long insertOne(SearchHistory searchHistory) {
        return mSearchDao.insert(searchHistory);
    }

    /**
     * 删除所有
     */
    @Override
    public int deleteAll() {
        return mSearchDao.deleteAll();
    }

    @Override
    public Flowable<List<SearchHistory>> findByMessage(String message) {
        return mSearchDao.findByMessage(message);
    }
}
