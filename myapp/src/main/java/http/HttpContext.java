package http;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.LogUtil;

/**
 * Created by daixiankade on 2018/4/13.
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
                        LogUtil.e(TAG_LOG, "Completed:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG_LOG, "Error:\n" + e.toString());
                        response.fail(e.getMessage());
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
        public abstract void fail(String error);
    }
}
