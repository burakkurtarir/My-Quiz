package com.example.myquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteLayer extends SQLiteOpenHelper {

    public SQLiteLayer(Context context){
        super(context, "MyQuiz", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Question (specialId text, name text, answer integer, optionA text, optionB text, optionC text, optionD text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists Question";
        db.execSQL(sql);
    }
}
