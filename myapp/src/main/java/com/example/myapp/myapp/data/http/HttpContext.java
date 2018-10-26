package com.example.myapp.myapp.data.http;

import com.example.myapp.myapp.di.retrofit.RetrofitServer;
import com.example.myapp.myapp.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yexing on 2018/4/13.
 */

public class HttpContext {
    private static final String TAG_LOG = "HttpServer";

    private Subscription mSubscription;

    /**
     * 创建网络所需的API
     *
     * @param service api
     * @param <T>     t
     * @return t
     */
    public <T> T createApi(Class<T> service) {
        return RetrofitServer.getRetrofit().create(service);
    }

    public <T> T createApi2(Class<T> service) {
        return RetrofitServer.getRetrofit2().create(service);
    }

    public <T> T createApi3(Class<T> service) {
        return RetrofitServer.getRetrofit3().create(service);
    }

    public <T> T createApi4(Class<T> service) {
        return RetrofitServer.getRetrofit4().create(service);
    }


    /**
     * 执行网络操作
     *
     * @param <T>        T
     * @param observable {@link Observable}
     * @param response   {@link Response}
     */
    public <T> void execute(Observable<T> observable, final Response<T> response) {

        mSubscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        response.stop();
                        LogUtil.e(TAG_LOG, "Completed:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG_LOG, "Error:\n" + e.toString());
                        response.error(e.getMessage());
                        //主动调用completed
                        onCompleted();
                    }

                    @Override
                    public void onNext(T t) {
                        LogUtil.i(TAG_LOG, "ResponseData：" + t.toString());
                        response.success(t);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        LogUtil.e(TAG_LOG, "Start:");
                        //判断是否有网络
//                        if (!NetWorkHelper.getInstance().isNetworkConnected()) {
//                            //无网络
//                            LogUtil.e(TAG_LOG, "请检查网络是否连接");
//                            //取消订阅
//                            unsubscribe();
//                            //调用
//                            response.error("无网络，请检查后刷新");
//                        } else {
//

                        response.start();
//                        }
                    }
                });
    }

    /**
     * 取消网络请求任务
     */
    public void cancel() {
        if (mSubscription != null) {
            if (!mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }
        }
    }

    /**
     * Http 网络响应
     */
    public static abstract class Response<T> {
        public abstract void success(T result);


        /**
         * 网络开始处理
         *
         * @see #stop()
         */
        public void start() {

        }

        /**
         * 网络结束处理(success or error) {@link #success(Object)}{@link #error(String)}}
         *
         * @see #start()
         */
        public void stop() {
        }

        /**
         * 网络响应失败
         *
         * @see #success(Object)
         */
        public void error(String error) {
        }

    }
}
