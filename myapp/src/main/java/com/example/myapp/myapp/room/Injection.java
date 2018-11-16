package com.example.myapp.myapp.room;

import android.content.Context;

import com.example.myapp.myapp.room.news.LocalCategoryDataSource;
import com.example.myapp.myapp.room.news.database.CategoryDatabase;
import com.example.myapp.myapp.room.search.LocalSearchDataSource;
import com.example.myapp.myapp.room.search.SearchDataSource;
import com.example.myapp.myapp.room.search.database.SearchHistoryDatabase;

/**
 * Created by yexing on 2018/10/26.
 */
//注射器
public class Injection {


    public static SearchDataSource provideLocalSearchDataSource(Context context) {
        SearchHistoryDatabase historyDatabase = SearchHistoryDatabase.getInstance(context);
        return new LocalSearchDataSource(historyDatabase.searchHistoryDao());
    }

    public static LocalCategoryDataSource provideLocalCategoryDataSource(Context context) {
        CategoryDatabase categoryDatabase = CategoryDatabase.getInstance(context);
        return new LocalCategoryDataSource(categoryDatabase.categoryDao());
    }

}
