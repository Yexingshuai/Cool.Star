package com.example.myapp.myapp.component.news;

import android.util.Log;

import com.example.myapp.myapp.component.news.helper.NewsHelper;
import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.room.Injection;
import com.example.myapp.myapp.room.RoomServer;
import com.example.myapp.myapp.room.news.LocalCategoryDataSource;
import com.example.myapp.myapp.room.news.entity.Category;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by yexing on 2018/10/10.
 */

public class NewsFragmentPresenter implements NewsContract.Presenter {


    private NewsContract.View mView;
    private NewsContract.NewsFragmentSource mSource;

    private RoomServer roomServer;
    private LocalCategoryDataSource categoryDataSource;


    public NewsFragmentPresenter(NewsContract.View view, NewsContract.NewsFragmentSource source) {

        mView = view;
        mSource = source;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        categoryDataSource = Injection.provideLocalCategoryDataSource(((NewsFragment) mView).getActivity());
        roomServer = new RoomServer();
    }

    @Override
    public void stop() {
        roomServer.dispose();
    }

    /**
     * 从网络获取分类信息
     */
    @Override
    public void requestCategory() {
        mSource.requestCategory(new HttpContext.Response<NewsCategoryResponse>() {
            @Override
            public void success(NewsCategoryResponse result) {
                if (result.getRetCode() == 200) {
                    mView.requestSuccess(result);
                } else {
                    mView.requestFail(result.getMsg());
                }
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.requestFail(error);
            }
        });
    }

    /**
     * 查询数据库
     */
    @Override
    public void queryDatabase() {
        Flowable<List<Category>> flowable = categoryDataSource.getAll();
        roomServer.execute(flowable, new RoomServer.Response<List<Category>>() {
            @Override
            public void success(List<Category> response) {
                //去除未添加的
                if (response.size() > 0) {
                    NewsHelper.getInstance().setDatabaseListCategoryList(response);
                    Iterator<Category> iterator = response.iterator();
                    while (iterator.hasNext()) {
                        Category category = iterator.next();
                        if (!category.isSave()) {
                            iterator.remove();
                        }
                    }
                    Collections.sort(response, new Comparator<Category>() {
                        @Override
                        public int compare(Category c1, Category c2) {
                            if (c1.getOrderId() > c2.getOrderId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    });
                    mView.setDatabaseData(response);
                }

            }

            @Override
            public void error(String error) {
                super.error(error);
            }
        });
    }

    /**
     * 删除数据库信息
     */
    @Override
    public void deleteDatabase() {

        roomServer.execute(roomServer.makeFlowable(new RoomServer.OnSubscribeListener<Integer>() {
            @Override
            public Integer onSubscribe() {
                return categoryDataSource.delete();
            }
        }), new RoomServer.Response<Integer>() {
            @Override
            public void success(Integer response) {

            }

            @Override
            public void error(String error) {
                super.error(error);
            }
        });
    }

    /**
     * 存储到数据库
     */
    @Override
    public void saveToDatabase(List<NewsCategoryResponse.ResultBean> categoryList) {
        final List<Category> saveList = new ArrayList();
        int size = categoryList.size();
        for (int i = 0; i < size; i++) {
            NewsCategoryResponse.ResultBean resultBean = categoryList.get(i);
            Category category = new Category(resultBean.getName(), i + 1, resultBean.getCid(), true);
            saveList.add(category);
        }
        NewsHelper.getInstance().setDatabaseListCategoryList(saveList);
        roomServer.execute(roomServer.makeFlowable(new RoomServer.OnSubscribeListener<List<Long>>() {
                    @Override
                    public List<Long> onSubscribe() {
                        return categoryDataSource.insertAll(saveList);
                    }
                }), new RoomServer.Response<List<Long>>() {
                    @Override
                    public void success(List<Long> response) {

                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                }
        );

    }
}
