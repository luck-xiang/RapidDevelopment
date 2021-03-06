package com.kxiang.quick.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.kexiang.function.db.TableImp;
import com.kexiang.function.utils.LogUtils;

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
    public boolean insertAndroid(List<TestBean> data, String name) {

        boolean saveSuccee = false;
//        LogUtils.toE("sql", "insert:" + DaoTest.Sql.insertALL);

        //开启事务
        getDatabase().beginTransaction();
        try {
            SQLiteStatement stmt = getDatabase().compileStatement(DaoTest.Sql.insertALL);
            for (int i = 0; i < data.size(); i++) {

                if (i == 10) {
                    Thread.sleep(2000);
                }
                LogUtils.toE("sql", "insert:" + name);
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

    public void insertAndroid(String[] data) {
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
    public void deleteAndroid() {
        //
        getDatabase().delete(Sql.TABLE_NAME, null, null);
    }

    /**
     * 修改
     */
    public void updataAndroid() {

    }

    public void upDate(String uploading_status, String trade_id, String name) {
        LogUtils.toE("sql", "insert:" + name);
        //开启事务
        getDatabase().beginTransaction();
        try {
            getDatabase().execSQL("update testTable set name = ? " +
                            " where id_DB = ?",
                    new String[]{
                            uploading_status,
                            trade_id
                    }
            );
            //事务完成
            getDatabase().setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            //提交事务
            getDatabase().endTransaction();
        }

    }


    /**
     * 查询
     */
    public void seletct(int id) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from ");
        buffer.append(Sql.TABLE_NAME);
        buffer.append(" where id_DB = ");
        buffer.append(id);
        String sql = buffer.toString();
        Cursor cursor = getDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id_DB = cursor.getInt(cursor.
                    getColumnIndex("id_DB"));
            LogUtils.toE(id_DB);
        }

        cursor.close();

    }

    /**
     * 将所有Sql语句提到一个类中，方便管理
     */
    public static class Sql {
        public static String TABLE_NAME = "testTable";


        public static final String insertALL = "insert into " +
                TABLE_NAME +
                " (name,sex,id_DB)" +
                " values(?,?,?)";
    }

}
