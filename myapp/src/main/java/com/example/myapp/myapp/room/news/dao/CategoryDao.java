package com.example.myapp.myapp.room.news.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.myapp.myapp.room.news.entity.Category;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yexing on 2018/11/7.
 */

@Dao
public interface CategoryDao {

    //-----query------
    @Query("SELECT * FROM category")
    Flowable<List<Category>> getAll();

    //-----insert-----

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<Category> categories);

    //------update-----

    //同上,返回更新条目数量
    @Update()
    int upDate(List<Category> categories);

    //-----Delete-------

    /**
     * Delete all
     */
    @Query("DELETE  FROM category")
    int deleteAll();
}
