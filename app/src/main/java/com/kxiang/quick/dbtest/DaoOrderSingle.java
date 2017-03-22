package com.kxiang.quick.dbtest;

import android.content.Context;
import android.database.Cursor;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:MoveOrder
 * 创建人:kexiang
 * 创建时间:2017/2/22 15:55
 */

public class DaoOrderSingle extends TableAbs {

    public DaoOrderSingle(Context context) {
        super(context);
    }


    public void insert(String[] data) {
        getDatabase().execSQL(DaoOrderSingle.SQL.INSERT, data);

    }

    public void upDate(int uploading_status, String order_content, String id) {

        String sql = "update " +
                DaoOrderSingle.SQL.TABLE_NAME +
                " set uploading_status = " + uploading_status +
//                "\"' and order_content = '\"" + order_content +
                " where id = " + id;

        LogUtils.toE("sqlite", "update:" + sql);
        getDatabase().execSQL("update order_single set uploading_status = ? , order_content = ?" +
                        " where id = ?",
                new String[]{
                        "" + uploading_status,
                        "" + order_content,
                        "" + id}
        );

    }


    public Cursor select(int uploading_status, int page, int pageSiz) {

        String sql = "select * from " +
                DaoOrderSingle.SQL.TABLE_NAME +
                " where uploading_status = '" +
                uploading_status +
                "' limit " +
                pageSiz +
                " offset " +
                (page * pageSiz - pageSiz);
        LogUtils.toE("sqlite", "select:" + sql);

        return getDatabase().rawQuery(sql, null);

    }


    public Cursor selectOrderOneSingle(long position) {

        String sql = "select * from " +
                DaoOrderSingle.SQL.TABLE_NAME +
                " order by id asc limit " +
                1 +
                " offset " + position;
        LogUtils.toE("sqlite", "select:" + sql);

        return getDatabase().rawQuery(sql, null);

    }


    public Cursor select(int uploading_status) {
        String sql = "select * from " +
                DaoOrderSingle.SQL.TABLE_NAME +
                " where uploading_status = " +
                uploading_status;
        LogUtils.toE("sqlite", "select:" + sql);
        return getDatabase().rawQuery(sql, null);

    }


    public static class SQL {

        public static final String TABLE_NAME = "order_single";
        /**
         * 订单详情表
         */
        public static final String CTEATE_TABLE =
                "create table " + TABLE_NAME +
                        "(id integer primary key autoincrement not null," +
                        " order_content text not null," +
                        " uploading_status integer not null," +
                        " save_time text not null)";
        public static final String INSERT =
                "insert into " +
                        TABLE_NAME +
                        " (order_content,uploading_status,save_time)" +
                        " values(?,?,?)";
        public static final String SELECT =
                "select * from " +
                        TABLE_NAME +
                        " order by id desc limit like ? offset like ?";
    }
}
