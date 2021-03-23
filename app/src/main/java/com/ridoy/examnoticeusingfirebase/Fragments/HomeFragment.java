package com.ridoy.examnoticeusingfirebase.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.Adapter.HomeAdapter;
import com.ridoy.examnoticeusingfirebase.MainActivity;
import com.ridoy.examnoticeusingfirebase.ModelClass.HomeCategoryModel;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ArrayList<HomeCategoryModel> homelist;
    FragmentHomeBinding fragmentHomeBinding;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding=FragmentHomeBinding.inflate(inflater,container,false);

        dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.show();

        homelist=new ArrayList<>();
        database=FirebaseDatabase.getInstance();

        HomeAdapter homeAdapter=new HomeAdapter(getActivity(),homelist);
        fragmentHomeBinding.homerv.setAdapter(homeAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        fragmentHomeBinding.homerv.setLayoutManager(gridLayoutManager);


        database.getReference().child("HomeCategoryList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homelist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    homelist.add(dataSnapshot.getValue(HomeCategoryModel.class));
                }
                homeAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return fragmentHomeBinding.getRoot();
    }
}