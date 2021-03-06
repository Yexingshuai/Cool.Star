package com.example.myapp.myapp.di.retrofit;

import android.util.Log;

import com.example.myapp.myapp.data.api.AppUrl;
import com.example.myapp.myapp.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yexing on 2018/4/13.
 */

public class RetrofitServer {

    private static final String TAG_LOG = "RetrofitServer";
    private static final Charset UTF8 = Charset.forName("UTF-8");


    public static final Retrofit getRetrofit() {

        OkHttpClient okHttpClient = getOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static final Retrofit getRetrofit2() {

        OkHttpClient okHttpClient = getOkHttpClient2();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASEURL2)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static final Retrofit getRetrofit3() {

        OkHttpClient okHttpClient = getOkHttpClient2();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASEURL3)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static final Retrofit getRetrofit4() {

        OkHttpClient okHttpClient = getOkHttpClient2();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASEURL4)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static final Retrofit getRetrofit5() {

        OkHttpClient okHttpClient = getOkHttpClient2();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASEURL5)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }


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


    /**
     * 获取OkHttpClient
     *
     * @return {@link OkHttpClient}
     */
    private static OkHttpClient getOkHttpClient() {
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.mContext));

        OkHttpClient.Builder builder = new OkHttpClient.Builder()      //注册应用拦截器
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())        //添加Cookie
                .addInterceptor(new LoggingInterceptors())
//                .cookieJar(cookieJar)          //还不知道怎么用
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        return builder.build();
    }


    /**
     * 获取OkHttpClient
     *
     * @return {@link OkHttpClient}
     */
    private static OkHttpClient getOkHttpClient2() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptors())      //注册应用拦截器
                .addNetworkInterceptor(new Interceptor() {                                                        //注册网络拦截器
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

                        }
                        return originalResponse;
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        return builder.build();
    }


    /**
     * 网络日志处理   用来打印出去的请求和收到的响应
     * {@link LogUtil}
     */
    private static class LoggingInterceptors implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long t1 = System.nanoTime();
            Request request = chain.request();


//            Request.Builder builder = request.newBuilder();
//            HashSet<String> hashSet = new HashSet<>();
//            for (String cookie : hashSet) {
//                builder.addHeader("Cookie", cookie);
//            }

            /*
            Request
             */
            StringBuffer sb = new StringBuffer("Sending request");
            sb.append("\nurl=");
            sb.append(request.url());
            sb.append("\nmethod=");
            sb.append(request.method());
            sb.append("\non=");
            sb.append(chain.connection());
            sb.append("\nheaders=");
            sb.append(request.headers());
            sb.append("\ntag ");
            sb.append(request.tag());


            RequestBody requestBody = request.body();


            Response response = chain.proceed(request);             // Response
            BufferedSource source = response.body().source();
            if (source != null) {
                source.request(Long.MAX_VALUE);
                //获得返回的数据
                Buffer buffer1 = source.buffer();
                //使用前clone()下，避免直接消耗
                Log.e("Retrofit-data--", buffer1.clone().readString(Charset.forName("UTF-8")));
            }

            Log.e("Retrofit-url--", request.url() + "");

            if (requestBody != null) {
//                sb.append("\nRequestBody");
//                sb.append("\n\tcontentLength=");
//                sb.append(requestBody.contentLength());
//                sb.append("\n\tcontentType=");
//                sb.append(requestBody.contentType().toString());
//                try {
//                    final Request copy = request.newBuilder().build();
//                    final Buffer buffer = new Buffer();
//                    copy.body().writeTo(buffer);
//                    Charset charset = UTF8;
//                    MediaType contentType = copy.body().contentType();
//                    if (contentType != null) {
//                        charset = contentType.charset(UTF8);
//                    }
//                    sb.append("\nBody details:--->>>\n");
//                    sb.append(buffer.readString(charset));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {

            }

            LogUtil.i(TAG_LOG, sb.toString());

            /*
            Response
             */
//            Response response = chain.proceed(request);


            long t2 = System.nanoTime();
            LogUtil.i(TAG_LOG, String.format("Received response for %s in %.1fms%n%s",
                    request.url(), (t2 - t1) / 1e6d, response.headers()));


            return response;
        }
    }
}
