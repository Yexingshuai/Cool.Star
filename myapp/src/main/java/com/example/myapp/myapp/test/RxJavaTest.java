package com.example.myapp.myapp.test;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by yexing on 2018/6/30.
 */

public class RxJavaTest {
    public static String TAG = "SSSSSSSSSSSSS";

    public static void test() {
        /**
         * 调用onComplete之后，下游不在接受事件
         */
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);

                emitter.onNext(3);
                emitter.onComplete();  //也可以不发送
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {

            Disposable mDisposable;

            //这个Disposable 译为  一次性用品， 当调用它的dispose方法，会将两根管道切断，下游不在接受事件,但是上游仍会发送事件
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                mDisposable.dispose();
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {

                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribe(observer);

    }


    public static void test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Consumer<Integer>() {  //Consumer:消费者    表示只需要onNext事件
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "onNext: " + integer);
            }


        });


    }

}
