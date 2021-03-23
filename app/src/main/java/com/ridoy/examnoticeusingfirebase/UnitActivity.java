package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.Adapter.UnitAdapter;
import com.ridoy.examnoticeusingfirebase.ModelClass.UnitModel;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityUnitBinding;

import java.util.ArrayList;

public class UnitActivity extends AppCompatActivity {

    ActivityUnitBinding activityUnitBinding;
    ArrayList<UnitModel> unitlist;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUnitBinding = ActivityUnitBinding.inflate(getLayoutInflater());
        setContentView(activityUnitBinding.getRoot());

        String universityName = getIntent().getStringExtra("UniversityName");

        database = FirebaseDatabase.getInstance();
        unitlist = new ArrayList<>();

        UnitAdapter unitAdapter = new UnitAdapter(this, unitlist);
        activityUnitBinding.unitlistRV.setAdapter(unitAdapter);

        database.getReference().child("UniversityUnitList").child(universityName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitlist.clear();
                if (!snapshot.exists()){
                    buildDialog(UnitActivity.this).show();
                }else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        unitlist.add(dataSnapshot.getValue(UnitModel.class));
                    }
                    unitAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_alert);
        builder.setTitle("Notice");
        builder.setMessage("Not Published Yet");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        return builder;
    }
}