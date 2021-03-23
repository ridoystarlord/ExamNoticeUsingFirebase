package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.Adapter.EligibilityAdapter;
import com.ridoy.examnoticeusingfirebase.ModelClass.EligibilityModel;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityEligibilityBinding;

import java.util.ArrayList;

public class EligibilityActivity extends AppCompatActivity {

    ActivityEligibilityBinding activityEligibilityBinding;
    ArrayList<EligibilityModel> eligibilityuniversitylist;
    FirebaseDatabase database;
    Float userSscpoint, userHscpoint;
    int userSscyear, userHscyear;
    EligibilityAdapter adapter;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEligibilityBinding = ActivityEligibilityBinding.inflate(getLayoutInflater());
        setContentView(activityEligibilityBinding.getRoot());

        if (SharedPreManager.getInstance(this).getUserloginstatus().equals("0")) {
            eligiblebuildDialog(EligibilityActivity.this).show();
        } else {

            dialog = new ProgressDialog(this);
            dialog.setMessage("Please Wait...\nWe are Searching...");
            dialog.setCancelable(false);

            userSscpoint = Float.valueOf(SharedPreManager.getInstance(this).getUsersscpoint());
            userHscpoint = Float.valueOf(SharedPreManager.getInstance(this).getUserhscpoint());

            userSscyear = Integer.valueOf(SharedPreManager.getInstance(this).getUsersscyear());
            userHscyear = Integer.valueOf(SharedPreManager.getInstance(this).getUserhscyear());

            database = FirebaseDatabase.getInstance();
            eligibilityuniversitylist = new ArrayList<>();

            adapter = new EligibilityAdapter(this, eligibilityuniversitylist);
            activityEligibilityBinding.eligibilityRV.setAdapter(adapter);

            dialog.show();
            database.getReference().child("FreeUserUniversityNamesList").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    eligibilityuniversitylist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        EligibilityModel model = dataSnapshot.getValue(EligibilityModel.class);

                        int universitysscyear = model.getR_sscyear();
                        int universityhscyear = model.getR_hscyear();
                        Float universitysscpoint = model.getR_sscpoint();
                        Float universityhscpoint = model.getR_hscpoint();

                        if ((userHscyear == universityhscyear && universitysscyear == universitysscyear) ||
                                (userHscyear == (universityhscyear - 1) && userSscyear == (universitysscyear - 1))) {
                            if (userSscpoint >= universitysscpoint && userHscpoint >= universityhscpoint) {
                                eligibilityuniversitylist.add(dataSnapshot.getValue(EligibilityModel.class));
                            }

                        }
                    }
                    if (eligibilityuniversitylist.isEmpty()) {
                        buildDialog(EligibilityActivity.this).show();
                    } else {

                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eligible, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_eligible_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_alert);
        builder.setTitle("Not Eligible");
        builder.setMessage("You are not Eligible for any university");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

    public AlertDialog.Builder eligiblebuildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_update);
        builder.setTitle("Notice");
        builder.setMessage("Please Update Your Profile first to check your Eligible University List...");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }
}