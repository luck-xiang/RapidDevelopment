package com.kxiang.quick.db;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.kxiang.quick.utils.LogUtils;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/2/22 12:44
 */

public class DaoTest extends TableImp {


    public DaoTest(Context context) {
        super(context);
    }


    /**
     * 快速插入数据，这个方法是insert(String[] data)一条一条插入数据库的快好几倍
     * 全部插入成功返回true，否则false
     */
    public boolean insert(List<TestBean> data) {

        boolean saveSuccee = false;
        LogUtils.toE("sql", "insert:" + DaoTest.Sql.insertALL);
        //开启事务
        getDatabase().beginTransaction();
        try {
            SQLiteStatement stmt = getDatabase().compileStatement(DaoTest.Sql.insertALL);
            for (int i = 0; i < data.size(); i++) {
                stmt.bindString(1, data.get(i).getName());
                stmt.bindString(2, data.get(i).getSex());
                stmt.bindString(3, data.get(i).getId_DB() + "");
                stmt.execute();
                stmt.clearBindings();
            }
            //事务完成
            getDatabase().setTransactionSuccessful();
            saveSuccee = true;
        } catch (Exception e) {
            saveSuccee = false;
        } finally {
            //提交事务
            getDatabase().endTransaction();
        }


        LogUtils.toE("sql", "insert:" + DaoTest.Sql.insertALL);


        return saveSuccee;
    }

    public void insert(String[] data) {
        LogUtils.toE("sql", "insert:" + DaoTest.Sql.insertALL);
        getDatabase().execSQL(DaoTest.Sql.insertALL, data);
    }

    public void insert(String sql) {
        LogUtils.toE("sql", "insert:" + sql);
        getDatabase().execSQL(sql);
    }


    /**
     * 删除
     */
    public void delete() {

    }

    /**
     * 修改
     */
    public void updata() {

    }

    /**
     * 查询
     */
    public void seletct() {

    }

    /**
     * 将所有Sql语句提到一个类中，方便管理
     */
    public static class Sql {
        public static String NAME = "testTable";
        /**
         * 创建表
         */
        public static final String CREATE_TestTable =
                "create table " +
                        NAME +
                        "(id integer primary key autoincrement not null," +
                        " name text not null," +
                        " sex text not null," +
                        " id_DB integer not null)";

        private static final String insertALL = "insert into " +
                NAME +
                " (name,sex,id_DB)" +
                " values(?,?,?)";
    }

}
