package com.example.myapp.myapp.test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;


/**
 * Created by yexing on 2018/7/2.
 * <p>
 * RxJava 上游和下游默认是在同一个线程工作
 */


public class RxJavaTest2 {

    public static String TAG = "SSSSSSSSSSSSS";

    public static void test() {
        Observable<String> mObservable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {


            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        };

//        mObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);

        //subscribeOn指定的是上游发送事件的线程，observerOn 指定的是下游接收事件的线程
        //多次指定上游线程，只有第一次指定的有效

        //多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.

    }
}
