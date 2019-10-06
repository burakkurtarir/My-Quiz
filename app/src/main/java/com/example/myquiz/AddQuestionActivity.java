package com.example.myquiz;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.nio.charset.Charset;
import java.util.Random;

public class AddQuestionActivity extends AppCompatActivity {

    EditText edtName, edtoptionA, edtoptionB, edtoptionC, edtoptionD;
    RadioButton rbOptionA, rbOptionB, rbOptionC, rbOptionD;

    QuestionFirebaseHelper questionFirebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        edtName = findViewById(R.id.edtName);
        edtoptionA = findViewById(R.id.edtOptionA);
        edtoptionB = findViewById(R.id.edtOptionB);
        edtoptionC = findViewById(R.id.edtOptionC);
        edtoptionD = findViewById(R.id.edtOptionD);

        rbOptionA = findViewById(R.id.rBOptionA);
        rbOptionB = findViewById(R.id.rbOptionB);
        rbOptionC = findViewById(R.id.rbOptionC);
        rbOptionD = findViewById(R.id.rbOptionD);

        //QuestionFirebaseHelper
        questionFirebaseHelper = new QuestionFirebaseHelper(this);
    }

    public void addQuestion(View view){

        Question question = new Question();
        //Kullanıcının specialId değeri random olarak veriliyor
        question.specialId = generateRandomString(10);
        question.name = edtName.getText().toString();
        question.answer = rbOptionA.isChecked() ? 1 : rbOptionB.isChecked() ? 2 : rbOptionC.isChecked() ? 3 : rbOptionD.isChecked() ? 4 : 0;
        question.optionA = edtoptionA.getText().toString();
        question.optionB = edtoptionB.getText().toString();
        question.optionC = edtoptionC.getText().toString();
        question.optionD = edtoptionD.getText().toString();

        if(question.name.isEmpty()){
            edtName.setError("Name cannot be empty");
            return;
        }
        if(question.optionA.isEmpty()){
            edtoptionA.setError("Option A cannot be empty");
            return;
        }
        if(question.optionB.isEmpty()){
            edtoptionB.setError("Option B cannot be empty");
            return;
        }
        if(question.optionC.isEmpty()){
            edtoptionC.setError("Option C cannot be empty");
            return;
        }
        if(question.optionD.isEmpty()){
            edtoptionD.setError("Option D cannot be empty");
            return;
        }

        questionFirebaseHelper.add(question);

        showMessage("Message", "Question added successfully :)");

        //EditTextleri temizle
        edtName.setText("");
        edtoptionA.setText("");
        edtoptionB.setText("");
        edtoptionC.setText("");
        edtoptionD.setText("");

        //Ekranda göstermek için
        /*StringBuffer buffer = new StringBuffer();
        buffer.append("Name: " + question.name + "\n");
        buffer.append("Special Id: " + question.specialId + "\n");
        buffer.append("Answer: " + question.answer + "\n");
        buffer.append("Option A: " + question.optionA + "\n");
        buffer.append("Option B: " + question.optionB + "\n");
        buffer.append("Option C: " + question.optionC + "\n");
        buffer.append("Option D: " + question.optionD + "\n");

        showMessage("Data", buffer.toString());*/
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public String generateRandomString(int size){
      String AlphaNumericString = "ABCDEFGHIJKLMNOPRSTUVWXYZ" +
                                  "0123456789" +
                                  "abcdefghijklmnoprstuvwxyz";

      StringBuilder stringBuilder = new StringBuilder(size);

      for(int i=0; i<size; i++){
          int index = (int)(AlphaNumericString.length() * Math.random());
          stringBuilder.append(AlphaNumericString.charAt(index));
      }

      return stringBuilder.toString();
    }

}
