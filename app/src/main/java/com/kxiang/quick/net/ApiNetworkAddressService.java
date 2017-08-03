package com.kxiang.quick.net;

import com.kxiang.quick.news.bean.NewsInfo;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

import static com.kxiang.quick.net.BaseRetrofit.CACHE_CONTROL_NETWORK;

/**
 * 项目名称:Retrofit2.0+RxJava+RxAndroid
 * 创建人:kexiang
 * 创建时间:2016/9/28 14:37
 */
public interface ApiNetworkAddressService {


    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    RequestBody body = RequestBody.create(JSON, "jsonString");

    /**
     * 获取新闻列表
     * eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     * http://c.m.163.com/nc/article/list/T1348647909107/60-20.html
     *
     * @param type      新闻类型
     * @param id        新闻ID
     * @param startPage 起始页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Call<NewsInfo> getNewsList(
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Call<ResponseBody> getNewsListTest(
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );


    /**
     * 获取新闻列表
     * eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     * http://c.m.163.com/nc/article/list/T1348647909107/60-20.html
     *
     * @param type      新闻类型
     * @param id        新闻ID
     * @param startPage 起始页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Response<NewsInfo>> getQueueList(
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Call<NewsInfo> getQueueListCall(
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );

    @GET
    Call<NewsInfo> newWork(
            @Url() String type
    );


    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @FormUrlEncoded
    @POST("message/UpdateReadTimes")
    public Call<ResponseBody> setData(@Field("id") String id);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("message/UpdateReadTimes")
    public Call<ResponseBody> setData(@Body RequestBody route);


}
