package com.example.myapp.myapp.room;

import com.example.myapp.myapp.utils.ToastUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yexing on 2018/10/26.
 */

public class RoomServer {


    private Disposable mDisposable;

    public <T> void execute(Flowable<T> flowable, final Response<T> response) {
        mDisposable = flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        response.success(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        response.error(throwable.getMessage());
                    }
                });

    }

    public void dispose() {
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }


    public static abstract class Response<T> {

        public abstract void success(T response);

        public void error(String error) {
            ToastUtil.showApp(error);
        }
    }

}
