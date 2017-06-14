package com.kxiang.quick.net;

/**
 * 项目名称:Retrofit2.0+RxJava+RxAndroid
 * 创建人:kexiang
 * 创建时间:2016/9/28 15:30
 */
public class BaseRetrofitFactory {
    private static ApiNetworkAddressService apiService;

    public static ApiNetworkAddressService getApiService() {
        synchronized (BaseRetrofitFactory.class) {
            if (apiService == null) {
                apiService = new BaseRetrofit().getApiService();
            }
            return apiService;
        }
    }

}
