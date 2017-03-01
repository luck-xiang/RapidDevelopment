package com.kxiang.quick.net;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 项目名称:Retrofit2.0+RxJava+RxAndroid
 * 创建人:kexiang
 * 创建时间:2016/9/28 14:37
 */
public interface ApiNetworkAddressService {

    @FormUrlEncoded
    @POST("message/UpdateReadTimes")
    public Call<ResponseBody> setData(@Field("id") String id);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("message/UpdateReadTimes")
    public Call<ResponseBody> setData(@Body RequestBody route);


}
