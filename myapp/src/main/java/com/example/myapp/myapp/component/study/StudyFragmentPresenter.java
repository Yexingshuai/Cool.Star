package com.example.myapp.myapp.component.study;

import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.data.bean.KeyWordResponse;
import com.example.myapp.myapp.data.bean.WanAndroidBaseReponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.study.StudyFragmentSource;
import com.example.myapp.myapp.room.Injection;
import com.example.myapp.myapp.room.search.SearchDataRoomServer;
import com.example.myapp.myapp.room.search.SearchDataSource;
import com.example.myapp.myapp.room.search.entity.SearchHistory;
import com.example.myapp.myapp.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by yexing on 2018/7/16.
 */

public class StudyFragmentPresenter implements StudyFragmentContract.Presenter {


    private StudyFragmentSource mSource;
    private StudyFragmentContract.View mView;

    public StudyFragmentPresenter(StudyFragmentSource source, StudyFragmentContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void requestBannerAndStutyInfo(int index) {
        mSource.requestBannerAndStutyInfo(index, new HttpContext.Response() {
            @Override
            public void success(Object result) {

                mView.setBannerAndStudyInfo(result);

            }

            @Override
            public void error(String error) {
                mView.requestBannerAndStudyInfoFail(error);
            }

            @Override
            public void stop() {
                super.stop();
                mView.hideLoading();

            }
        });
    }

    @Override
    public void requestStudyInfo(int index) {
        mSource.requestStudyInfo(index, new HttpContext.Response<HomeItemBean>() {
            @Override
            public void success(HomeItemBean result) {
                mView.setStudyInfo(result);
            }

            @Override
            public void error(String error) {

            }


        });
    }

    @Override
    public void collectArtist(int id) {
//        mSource.collect(id, new HttpContext.Response<WanAndroidBaseReponse>() {
//            @Override
//            public void success(WanAndroidBaseReponse result) {
//                int errorCode = result.getErrorCode();
//                if (errorCode == 0) {
//                    mView.collectSuccess();
//                } else {
//                    mView.collectFail("");
//                }
//            }
//
//            @Override
//            public void error(String error) {
//                super.error(error);
//                mView.collectFail(error);
//            }
//        });
        ArrayList<String> userCookieInfo = LoginContext.getInstance().getUserCookieInfo();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Cookie", userCookieInfo.get(0) != null ? userCookieInfo.get(0) : "");
        httpHeaders.put("cookie", userCookieInfo.get(1) != null ? userCookieInfo.get(1) : "");
        OkGo.post("http://www.wanandroid.com/lg/collect/" + id + "/json")
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .headers(httpHeaders)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        WanAndroidBaseReponse reponse = gson.fromJson(s, WanAndroidBaseReponse.class);
                        int errorCode = reponse.getErrorCode();
                        if (errorCode == 0) {
                            mView.collectSuccess();
                        } else {
                            mView.collectFail(reponse.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mView.collectFail(e.getMessage());

                    }
                });

    }

    /**
     * 取消收藏
     *
     * @param id
     */
    @Override
    public void unCollect(int id) {
        ArrayList<String> userCookieInfo = LoginContext.getInstance().getUserCookieInfo();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Cookie", userCookieInfo.get(0) != null ? userCookieInfo.get(0) : "");
        httpHeaders.put("cookie", userCookieInfo.get(1) != null ? userCookieInfo.get(1) : "");
        OkGo.post("http://www.wanandroid.com/lg/uncollect_originId/" + id + "/json")
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .headers(httpHeaders)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        WanAndroidBaseReponse reponse = gson.fromJson(s, WanAndroidBaseReponse.class);
                        int errorCode = reponse.getErrorCode();
                        if (errorCode == 0) {
                            mView.unCollectSuccess();
                        } else {
                            mView.unCollectFail(reponse.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mView.unCollectFail(e.getMessage());
                    }
                });

    }

    @Override
    public void searchKeyWord(final String message) {
        mSource.searchKeyWord(message, new HttpContext.Response<KeyWordResponse>() {
            @Override
            public void success(KeyWordResponse result) {
                mView.setKeyWordInfo(result);
                //保存导数据库
                saveDatabase(message);
            }

            @Override
            public void error(String error) {
                super.error(error);
            }

        });
    }

    /**
     * 记录保存到数据库
     *
     * @param message
     */
    private void saveDatabase(String message) {

        SearchDataSource searchDataSource = Injection.provideLocalSearchDataSource(((StudyFragment) mView).getActivity());
        SearchDataRoomServer.getInstance().queryAll(searchDataSource.getAll(), new SearchDataRoomServer.Response<List<SearchHistory>>() {
            @Override
            public void success(List<SearchHistory> list) {
                ToastUtil.showApp(list.size()+"");
            }

            @Override
            public void error(String error) {
                super.error(error);
            }
        });
    }
}
