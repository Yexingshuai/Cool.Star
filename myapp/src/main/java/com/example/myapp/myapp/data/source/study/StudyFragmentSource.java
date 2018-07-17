package com.example.myapp.myapp.data.source.study;

import com.example.myapp.myapp.data.http.HttpContext;

import retrofit2.http.HTTP;

/**
 * Created by yexing on 2018/7/16.
 */

public interface StudyFragmentSource {

    void requestBannerAndStutyInfo(int index, HttpContext.Response response);

    void requestStudyInfo(int index, HttpContext.Response response);
}
