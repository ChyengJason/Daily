package com.example.cheng.myfifthapplication.Hleper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cheng on 16-3-6.
 * 数据库操作
 */
public class DbHelper extends SQLiteOpenHelper {
    // 数据库名
    public final static String DATABASE_NAME = "DailyData.db";
    // 数据库版本
    public static int DATABASE_VERSION = 1;
    // 表名
    public final static String TABLE_NAME = "daily";
    // 表中的字段
    public final static String DAILY_ID = "id";
    public final static String DAILY_DATE = "date";
    public final static String DAILY_TIME = "time";
    public final static String DAILY_TITLE = "title";
    public final static String DAILY_CONTENT = "content";
    public final static String DAILY_COLOR = "color";
//    public final static String DAILY_HASH = "hash";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + DAILY_ID + " INTEGER primary key autoincrement, "
                + DAILY_DATE + " text, " + DAILY_TIME + " text," + DAILY_TITLE
                + " text," + DAILY_CONTENT  +" text, " + DAILY_COLOR + " text);";
        db.execSQL(sql);
    }

    /**
     * 数据库升级时调用 删除数据库中原有的user表，并重新创建新user表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}

