package com.kxiang.quick.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:44
 */

public interface TableInterface {
    /**
     * 用于获取数据库的连接SQLiteDatabase
     *
     * @return
     */
    SQLiteDatabase getDatabase();



}
