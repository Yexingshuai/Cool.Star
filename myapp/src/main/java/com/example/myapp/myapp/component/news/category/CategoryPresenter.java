package com.example.myapp.myapp.component.news.category;

import android.view.LayoutInflater;

import com.example.myapp.myapp.room.Injection;
import com.example.myapp.myapp.room.RoomServer;
import com.example.myapp.myapp.room.news.LocalCategoryDataSource;
import com.example.myapp.myapp.room.news.entity.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by yexing on 2018/11/8.
 */

public class CategoryPresenter implements CategoryContract.Presenter {
    private CategoryContract.View mView;
    private RoomServer roomServer;
    private LocalCategoryDataSource categoryDataSource;

    @Override
    public void start() {
        roomServer = new RoomServer();
        categoryDataSource = Injection.provideLocalCategoryDataSource(((CategoryActivity) mView));
    }

    @Override
    public void stop() {
        roomServer.dispose();
    }

    public CategoryPresenter(CategoryContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void saveData2Database(List<Category> mActiveList, List<Category> mInActiveList) {
        final List list1 = new ArrayList();

        Category category = new Category("里日天", 1, 1, true);
        list1.add(category);

        Flowable<List<Long>> flowable = Flowable.create(new FlowableOnSubscribe<List<Long>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Long>> emitter) throws Exception {
                List<Long> longs = categoryDataSource.insertAll(list1);
                emitter.onNext(longs);
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        roomServer.execute(flowable, new RoomServer.Response<List<Long>>() {
            @Override
            public void success(List<Long> response) {

            }
        });

    }

    @Override
    public void updateCatogryDatabase(final List<Category> mActiveList, List<Category> mInActiveList) {
        final ArrayList<Category> list = new ArrayList<>();
        for (int i = 0; i < mActiveList.size(); i++) {
            Category category = mActiveList.get(i);
            category.setOrderId(i + 1);
        }
        list.addAll(mActiveList);
        list.addAll(mInActiveList);
        roomServer.execute(roomServer.makeFlowable(new RoomServer.OnSubscribeListener<Integer>() {

            @Override
            public Integer onSubscribe() {
                return categoryDataSource.upDateAll(list);
            }
        }), new RoomServer.Response<Integer>() {

            @Override
            public void success(Integer integer) {

            }
        });

    }

    @Override
    public void queryAll() {
        roomServer.execute(categoryDataSource.getAll(), new RoomServer.Response<List<Category>>() {
            @Override
            public void success(List<Category> response) {
                if (response.size() > 0) {
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
        });
    }

}
