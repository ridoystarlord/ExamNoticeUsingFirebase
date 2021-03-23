package com.ridoy.examnoticeusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.ridoy.examnoticeusingfirebase.databinding.ActivityFreeUserBinding;

import java.util.ArrayList;

public class FreeUserActivity extends AppCompatActivity {

    ActivityFreeUserBinding activityFreeUserBinding;
    ArrayList<EligibilityModel> universitylist;
    FirebaseDatabase database;
    EligibilityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFreeUserBinding = ActivityFreeUserBinding.inflate(getLayoutInflater());
        setContentView(activityFreeUserBinding.getRoot());

        if(SharedPreManager.getInstance(this).isLoggedIn()){
            finishAffinity();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        buildDialog(FreeUserActivity.this).show();

        database = FirebaseDatabase.getInstance();
        universitylist = new ArrayList<>();

        adapter = new EligibilityAdapter(this, universitylist);
        activityFreeUserBinding.freeuserRV.setAdapter(adapter);

        database.getReference().child("FreeUserUniversityNamesList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                universitylist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    universitylist.add(dataSnapshot.getValue(EligibilityModel.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_free_user,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_free_user_eligible_search);
        SearchView searchView= (SearchView) menuItem.getActionView();
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_free_user_addperson:
                Intent intent=new Intent(FreeUserActivity.this,PhoneNumberActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
    public void onBackPressed() {
        AlertDialog.Builder alertdialog;
        alertdialog=new AlertDialog.Builder(FreeUserActivity.this);
        alertdialog.setIcon(R.drawable.ic_alert);
        alertdialog.setTitle("Exit");
        alertdialog.setMessage("Do You Want to Exit");
        alertdialog.setCancelable(false);

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog=alertdialog.create();
        alertDialog.show();
    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_signup);
        builder.setTitle("Notice");
        builder.setMessage("Do You want to get notify in your mobile phone, when any new university notice comes ? then signup.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(FreeUserActivity.this,PhoneNumberActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        return builder;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}