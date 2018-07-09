package text;

import android.support.annotation.MainThread;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by daixiankade on 2018/7/4.
 */


// 背压。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。Backpressure
public class RxJavaTest5 {

    public static String TAG = "SSSSSSSSSSSSS";


    //上一章节 说到zip 可以将多个上游发送的事件发送给下游， 可其中一个管子发送事件速度过快，而另一个管子 发送速度过慢，这么多事件存储在哪里呢？
    //zip  给每一根水管 都会弄一个水缸，用来保存这些事件
    //BackPressure 控制流量。


    /**
     * 当上下游工作在同一个线程中时, 这时候是一个同步的订阅关系, 也就是说上游每发送一个事件必须等到下游接收处理完了以后才能接着发送下一个事件.
     *
     * 如果上下游不在同一个线程工作时，上游发送事件速度过快，而下游处理速度过慢， 就会导致内存溢出，程序崩溃。
     */


    //RxJavaTest6


    /**
     * 既然上游发送速度过快， 有两种维度去控制，一种是从速度上处理：减慢事件存储到水缸的速度，一种是从数量上处理：减少放入水缸事件的数量（但是这么做 会丢失事件）。
     */

    public static void test() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .filter(new Predicate<Integer>() {            //对事件进行过滤。只挑取我们需要的
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 10 == 0;
                    }
                })
//                .observeOn(AndroidSchedulers.mainThread())         //------------------------这个地方一定是主线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });

    }


    /**
     * 每隔一段时间，取一个事件放入水缸里...   质量上取胜：
     */
    public static void test2() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)  //sample取样    让他每过两秒钟取一个事件 给下游。。
//                .observeOn(AndroidSchedulers.mainThread())    //这里是主线程，做同步任务
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });

    }


    /**
     * 从速度上治理
     */

    public static void test3() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                    Thread.sleep(2000);  //每次发送完事件延时2秒    减缓发送事件的速度
                }
            }
        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())      //这里是主线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });


    }

    /**
     * 解决之前的问题
     */
    public static void test4() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);

//                    Thread.sleep(2000);  //发送事件之后延时2秒     也可以通过这种方式，减缓上游发送事件的速度
                }
            }
        }).subscribeOn(Schedulers.io()).sample(2, TimeUnit.SECONDS); //进行sample采样

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        })
//                .observeOn(AndroidSchedulers.mainThread())                // 这里是主线程
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.w(TAG, throwable);
                    }
                });


    }
}
