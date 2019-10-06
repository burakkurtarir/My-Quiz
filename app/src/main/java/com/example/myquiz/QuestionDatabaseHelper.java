package com.example.myquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioFocusRequest;

import java.util.ArrayList;
import java.util.List;

public class QuestionDatabaseHelper {

    SQLiteDatabase database;
    SQLiteLayer layer;

    public QuestionDatabaseHelper(Context context){
        layer = new SQLiteLayer(context);
    }

    public void openWritableConnection(){
        database = layer.getWritableDatabase();
    }

    public void openReadableConnection(){
        database = layer.getReadableDatabase();
    }

    public void closeConnection(){
        layer.close();
    }

    public Question getQuestion(String specialId){
        openReadableConnection();

        Question question = new Question();
        Cursor cursor = database.rawQuery("select specialId from Question where specialId = ? ", new String[]{specialId});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            question.specialId = cursor.getString(0);
            cursor.close();
        }

        closeConnection();

        return question;
    }

    public List<Question> getAllQuestions(){
        openReadableConnection();

        List<Question> questions = new ArrayList<Question>();
        String columns[] = new String[]{"specialId", "name", "answer", "optionA", "optionB", "optionC", "optionD"};

        Cursor cursor = database.query("Question", columns, null, null, null, null, null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Question question = new Question();
                question.specialId = cursor.getString(0);
                question.name = cursor.getString(1);
                question.answer = cursor.getInt(2);
                question.optionA = cursor.getString(3);
                question.optionB = cursor.getString(4);
                question.optionC = cursor.getString(5);
                question.optionD = cursor.getString(6);

                questions.add(question);
            }
        }

        closeConnection();

        return questions;
    }

    public void addQuestion(Question question){
        openWritableConnection();

        ContentValues contentValues = new ContentValues();
        contentValues.put("specialId", question.specialId);
        contentValues.put("name", question.name);
        contentValues.put("answer", question.answer);
        contentValues.put("optionA", question.optionA);
        contentValues.put("optionB", question.optionB);
        contentValues.put("optionC", question.optionC);
        contentValues.put("optionD", question.optionD);

        database.insert("Question", null, contentValues);

        closeConnection();
    }
}
