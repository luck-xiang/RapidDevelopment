package com.kxiang.quick.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:45
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String LIBRARY = "rapid.db";
    private static DBHelper helper;
    private static SQLiteDatabase database;

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, LIBRARY, factory, version);
    }

    public static SQLiteDatabase getInstance(Context context) {

        if (helper == null) {
            helper = new DBHelper(context, null, 1);
        }
        if (database == null) {
            database = helper.getWritableDatabase();
        }
        else if (!database.isOpen()) {
            database = helper.getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DaoTest.Sql.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
