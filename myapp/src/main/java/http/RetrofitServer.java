package http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
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
import utils.LogUtil;

/**
 * Created by daixiankade on 2018/4/13.
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

        OkHttpClient okHttpClient = getOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASEURL2)
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

        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptors())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        return builder.build();
    }


    /**
     * 网络日志处理
     * {@link utils.LogUtil}
     */
    private static class LoggingInterceptors implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long t1 = System.nanoTime();
            Request request = chain.request();
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
            sb.append("tag ");
            sb.append(request.tag());



            RequestBody requestBody = request.body();

            Response response1 = chain.proceed(request);
            BufferedSource source = response1.body().source();
            source.request(Long.MAX_VALUE);
            //获得返回的数据
            Buffer buffer1 = source.buffer();
            //使用前clone()下，避免直接消耗
            Log.e("Retrofit-data--" , buffer1.clone().readString(Charset.forName("UTF-8")));
            Log.e("Retrofit-url--" , request.url()+"");

            if (requestBody != null) {
                sb.append("\nRequestBody");
                sb.append("\n\tcontentLength=");
                sb.append(requestBody.contentLength());
                sb.append("\n\tcontentType=");
                sb.append(requestBody.contentType().toString());

                try {
                    final Request copy = request.newBuilder().build();
                    final Buffer buffer = new Buffer();
                    copy.body().writeTo(buffer);
                    Charset charset = UTF8;
                    MediaType contentType = copy.body().contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }
                    sb.append("\nBody details:--->>>\n");
                    sb.append(buffer.readString(charset));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            LogUtil.i(TAG_LOG, sb.toString());

            /*
            Response
             */
            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtil.i(TAG_LOG, String.format("Received response for %s in %.1fms%n%s",
                    request.url(), (t2 - t1) / 1e6d, response.headers()));



            return response;
        }
    }
}
