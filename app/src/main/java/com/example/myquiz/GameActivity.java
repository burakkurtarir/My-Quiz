package com.example.myquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    TextView txtName, txtOptionA, txtOptionB, txtOptionC, txtOptionD, currentTextView, txtScore;
    Button btnSeeNextQuestion;
    LinearLayout lyScore;

    List<Question> questionList;
    List<TextView> textViewList;
    QuestionDatabaseHelper questionDatabaseHelper;
    int currentQuestionNumber, trueAnswers, falseAnswers, questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionDatabaseHelper = new QuestionDatabaseHelper(this);

        txtScore = findViewById(R.id.txtScore);
        lyScore = findViewById(R.id.lyScore);
        btnSeeNextQuestion = findViewById(R.id.btnSeeNextQuestion);
        txtName = findViewById(R.id.txtName);
        txtOptionA = findViewById(R.id.txtOptionA);
        txtOptionB = findViewById(R.id.txtOptionB);
        txtOptionC = findViewById(R.id.txtOptionC);
        txtOptionD = findViewById(R.id.txtOptionD);

        txtOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 1;
                currentTextView = (TextView) v;
                answerTheQuestion(answer, v);
            }
        });
        txtOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 2;
                currentTextView = (TextView) v;
                answerTheQuestion(answer, v);
            }
        });
        txtOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 3;
                currentTextView = (TextView) v;
                answerTheQuestion(answer, v);
            }
        });
        txtOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 4;
                currentTextView = (TextView) v;
                answerTheQuestion(answer, v);
            }
        });

        textViewList = new ArrayList<TextView>();
        textViewList.add(txtOptionA);
        textViewList.add(txtOptionB);
        textViewList.add(txtOptionC);
        textViewList.add(txtOptionD);

        startGame();
    }

    public void setTextViewsClickable(boolean isClickable){
        for(TextView textView : textViewList){
            textView.setClickable(isClickable);
        }
    }

    public void startGame(){
        questionList = questionDatabaseHelper.getAllQuestions();
        Collections.shuffle(questionList);

        if(questionList.isEmpty()){
            setTextViewsClickable(false);
            Toast.makeText(this, "There is no question in database", Toast.LENGTH_SHORT).show();
            return;
        }

        currentQuestionNumber = 0;
        trueAnswers = 0;
        falseAnswers = 0;
        questionNumber = questionList.size();

        showCurrentQuestion();
    }

    public Question getCurrentQuestion(){
        return questionList.get(currentQuestionNumber);
    }

    public void showCurrentQuestion(){
        Question currentQuestion = getCurrentQuestion();
        txtName.setText((currentQuestionNumber + 1) + ") " + currentQuestion.name);
        txtOptionA.setText("A) " + currentQuestion.optionA);
        txtOptionB.setText("B) " + currentQuestion.optionB);
        txtOptionC.setText("C) " + currentQuestion.optionC);
        txtOptionD.setText("D) " + currentQuestion.optionD);
    }

    public void answerTheQuestion(final int answer, View view){
        setTextViewsClickable(false);
        final TextView textView = (TextView) view;
        view.setBackgroundResource(R.drawable.textview_black_border);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isAnswerRight = isAnswerRight(answer);
                if(isAnswerRight){
                    textView.setBackgroundResource(R.drawable.textview_green_border);
                }
                else{
                    textView.setBackgroundResource(R.drawable.textview_red_border);
                }


                //Diğer soruya geçmeyi sağlayan buton görünür hale getirilir
                btnSeeNextQuestion.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    public boolean isAnswerRight(int answer){
        if (getCurrentQuestion().answer == answer){
            trueAnswers++;
            return true;
        }
        falseAnswers++;
        return false;
    }

    public void seeNextQuestion(View view){
        boolean isNextQuestionExists = goToNextQuestion();
        if(isNextQuestionExists){
            currentTextView.setBackgroundResource(R.drawable.textview_lightblack_border);
            showCurrentQuestion();
            setTextViewsClickable(true);
        }
        else{
            txtScore.setText("You have " + trueAnswers + " true and " + falseAnswers + " false answers\n" +
                    "Your score is " + trueAnswers + " / " + questionNumber);
            lyScore.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Game over", Toast.LENGTH_SHORT).show();
        }

        view.setVisibility(View.GONE);
    }

    public boolean goToNextQuestion(){
        if(currentQuestionNumber + 1 < questionList.size()){
            currentQuestionNumber++;
            return true;
        }
        return false;
    }

    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
