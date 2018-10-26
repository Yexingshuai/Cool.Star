package com.example.myapp.myapp.room.search.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.myapp.myapp.room.search.entity.SearchHistory;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yexing on 2018/10/24.
 */

@Dao
public interface SearchHistoryDao {

    //-------------query----------------------
    @Query("SELECT * FROM search_history")
    //查询表中的所有column字段
    Flowable<List<SearchHistory>> getAll();

    //根据条件查询  方法参数和注解参数一一对应
    @Query("SELECT * FROM search_history WHERE uid IN (:ids)")
    List<SearchHistory> loadAllByIds(int... ids);

    //根据一个uid  查找对象
    @Query("SELECT * FROM SEARCH_HISTORY WHERE uid = :uid")
    SearchHistory FindByUid(int uid);

    //--------------insert----------------------

    //数据判断根据主键匹配，也就是uid
    //OnConflictStrategy.REPLACE  ,表示已有数据，那么就覆盖掉
    //Long 返回的是，插入条目的主键值
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Flowable<Long> insert(SearchHistory searchHistoty);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(SearchHistory... searchHistoties);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<SearchHistory> searchHistoties);


    //---------------update----------------------
    //更新已有数据，根据主键（uid）匹配，也就是uid
    //int 返回代表 更新条目数量
    @Update()
    int upDate(SearchHistory searchHistoty);

    //同上
    @Update()
    int upDate(SearchHistory... searchHistoties);

    //同上
    @Update()
    int upDate(List<SearchHistory> searchHistories);


    //--------------Delete----------------------------
    //删除数据，数据匹配通过uid
    //int 返回代表  删除条目个数
    @Delete()
    int delete(SearchHistory searchHistoty);

    //同上
    @Delete()
    int delete(SearchHistory... searchHistoties);

    //同上
    @Delete()
    int delete(List<SearchHistory> searchHistotyList);

}
