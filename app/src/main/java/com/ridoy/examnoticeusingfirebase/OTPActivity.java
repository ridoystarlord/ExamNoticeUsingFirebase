package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.OnOtpCompletionListener;
import com.ridoy.examnoticeusingfirebase.ModelClass.Userinformation;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityOTPBinding;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {


    ActivityOTPBinding activityOTPBinding;
    FirebaseAuth mAuth;
    String verificationId;
    ProgressDialog dialog;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOTPBinding = ActivityOTPBinding.inflate(getLayoutInflater());
        setContentView(activityOTPBinding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String phone = getIntent().getStringExtra("phone");
        activityOTPBinding.verifyPhoneLevelTV.setText(phone);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                dialog.dismiss();
                                verificationId = s;
                                //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                                activityOTPBinding.otpView.requestFocus();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        activityOTPBinding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            database.getReference().child("UsersInformation").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Userinformation userinformation = snapshot.getValue(Userinformation.class);
                                        SharedPreManager.getInstance(getApplicationContext()).userlogin(
                                                userinformation.getuId(),
                                                userinformation.getName(),
                                                userinformation.getPhoneNumber(),
                                                userinformation.getImageUrl(),
                                                userinformation.getSscpoint(),
                                                userinformation.getSscyear(),
                                                userinformation.getHscpoint(),
                                                userinformation.getHscyear(),
                                                userinformation.getLoginstatus(),
                                                userinformation.getCurrentpoint(),
                                                userinformation.getTotalpoint(),
                                                userinformation.getTotalearn()
                                        );
                                        startActivity(new Intent(OTPActivity.this, MainActivity.class));
                                        finishAffinity();
                                    } else {
                                        startActivity(new Intent(OTPActivity.this, SetupProfileActivity.class));
                                        finishAffinity();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(OTPActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}