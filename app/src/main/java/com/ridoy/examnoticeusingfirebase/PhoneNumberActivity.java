package com.ridoy.examnoticeusingfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityPhoneNumberBinding;

public class PhoneNumberActivity extends AppCompatActivity {

    ActivityPhoneNumberBinding activityPhoneNumberBinding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPhoneNumberBinding=ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(activityPhoneNumberBinding.getRoot());

        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();

        activityPhoneNumberBinding.sentotpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=activityPhoneNumberBinding.phoneET.getText().toString();
                if (phone.isEmpty()){
                    activityPhoneNumberBinding.phoneET.setError("Phone Number required");
                    activityPhoneNumberBinding.phoneET.requestFocus();
                    return;
                }
                if (phone.length()<11 || phone.length()>11){
                    activityPhoneNumberBinding.phoneET.setError("Invalid Phone Number");
                    activityPhoneNumberBinding.phoneET.requestFocus();
                    return;
                }
                String finalphone="+88"+phone;
                Intent intent=new Intent(PhoneNumberActivity.this,OTPActivity.class);
                intent.putExtra("phone",finalphone);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(PhoneNumberActivity.this, MainActivity.class));
            finishAffinity();
        }
    }
}