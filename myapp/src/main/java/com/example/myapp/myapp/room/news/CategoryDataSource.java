package com.example.myapp.myapp.room.news;

import com.example.myapp.myapp.room.news.entity.Category;
import com.example.myapp.myapp.room.search.entity.SearchHistory;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yexing on 2018/11/7.
 */

public interface CategoryDataSource {

    Flowable<List<Category>> getAll();

    List<Long> insertAll(List<Category> list);

    int upDateAll(List<Category> list);

    int delete();
}
