package com.kxiang.quick.dbtest;

import android.content.Context;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:55
 */

public class DaoFactory {


    public static final int OrderSingle = 1;
    public static final int BlackeList = 2;

    public static TableInterface getTable(int type, Context context) {
        TableInterface table = null;

        switch (type) {
            case OrderSingle:
                table = new DaoOrderSingle(context);
                break;
             case BlackeList:
                table = new DaoBlacklist(context);
                break;

        }
        return table;

    }
}
