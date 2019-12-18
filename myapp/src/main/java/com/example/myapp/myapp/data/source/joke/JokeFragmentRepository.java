package com.example.myapp.myapp.data.source.joke;

import com.example.myapp.myapp.data.api.OpenApi;
import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.http.HttpContext;

public class JokeFragmentRepository implements JokeFragmentSource {

    @Override
    public void getJokeList(int page, String type, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        OpenApi api = httpContext.createApi5(OpenApi.class);
        httpContext.execute(api.getJokeInfo(page, 20, type), response);
    }
}
