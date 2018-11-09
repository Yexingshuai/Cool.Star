package com.example.myapp.myapp.room.news;

import com.example.myapp.myapp.room.news.dao.CategoryDao;
import com.example.myapp.myapp.room.news.entity.Category;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yexing on 2018/11/7.
 */

public class LocalCategoryDataSource implements CategoryDataSource {

    private CategoryDao mCategoryDao;

    public LocalCategoryDataSource(CategoryDao categoryDao) {
        mCategoryDao = categoryDao;
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public Flowable<List<Category>> getAll() {
        return mCategoryDao.getAll();
    }

    /**
     * 插入全部
     *
     * @param list
     * @return
     */
    @Override
    public List<Long> insertAll(List<Category> list) {
        return mCategoryDao.insert(list);
    }


    /**
     * 更改全部
     *
     * @param list
     * @return
     */
    @Override
    public int upDateAll(List<Category> list) {
        return mCategoryDao.upDate(list);
    }

    @Override
    public int delete() {
        return mCategoryDao.deleteAll();
    }
}
