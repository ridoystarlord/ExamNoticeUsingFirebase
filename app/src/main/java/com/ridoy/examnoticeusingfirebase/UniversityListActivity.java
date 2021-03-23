package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.Adapter.UniversityListAdapter;
import com.ridoy.examnoticeusingfirebase.ModelClass.HomeCategoryModel;
import com.ridoy.examnoticeusingfirebase.ModelClass.UniversityNamesModel;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityUniversityListBinding;

import java.util.ArrayList;

public class UniversityListActivity extends AppCompatActivity {

    ActivityUniversityListBinding activityUniversityListBinding;
    ArrayList<UniversityNamesModel> universitylist;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUniversityListBinding = ActivityUniversityListBinding.inflate(getLayoutInflater());
        setContentView(activityUniversityListBinding.getRoot());

        database = FirebaseDatabase.getInstance();
        universitylist = new ArrayList<>();

        String university_category = getIntent().getStringExtra("unicatname");

        UniversityListAdapter universityListAdapter = new UniversityListAdapter(this, universitylist);
        activityUniversityListBinding.universitylistRV.setAdapter(universityListAdapter);

        database.getReference().child("UniversityCategory").child(university_category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        universitylist.clear();
                        if (!snapshot.exists()) {
                            buildDialog(UniversityListActivity.this).show();
                        } else {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                universitylist.add(dataSnapshot.getValue(UniversityNamesModel.class));
                            }
                            universityListAdapter.notifyDataSetChanged();
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

            }
        });

        return builder;
    }
}