package com.example.myapp.myapp.data.source.study;

import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.bean.BannerBean;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.data.http.HttpContext;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yexing on 2018/7/16.
 */

public class StudyFragmentRepository implements StudyFragmentSource {
    @Override
    public void requestBannerAndStutyInfo(int index, final HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        /**
         * 改用RxJava合并方式
         */

        rx.Observable.merge(api.getBanner(), api.getHome(index))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        response.stop();
                    }

                    @Override
                    public void onError(Throwable e) {
                        response.error(e.getMessage());

                    }

                    @Override
                    public void onNext(Object o) {
                        response.success(o);


                    }

                    @Override
                    public void onStart() {
                        super.onStart();


                    }
                });
    }

    @Override
    public void requestStudyInfo(int index, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.getHome(index), response);
    }

    @Override
    public void collect(int id, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.collect(id), response);
    }
}
