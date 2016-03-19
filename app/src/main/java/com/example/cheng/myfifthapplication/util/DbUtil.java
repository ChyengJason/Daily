package com.example.cheng.myfifthapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.cheng.myfifthapplication.Bean.DailyInfo;
import com.example.cheng.myfifthapplication.Hleper.DbHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by cheng on 16-3-6.
 */
public class DbUtil {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DbUtil(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // 插入数据操作
    public void insert(DailyInfo dailyInfo) {
        this.open();
        ContentValues cv = new ContentValues();

        cv.put(DbHelper.DAILY_TITLE, dailyInfo.getTitle());
        cv.put(DbHelper.DAILY_DATE, dailyInfo.getDate());
        cv.put(DbHelper.DAILY_TIME, dailyInfo.getTime());
        cv.put(DbHelper.DAILY_CONTENT, dailyInfo.getContent());
        cv.put(DbHelper.DAILY_COLOR,dailyInfo.getColor());
//        cv.put(DbHelper.DAILY_HASH, dailyInfo.getHash());
        db.insert(DbHelper.TABLE_NAME, null, cv);
        this.close();
    }

    // 删除数据操作
    public boolean delete(String id) {
        this.open();
        String where = DbHelper.DAILY_ID + " = ?";
        String[] whereValue = {id};
        if (db.delete(DbHelper.TABLE_NAME, where, whereValue) > 0) {
            this.close();
            return true;
        }
        return false;
    }

    // 更新数据操作
    public boolean update(String id, DailyInfo dailyInfo) {
        this.open();
        ContentValues cv = new ContentValues();

        cv.put(DbHelper.DAILY_TITLE, dailyInfo.getTitle());
        cv.put(DbHelper.DAILY_DATE, dailyInfo.getDate());
        cv.put(DbHelper.DAILY_TIME, dailyInfo.getTime());
        cv.put(DbHelper.DAILY_CONTENT, dailyInfo.getContent());
        cv.put(DbHelper.DAILY_COLOR, dailyInfo.getColor());
//        cv.put(DbHelper.DAILY_HASH, dailyInfo.getHash());

        if (db.update(DbHelper.TABLE_NAME, cv, DbHelper.DAILY_ID + "=?",
                new String[]{id}) > 0) {
            this.close();
            return true;
        }
        db.close();
        return false;
    }

    // 获取所有数据
    public ArrayList<DailyInfo> getAllData() {
        this.open();
        ArrayList<DailyInfo> items = new ArrayList<DailyInfo>();
        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor
                        .getColumnIndex(DbHelper.DAILY_ID));
                String date = cursor.getString(cursor
                        .getColumnIndex(DbHelper.DAILY_DATE));
                String time = cursor.getString(cursor
                        .getColumnIndex(DbHelper.DAILY_TIME));
                String title = cursor.getString(cursor
                        .getColumnIndex(DbHelper.DAILY_TITLE));
                String content = cursor.getString(cursor
                        .getColumnIndex(DbHelper.DAILY_CONTENT));
                String color = cursor.getString(cursor
                        .getColumnIndex(DbHelper.DAILY_COLOR));

                DailyInfo item = new DailyInfo(Integer.parseInt(id), date, time, title, content, color);
                items.add(item);
                cursor.moveToNext();
            }
        }
        this.close();
        Collections.reverse(items);
        return items;
    }

    public int lastId(){
        return getAllData().get(0).getId();
    }

}
