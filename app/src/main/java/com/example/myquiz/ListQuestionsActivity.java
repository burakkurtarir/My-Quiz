package com.example.myquiz;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class ListQuestionsActivity extends AppCompatActivity {

    QuestionFirebaseHelper questionFirebaseHelper;
    QuestionDatabaseHelper questionDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        questionFirebaseHelper = new QuestionFirebaseHelper(this);
        questionDatabaseHelper = new QuestionDatabaseHelper(this);
    }

    public void getAllQuestions(View view){
       questionFirebaseHelper.getAll();
    }

    public void getAllQuestionsFromLocalDatabase(View view){
        List<Question> questions =  questionDatabaseHelper.getAllQuestions();
        StringBuffer buffer = new StringBuffer();
        for(Question question : questions){
            buffer.append("Special Id: " + question.specialId + "\n");
            buffer.append("Name: " + question.name + "\n");
            buffer.append("Answer: " + question.answer + "\n");
            buffer.append("Option A: " + question.optionA + "\n");
            buffer.append("Option B: " + question.optionB + "\n");
            buffer.append("Option C: " + question.optionC + "\n");
            buffer.append("Option D: " + question.optionD + "\n\n");
        }
        showMessage("Title", buffer.toString());
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
