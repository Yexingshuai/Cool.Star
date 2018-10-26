package com.example.myapp.myapp.room.search;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yexing on 2018/10/26.
 */

public class SearchDataRoomServer {


    private Disposable mDisposable;

    public static SearchDataRoomServer getInstance() {
        return Holder.SERVER;
    }


    public static final class Holder {
        private static final SearchDataRoomServer SERVER = new SearchDataRoomServer();
    }


    public <T> void queryAll(Flowable<T> flowable, final Response<T> response) {
        mDisposable = flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        response.error(throwable.getMessage());
                    }
                });
    }


    public static abstract class Response<T> {

        public abstract void success(T response);

        public void error(String error) {

        }
    }

}
