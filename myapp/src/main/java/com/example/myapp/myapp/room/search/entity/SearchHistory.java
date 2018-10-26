package com.example.myapp.myapp.room.search.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by yexing on 2018/10/24.
 */

@Entity(tableName = "search_history")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "message")
    private String message;

    /**
     * 必须指定一个构造方法，  如果有其他构造方法，必须添加Ignore注解
     */
    public SearchHistory() {

    }

    @Ignore
    public SearchHistory(String message) {
        this.message = message;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int id) {
        this.uid = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
