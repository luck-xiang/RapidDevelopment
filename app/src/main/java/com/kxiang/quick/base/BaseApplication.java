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
import android.support.multidex.MultiDex;

import com.google.gson.Gson;
import com.kxiang.quick.bean.LoginBack;
import com.kxiang.quick.net.ApiNetworkAddressService;
import com.kxiang.quick.net.JsonBeanFactory;
import com.kxiang.quick.utils.AppException;
import com.kxiang.quick.utils.LogUtils;
import com.kxiang.quick.utils.MD5Util;
import com.kxiang.quick.utils.SharedFieds;
import com.kxiang.quick.utils.SharedPreferencesUtil;


public class BaseApplication extends Application {

    protected ApiNetworkAddressService apiNetService;
    @Override
    public void onCreate() {
        MultiDex.install(this);
        LogUtils.initDebug(true);
        super.onCreate();
        instance = this;
        apiNetService = JsonBeanFactory.getApiService();
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());

    }

    private LoginBack loginBack;
    public static int PermissionNull = -10;
    private int topPermission = PermissionNull;


    public int getTopPermission() {
        if (topPermission == PermissionNull) {
            if (loginBack == null) {
                Gson gson = new Gson();
                String login = (String) SharedPreferencesUtil.get(this,
                        MD5Util.MD5ToBig32(SharedFieds.LOGINBACK), "error");
                if (login != null && !login.equals("error")) {
                    loginBack = gson.fromJson(login, LoginBack.class);
//                    topPermission =
//                            ShopPermissionManage.getTopPermission(loginBack.getCurrentShopRoles());
                }
            }

        }
        return topPermission;
    }


    public void setTopPermission(int topPermission) {
        this.topPermission = topPermission;
    }

    public LoginBack getLoginBack() {
        if (loginBack == null) {
            Gson gson = new Gson();
            String login = (String) SharedPreferencesUtil.get(this,
                    MD5Util.MD5ToBig32(SharedFieds.LOGINBACK), "error");
            if (login != null && !login.equals("error")) {
                loginBack = gson.fromJson(login, LoginBack.class);
//                topPermission =
//                        ShopPermissionManage.getTopPermission(loginBack.getCurrentShopRoles());
            }
        }
        return loginBack;
    }

    public void setLoginBack(LoginBack loginBack) {
        this.loginBack = loginBack;
    }

    private static BaseApplication instance;
    public static BaseApplication getInstance() {
        return instance;
    }

    public ApiNetworkAddressService getApiNetService() {
        return apiNetService;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}
