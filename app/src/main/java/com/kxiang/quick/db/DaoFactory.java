package com.kxiang.quick.db;

import android.content.Context;

import com.kexiang.function.db.TableInterface;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:55
 */

public class DaoFactory {

    public static final int TestTable = 1;

    public static TableInterface getTable(int type, Context context) {
        TableInterface table = null;
        switch (type) {
            case TestTable:
                table = new DaoTest(context);
                break;
        }
        return table;
    }
}
