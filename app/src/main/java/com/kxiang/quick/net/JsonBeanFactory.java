package com.kxiang.quick.net;

/**
 * 项目名称:Retrofit2.0+RxJava+RxAndroid
 * 创建人:kexiang
 * 创建时间:2016/9/28 15:30
 */
public class JsonBeanFactory {
    protected static final Object monitor = new Object();
    private static ApiNetworkAddressService apiService;

    public static ApiNetworkAddressService getApiService() {
        synchronized (monitor) {
            if (apiService == null) {
                apiService = new InitBaseRetrofit().getApiService();
            }
            return apiService;
        }
    }

}
