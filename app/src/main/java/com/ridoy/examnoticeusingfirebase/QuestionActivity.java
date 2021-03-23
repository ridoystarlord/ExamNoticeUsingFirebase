package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ridoy.examnoticeusingfirebase.ModelClass.Question;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView Question,qcount,timer;
    private Button option1,option2,option3,option4;
    private List<Question> questionsList;
    private CountDownTimer countDownTimer;
    private int score;
    private FirebaseFirestore firebaseFirestore;
    int questionnumber=0;
    private ProgressDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        getSupportActionBar().hide();

        score=0;

        timer=findViewById(R.id.countdowntextViewid);
        qcount=findViewById(R.id.noquestextViewid);
        Question=findViewById(R.id.questiontextViewid);

        option1=findViewById(R.id.button1id);
        option2=findViewById(R.id.button2id);
        option3=findViewById(R.id.button3id);
        option4=findViewById(R.id.button4id);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);


        loadingdialog=new ProgressDialog(this);
        loadingdialog.setCancelable(false);
        loadingdialog.setMessage("Please Wait, We are setting the Questions for You");
        loadingdialog.show();


        firebaseFirestore=FirebaseFirestore.getInstance();

        getQuestionList();
    }

    private void getQuestionList() {
        questionsList=new ArrayList<>();

        firebaseFirestore.collection("QuizQuestions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful())
                {
                    QuerySnapshot querySnapshot=task.getResult();

                    for (QueryDocumentSnapshot doc : querySnapshot){
                        questionsList.add(new Question(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                        ));
                    }
                    setQuestion();
                }
                else {

                    Toast.makeText(QuestionActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }

                loadingdialog.cancel();

            }
        });
    }

    private void setQuestion() {
        timer.setText(String.valueOf(10));

        Question.setText(questionsList.get(0).getQuestion());
        option1.setText(questionsList.get(0).getOptionA());
        option2.setText(questionsList.get(0).getOptionB());
        option3.setText(questionsList.get(0).getOptionC());
        option4.setText(questionsList.get(0).getOptionD());

        qcount.setText(String.valueOf(1)+"/"+String.valueOf(questionsList.size()));

        startTimer();
        questionnumber=0;
    }

    private void startTimer() {
        countDownTimer= new CountDownTimer(11000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (millisUntilFinished<10000) {
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {
                changeQuestion();

            }
        };
        countDownTimer.start();
    }

    private void changeQuestion() {
        if (questionnumber<questionsList.size()-1){

            questionnumber++;

            playAnim(Question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            qcount.setText(String.valueOf(questionnumber+1)+"/"+questionsList.size());

            timer.setText(String.valueOf(10));
            startTimer();

        }
        else {
            //display the score
            Intent intent=new Intent(QuestionActivity.this,ScoreActivity.class);
            intent.putExtra("score",score);
            intent.putExtra("scorestring",String.valueOf(score+"/"+String.valueOf(questionsList.size())));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private void playAnim(final View view, final int value, final int viewno) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(1000)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value==0){

                            switch (viewno){
                                case 0:
                                    ((TextView)view).setText(questionsList.get(questionnumber).getQuestion());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionsList.get(questionnumber).getOptionB());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionsList.get(questionnumber).getOptionA());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionsList.get(questionnumber).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionsList.get(questionnumber).getOptionD());
                                    break;
                                default:
                            }

                            if (viewno!=0){

                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1FA2FF")));

                            }

                            playAnim(view,1,viewno);

                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onClick(View v) {

        int selectedoption=0;

        switch (v.getId())
        {
            case R.id.button1id:
                selectedoption=1;
                break;
            case R.id.button2id:
                selectedoption=2;
                break;
            case R.id.button3id:
                selectedoption=3;
                break;
            case R.id.button4id:
                selectedoption=4;
                break;
            default:
        }

        countDownTimer.cancel();
        checkAnswer(selectedoption,v);
        
    }

    private void checkAnswer(int selectedoption, View v) {
        if (selectedoption==questionsList.get(questionnumber).getCorrectAnswer()){
            //Right answer
            ((Button)v).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;

        }else {

            //wrong Answer

            ((Button)v).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (questionsList.get(questionnumber).getCorrectAnswer()){
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                default:
            }

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },1000);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }
}