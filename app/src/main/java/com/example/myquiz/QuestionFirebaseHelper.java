package com.example.myquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.style.QuoteSpan;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionFirebaseHelper {

    private DatabaseReference mDatabase;
    QuestionDatabaseHelper questionDatabaseHelper;
    Context _context;

    public QuestionFirebaseHelper(Context context){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        questionDatabaseHelper = new QuestionDatabaseHelper(context);
        _context = context;
    }

    public void add(Question question){
        mDatabase.child("questions").child(question.specialId).setValue(question);
    }

    public void getAll(){
        final List<Question> questions = new ArrayList<Question>();

        mDatabase.child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Question question = keyNode.getValue(Question.class);
                    questions.add(question);
                }

                //Firebase'den okunan verilerin içinde bir tur atalım :)
                for(Question question : questions){
                    Question myQuestion = questionDatabaseHelper.getQuestion(question.specialId);
                    //Eğer Firebase'deki veri local veritabanında yoksa, local veritabanına ekle
                    if(myQuestion.specialId == null) {
                        questionDatabaseHelper.addQuestion(question);
                        Toast.makeText(_context, "Yeni data eklendi " + question.specialId, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(_context, "Bu data zaten var " + question.specialId, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
