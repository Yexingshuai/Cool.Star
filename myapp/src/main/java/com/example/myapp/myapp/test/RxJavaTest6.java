package com.example.myapp.myapp.test;

/**
 * Created by daixiankade on 2018/7/4.
 */


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;


/**
 * 之前的上游是Observable 和Observer.   这一次的上游是Flowable, 下游变成了Subscriber
 * <p>
 * Flowable采用的一种新思路：响应式拉取，
 */
public class RxJavaTest6 {

    public static String TAG = "SSSSSSSSSSSSS";

    public static void test() {

        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");                 //即使缺少下游的request, 上游依然会发送自己的事件.Flowable里有一个默认的大小128的水缸。当上下游工作在不同线程时，
                emitter.onNext(2);                     //上游会把事件发送到这个水缸中，下游即使不调用request，上游在水缸中还是会保存着这些事件，下游调用request时候，才从水缸里取出事件发给下游
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();


                for (int i = 0; i < 128; i++) {
                    Log.d(TAG, "emit " + i);               //如果在这里发送128个事件，下游不做处理，代码运行无误，发出128事件，下游未处理，但是129则会抛出异常，
                    emitter.onNext(i);                            //即使发出了129个事件，如果下游能够快速消费一个，就不会内存溢出了.
                }

            }
        }, BackpressureStrategy.ERROR); //增加了一个参数     上下流游速不均衡的话就抛出一个异常  代表一种策略

        //BackpressureStrategy.BUFFER  换一个大水缸，可以发送大于128的事件
        //BackpressureStrategy.DROP     把存不下的事件丢弃,
        //BackpressureStrategy.LATEST    只保留最新的事件


        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");

                s.cancel();   //用来切断水管
                s.request(Long.MAX_VALUE);  //注意这句代码 表示的是一种能力,下游处理事件的能力   这样的话不会导致事件丢失，也不会减速 上游发送事件速度
                //如果没有调用这个request, 上游会认为下游没有处理事件的能力，而且上游不能一直等待，因此抛出异常.
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream.subscribe(downstream);


    }

}
