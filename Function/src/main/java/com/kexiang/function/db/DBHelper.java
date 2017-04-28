package com.kexiang.function.db;

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
        db.execSQL(Table.Test_Create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private static class Table {

        /**
         * 创建表
         */
        private static final String Test_Create =
                "create table testTable " +
                        "(id integer primary key autoincrement not null," +
                        " name text not null," +
                        " sex text not null," +
                        " id_DB integer not null)";
    }
}
