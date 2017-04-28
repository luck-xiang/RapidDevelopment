package com.kexiang.function.utils;

import java.util.List;

import retrofit2.Response;

/**
 * 项目名称:queued
 * 创建人:kexiang
 * 创建时间:2017/4/18 9:04
 */

public class OkhttpBackUtils {

    public static boolean isBackDataNoEmpty(Response value) {
        return value.code() == 200 && value.body() != null;
    }

    public static boolean isBackListNoEmpty(List list) {
        return list != null && list.size() != 0;
    }

}
