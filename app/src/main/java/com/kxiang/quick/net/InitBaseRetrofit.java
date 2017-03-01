package com.kxiang.quick.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称:Retrofit2.0+RxJava+RxAndroid
 * 创建人:kexiang
 * 创建时间:2016/9/28 15:24
 */
public class InitBaseRetrofit {

    ApiNetworkAddressService apiService;

    public  final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();


    InitBaseRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlFields.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiNetworkAddressService.class);

    }


    public ApiNetworkAddressService getApiService() {
        return apiService;
    }
}
