package com.ridoy.examnoticeusingfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    private TextView score_screen_score;
    private AppCompatButton btn_done;
    int finalscore,score,currentscore,previoussocre,totalscore;
    ProgressDialog dialog;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        score= SharedPreManager.getInstance(this).getUsertotalScore();

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait, Your Score Updating");
        dialog.setCancelable(false);

        score_screen_score=findViewById(R.id.score_TV);
        btn_done=findViewById(R.id.claim_btn);

        currentscore=getIntent().getIntExtra("score",0);
        previoussocre=SharedPreManager.getInstance(this).getUsercurrentScore();
        finalscore=currentscore+previoussocre;
        totalscore=score+currentscore;

        final String scorestring=getIntent().getStringExtra("scorestring");
        score_screen_score.setText(scorestring);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> map=new HashMap<>();
                map.put("currentpoint",finalscore);
                map.put("totalpoint",totalscore);

                database.getReference().child("UsersInformation").child(auth.getUid()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreManager.getInstance(getApplicationContext()).userPointsUpdate(finalscore,totalscore);
                        Toast.makeText(ScoreActivity.this, "Score Updated", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ScoreActivity.this,MainActivity.class));
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {

        HashMap<String, Object> map=new HashMap<>();
        map.put("currentpoint",finalscore);
        map.put("totalpoint",totalscore);

        database.getReference().child("UsersInformation").child(auth.getUid()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SharedPreManager.getInstance(getApplicationContext()).userPointsUpdate(finalscore,totalscore);
                Toast.makeText(ScoreActivity.this, "Score Updated", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ScoreActivity.this,MainActivity.class));
            }
        });
    }
}