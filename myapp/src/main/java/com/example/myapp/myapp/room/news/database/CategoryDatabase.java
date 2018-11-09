package com.example.myapp.myapp.room.news.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.myapp.myapp.room.news.dao.CategoryDao;
import com.example.myapp.myapp.room.news.entity.Category;

/**
 * Created by yexing on 2018/11/7.
 */

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryDatabase extends RoomDatabase {

    public static CategoryDatabase categoryDatabase;

    public abstract CategoryDao categoryDao();


    public static CategoryDatabase getInstance(Context context) {

        if (categoryDatabase == null) {
            synchronized (CategoryDatabase.class) {
                if (categoryDatabase == null) {
                    categoryDatabase = Room.databaseBuilder(context.getApplicationContext(), CategoryDatabase.class, "category.db")
                            .build();
                }
            }
        }
        return categoryDatabase;
    }
}
