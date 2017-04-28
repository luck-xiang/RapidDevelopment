package com.kxiang.quick.net;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.utils.NetUtil;
import com.kxiang.quick.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称:Retrofit2.0+RxJava+RxAndroid
 * 创建人:kexiang
 * 创建时间:2016/9/28 15:24
 */
public class BaseRetrofit {


    public static final String HEAD_LINE_NEWS = "T1348647909107";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";


    ApiNetworkAddressService apiService;

    public final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();


    BaseRetrofit() {


        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(
                new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "HttpCache"),
//                new File(BaseApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100
        );

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .cache(cache)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(sLoggingInterceptor)
                        .addInterceptor(sRewriteCacheControlInterceptor)
                        .addInterceptor(httpLoggingInterceptor)
                        .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                        .addNetworkInterceptor(httpLoggingInterceptor)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(UrlFields.URL_BASE)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

        apiService = retrofit.create(ApiNetworkAddressService.class);

    }

    /**
     * 设置打印接口
     */
    private HttpLoggingInterceptor httpLoggingInterceptor =
            new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.toJsonFormat(message);
                }
            });

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            LogUtils.toE("LogTAG", "sRewriteCacheControlInterceptor:"+request.body());
//            okhttp3.Response proceed = chain.proceed(request);
//            Log.e("zzz", "proceed.body()====" + URLDecoder.decode(proceed.body().string(), "UTF-8"));

            if (!NetUtil.isNetworkAvailable(BaseApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//                LogUtils.toE("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(BaseApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
            else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            }

            return chain.proceed(request);
        }
    };

    @NonNull
    private static String parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    public ApiNetworkAddressService getApiService() {
        return apiService;
    }
}
