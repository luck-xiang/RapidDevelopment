/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kxiang.quick.base;

import android.app.Application;
import android.content.Context;

import com.kexiang.function.utils.AppException;
import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.net.ApiNetworkAddressService;
import com.kxiang.quick.net.BaseRetrofitFactory;
import com.tencent.bugly.Bugly;


public class BaseApplication extends Application {

    protected ApiNetworkAddressService apiNetService;
    public static final String APP_ID = "72bdbd38c8"; // TODO 替换成bugly上注册的appid

    @Override
    public void onCreate() {
//        MultiDex.install(this);
        LogUtils.initDebug();
        //  CrashReport.initCrashReport(getApplicationContext(), APP_ID, false);
        Bugly.init(getApplicationContext(), APP_ID, false);
        super.onCreate();
        context = this;
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());

    }


    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private static BaseApplication context;

    public static BaseApplication getContext() {
        return context;
    }


    public ApiNetworkAddressService getApiNetService() {
        if (apiNetService == null) {
            apiNetService = BaseRetrofitFactory.getApiService();
        }
        return apiNetService;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }


}
