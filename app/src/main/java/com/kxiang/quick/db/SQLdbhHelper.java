package com.kxiang.quick.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:45
 */

public class SQLdbhHelper extends SQLiteOpenHelper {

    private static final String LIBRARY = "rapid.db";
    private static SQLdbhHelper helper;

    private SQLdbhHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, LIBRARY, factory, version);
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (helper == null) {
            // 将数据库设计成单利模式，只有一个连接，只有在多线程先操作不会出现错误，
            //在程序运行的时候不调用SQLiteDatabase的close方法，保持长连接，不然会奔溃
            helper = new SQLdbhHelper(context, null, 1);
        }
        return helper.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DaoTest.Sql.CREATE_TestTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
