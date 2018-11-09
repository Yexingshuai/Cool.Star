package com.example.myapp.myapp.component.news.helper;

import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.room.news.entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/11/5.
 */

public class NewsHelper {


    private List<Category> databaseList;

    private NewsHelper() {

    }

    public static NewsHelper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final NewsHelper INSTANCE = new NewsHelper();
    }


    public void setDatabaseListCategoryList(List<Category> categoryList) {
        this.databaseList = categoryList;
    }

    public List getDatabaseCategoryList() {
        return databaseList;
    }


    public static void release() {

    }


}
