package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.ModelClass.PaymentRequestModel;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityPaymentBinding;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding activityPaymentBinding;
    String paymentMethod;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    String paymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentBinding.getRoot());

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(PaymentActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Request Sending...");

        database.getReference().child("UsersInformation").child(mAuth.getUid())
                .child("paymentRequestStatus")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            updatebuildDialog(PaymentActivity.this).show();
                        } else {
                            paymentStatus = snapshot.getValue(String.class);
                            if (paymentStatus.equals("1")) {
                                buildDialog(PaymentActivity.this).show();
                            } else {
                                activityPaymentBinding.balanceTV.setText(String.valueOf(SharedPreManager.getInstance(PaymentActivity.this).getUsercurrentScore()));

                                activityPaymentBinding.paymentRequestBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int selectedMethod = activityPaymentBinding.paymentMethod.getCheckedRadioButtonId();

                                        String phoneNumber = activityPaymentBinding.phoneNumberET.getText().toString();
                                        String paymentPoint = activityPaymentBinding.requestPointET.getText().toString();

                                        if (selectedMethod < 0) {
                                            activityPaymentBinding.bkashradioButton.setError("Please Select a method");
                                            activityPaymentBinding.rocketradioButton.setError("Please Select a method");
                                            return;
                                        }
                                        if (phoneNumber.isEmpty()) {
                                            activityPaymentBinding.phoneNumberET.setError("Plz, Enter Your Cashout Number");
                                            activityPaymentBinding.phoneNumberET.requestFocus();
                                            return;
                                        }
                                        if (paymentPoint.isEmpty()) {
                                            activityPaymentBinding.requestPointET.setError("Plz, Enter Your Cashout Point");
                                            activityPaymentBinding.requestPointET.requestFocus();
                                            return;
                                        }
                                        if (Integer.parseInt(paymentPoint) > SharedPreManager.getInstance(PaymentActivity.this).getUsercurrentScore()) {
                                            activityPaymentBinding.requestPointET.setError("You Dont have that much point. Plz, Enter Your valid Cashout Point");
                                            activityPaymentBinding.requestPointET.requestFocus();
                                            return;
                                        }

                                        if (selectedMethod == R.id.bkashradioButton) {
                                            paymentMethod = activityPaymentBinding.bkashradioButton.getText().toString();
                                        }
                                        if (selectedMethod == R.id.rocketradioButton) {
                                            paymentMethod = activityPaymentBinding.rocketradioButton.getText().toString();
                                        }
                                        activityPaymentBinding.bkashradioButton.setError(null);
                                        activityPaymentBinding.rocketradioButton.setError(null);
                                        PaymentRequestModel paymentRequestModel = new PaymentRequestModel(paymentMethod, phoneNumber, paymentPoint, mAuth.getUid());
                                        dialog.show();
                                        if (SharedPreManager.getInstance(PaymentActivity.this).getUsercurrentScore() >= 1) {
                                            database.getReference().child("PaymentRequest").child(mAuth.getUid())
                                                    .setValue(paymentRequestModel)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                activityPaymentBinding.paymentRequestBtn.setVisibility(View.GONE);
                                                                activityPaymentBinding.paymentMethod.clearCheck();
                                                                activityPaymentBinding.phoneNumberET.setText("");
                                                                activityPaymentBinding.requestPointET.setText("");
                                                                updateUserbalance(paymentPoint);
                                                            } else {
                                                                Toast.makeText(PaymentActivity.this, "Some Error Occurs", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(PaymentActivity.this, "You need more coins to get withdraw.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void updateUserbalance(String point) {
        int cashoutpoint = Integer.parseInt(point);
        int currentpoint = SharedPreManager.getInstance(this).getUsercurrentScore();
        int finalpoint = currentpoint - cashoutpoint;

        HashMap map = new HashMap();
        map.put("currentpoint", finalpoint);
        map.put("paymentRequestStatus", "1");

        database.getReference().child("UsersInformation").child(mAuth.getUid())
                .updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                SharedPreManager.getInstance(PaymentActivity.this).userPointsUpdate(finalpoint);
                dialog.dismiss();
                activityPaymentBinding.balanceTV.setText(String.valueOf(finalpoint));
                Toast.makeText(PaymentActivity.this, "Payment Request Sent Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_alert);
        builder.setTitle("Not Available");
        builder.setMessage("You have already sent a Payment Request. Payment Request will be available after Previous Request Processing...");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

    public AlertDialog.Builder updatebuildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_update);
        builder.setTitle("Notice");
        builder.setMessage("Please Update Your Profile first to check Payment Methods...");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }
}