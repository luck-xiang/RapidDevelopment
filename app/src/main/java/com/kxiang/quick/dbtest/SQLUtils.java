package com.kxiang.quick.dbtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:HandheldPC
 * 创建人:kexiang
 * 创建时间:2017/3/3 15:40
 */

public class SQLUtils {


    /**
     * 删除表中的所有数据数据
     *
     * @param database
     * @param tableName
     */
    public static void deleteAllData(SQLiteDatabase database, String tableName) {
        String sql = "delete from " + tableName;
        LogUtils.toE("sqlite", "delete:" + sql);
        database.execSQL(sql);
    }

    /**
     * 查询数据库中的总条数.
     *
     * @return
     */
    public static long getAllCaseNum(SQLiteDatabase database, String tableName) {
        String sql = "select count(*) from " + tableName;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    /**
     * 获取数据库中全部数据
     *
     * @param database
     * @param tableName
     * @return
     */

    public Cursor selectAllData(SQLiteDatabase database, String tableName) {
        String sql = "select * from " + tableName;
        LogUtils.toE("sqlite", "select:" + sql);
        return database.rawQuery(sql, null);

    }
}
