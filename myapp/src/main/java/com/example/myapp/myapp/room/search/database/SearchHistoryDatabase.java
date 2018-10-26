package com.example.myapp.myapp.room.search.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.myapp.myapp.room.search.dao.SearchHistoryDao;
import com.example.myapp.myapp.room.search.entity.SearchHistory;


/**
 * Created by yexing on 2018/10/24.
 */

@Database(entities = {SearchHistory.class}, version = 1, exportSchema = false)
public abstract class SearchHistoryDatabase extends RoomDatabase {

    public static SearchHistoryDatabase historyDatabase;

    public abstract SearchHistoryDao searchHistoryDao();

    public static SearchHistoryDatabase getInstance(Context context) {
        if (historyDatabase == null) {

            synchronized (SearchHistoryDatabase.class) {
                if (historyDatabase == null) {
                    historyDatabase = Room.databaseBuilder(context.getApplicationContext(), SearchHistoryDatabase.class, "room-search-database")
                            //加上表示允许在主线程中进行数据库操作，，所以去掉他
//                            .allowMainThreadQueries()
                            //数据库升级或变动 则增加这一行
//                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return historyDatabase;
    }


    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //执行变动语句，在之前的表中加入新的column,名字是 age
            //类型是Integer,不为空，默认是0
            database.execSQL("ALTER TABLE search_history "
                    + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };

}
