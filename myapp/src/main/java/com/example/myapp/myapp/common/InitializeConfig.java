package com.example.myapp.myapp.common;

import android.app.Application;

import com.example.myapp.myapp.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.logging.Level;


/**
 * Created by yexing on 2018/4/17.
 */

public class InitializeConfig {

    public static final boolean isDebug = true;

    public static void init(Application appContext) {

        //Log
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }

        });

        ToastUtil.initToastApp(appContext);

        //友盟第三方分享
        UMConfigure.init(appContext, "5b598a94b27b0a77c30000d1"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin("wxdda8441a2fcd9957", "9dcf5851d8d807bbcf92f7dd56856eae");
        PlatformConfig.setSinaWeibo("3072140610", "faa0b4cf935fa6f3ee935b531190106c", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1107157177", "ri5rEKt2ZU3zUaBI");


        //初始化框架
//        Retrofit.Builder builder = new Retrofit.Builder();
//        builder.baseUrl(AppUrl.BASEURL);//配置主机地址， 必须以/ 结束
//        builder.addConverterFactory(GsonConverterFactory.create(new Gson()));// 指定使用什么框架完成json解析
//        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());  //支持RxJava
//        //读取@get上面的请求
//        httpUtil = builder.build().create(WandroidApi.class);


        //必须调用初始化
        OkGo.init(appContext);


        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo----------", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3);

            //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
            //      .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
//                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

            //可以设置https的证书,以下几种方案根据需要自己设置
//                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
//                  .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
            //      .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
            //              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//                  .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
            //      .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
            //      .addInterceptor(new Interceptor() {
            //            @Override
            //            public Response intercept(Chain chain) throws IOException {
            //                 return chain.proceed(chain.request());
            //            }
            //       })

            //这两行同上，不需要就不要加入
//                    .addCommonHeaders(headers)  //设置全局公共头
//                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
