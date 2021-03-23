package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ridoy.examnoticeusingfirebase.ModelClass.Userinformation;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;
import com.ridoy.examnoticeusingfirebase.databinding.ActivitySetupProfileBinding;

import java.util.HashMap;

public class SetupProfileActivity extends AppCompatActivity {

    ActivitySetupProfileBinding activitySetupProfileBinding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dialog;
    String name,imageURL,uid,phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySetupProfileBinding = ActivitySetupProfileBinding.inflate(getLayoutInflater());
        setContentView(activitySetupProfileBinding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Profile Updating...");


        activitySetupProfileBinding.setupprofileProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });
        activitySetupProfileBinding.gotodashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = activitySetupProfileBinding.nameET.getText().toString();
                if (name.isEmpty()) {
                    activitySetupProfileBinding.nameET.setError("Please, Enter Your Name");
                    activitySetupProfileBinding.nameET.requestFocus();
                    return;
                }

                dialog.show();
                if (selectedImage != null) {
                    StorageReference reference = storage.getReference().child("ProfilePhotos").child(auth.getUid());

                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        imageURL = uri.toString();
                                        uid = auth.getUid();
                                        phonenumber = auth.getCurrentUser().getPhoneNumber();

                                        Userinformation userinformation = new Userinformation(uid, name, phonenumber, imageURL);
                                        database.getReference().child("Users").child(uid).setValue(userinformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialog.dismiss();
                                                SharedPreManager.getInstance(getApplicationContext()).usersignup(uid,name,phonenumber,imageURL);
                                                startActivity(new Intent(SetupProfileActivity.this, MainActivity.class));
                                                finishAffinity();
                                            }
                                        });
                                    }
                                });
                            } else {
                                Toast.makeText(SetupProfileActivity.this, "Some Error Occures! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    uid = auth.getUid();
                    phonenumber = auth.getCurrentUser().getPhoneNumber();
                    imageURL="No Images Found";

                    Userinformation users = new Userinformation(uid, name, phonenumber, imageURL);
                    database.getReference().child("Users").child(uid).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            SharedPreManager.getInstance(getApplicationContext()).usersignup(uid,name,phonenumber,imageURL);
                            startActivity(new Intent(SetupProfileActivity.this, MainActivity.class));
                            finishAffinity();
                        }
                    });


                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedImage = data.getData();
        if (data != null) {
            if (selectedImage != null) {
                activitySetupProfileBinding.setupprofileProfileImage.setImageURI(selectedImage);
            }
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        database.getReference().child("UsersInformation").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    Userinformation userinformation=snapshot.getValue(Userinformation.class);
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
                            userinformation.getTotalearn(),
                            userinformation.getPaymentRequestStatus()
                    );
                    startActivity(new Intent(SetupProfileActivity.this,MainActivity.class));
                    finishAffinity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}