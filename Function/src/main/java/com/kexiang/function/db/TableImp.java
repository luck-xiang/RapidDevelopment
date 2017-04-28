package com.kexiang.function.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:44
 */

public class TableImp implements TableInterface {


    private SQLiteDatabase database;

    protected TableImp(Context context) {
        database = DBHelper.getInstance(context);
    }

    /**
     * 说Dao类的公共方法，方便实现外部调用获取SQLiteDatabase
     * @return
     */
    @Override
    public SQLiteDatabase getDatabase() {

        return database;
    }
}
