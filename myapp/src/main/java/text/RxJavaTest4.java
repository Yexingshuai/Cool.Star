package text;

import android.util.Log;

import com.example.myapp.ui.TestAboutActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Created by daixiankade on 2018/7/4.
 */


//zip 操作符。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
public class RxJavaTest4 {

    public static String TAG = "SSSSSSSSSSSSS";


    /**
     * 运行查看log，会发现observable1事件发送完之后，obsrvable2才会发送事件，因为他们默认在同一个线程
     */


    /**
     * zip操作符 将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件， 他只发射 与发射数据项最少的那个observable 一样多的数据， 因为是要两两结合。
     */

    public static void test() {
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");

            }
        });


        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
