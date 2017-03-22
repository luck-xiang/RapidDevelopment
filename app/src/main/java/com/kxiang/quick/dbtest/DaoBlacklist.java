package com.kxiang.quick.dbtest;

import android.content.Context;
import android.database.Cursor;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:HandheldPC
 * 创建人:kexiang
 * 创建时间:2017/3/10 9:18
 */

public class DaoBlacklist extends TableAbs {
    public DaoBlacklist(Context context) {
        super(context);
    }


    public boolean select(String cardId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from ");
        buffer.append(SQL.TABLE_NAME);
        buffer.append(" where black_card_id = ");
        buffer.append(cardId);
        LogUtils.toE(buffer.toString());
        Cursor cursor = getDatabase().rawQuery(buffer.toString(), null);
        boolean all = false;
        while (cursor.moveToNext()) {
            all = true;
            break;
        }
        cursor.close();

        LogUtils.toE("all", all);
        return all;
    }


    public void insert(String black_card_id, String save_time) {
        getDatabase().execSQL(SQL.INSERT, new String[]{
                black_card_id,
                save_time
        });
    }

    public void deleteAndroid(String black_card_id) {

        getDatabase().delete(SQL.TABLE_NAME, "black_card_id=?", new String[]{
                black_card_id
        });
    }

    public void delete() {
        SQLUtils.deleteAllData(getDatabase(), SQL.TABLE_NAME);
    }


    public static class SQL {
        public static String TABLE_NAME = "blacklist";
        public static String CTEATE_TABLE =
                "create table " + TABLE_NAME +
                        "(id integer primary key autoincrement not null," +
                        " black_card_id text not null," +
                        " save_time text not null)";
        public static String INSERT = "insert into " +
                TABLE_NAME +
                " (black_card_id,save_time)" +
                " values(?,?)";

    }
}
