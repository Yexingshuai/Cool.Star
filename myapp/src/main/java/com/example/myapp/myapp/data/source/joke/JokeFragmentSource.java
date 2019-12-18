package com.example.myapp.myapp.data.source.joke;

import com.example.myapp.myapp.data.http.HttpContext;

public interface JokeFragmentSource {


    void getJokeList(int page, String type, HttpContext.Response response);

}
