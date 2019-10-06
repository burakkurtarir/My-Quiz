package com.example.myquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    QuestionFirebaseHelper questionFirebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionFirebaseHelper = new QuestionFirebaseHelper(this);

        synchronizeDtabaseWithFirebase();
    }

    public void addQuestion(View view){
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
    }

    public void listQuestions(View view){
        Intent intent = new Intent(this, ListQuestionsActivity.class);
        startActivity(intent);
    }

    public void play(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void synchronizeDtabaseWithFirebase(){
        questionFirebaseHelper.getAll();
    }
}
