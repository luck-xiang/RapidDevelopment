package com.kxiang.quick.dbtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:44
 */

public  class TableAbs implements TableInterface {

    private SQLiteDatabase database;

    public TableAbs(Context context) {
        database = DBHelper.getInstance(context);
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return database;
    }
}
