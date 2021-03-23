package com.ridoy.examnoticeusingfirebase.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.Adapter.LeaderboardAdapter;
import com.ridoy.examnoticeusingfirebase.ModelClass.LeaderboardModel;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.databinding.FragmentLeaderboardBinding;

import java.util.ArrayList;

public class LeaderboardFragment extends Fragment {

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentLeaderboardBinding fragmentLeaderboardBinding;
    DatabaseReference reference;
    ArrayList<LeaderboardModel> leaderboardModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLeaderboardBinding=FragmentLeaderboardBinding.inflate(inflater, container, false);
        leaderboardModels=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference().child("UsersInformation");

        LeaderboardAdapter leaderboardAdapter=new LeaderboardAdapter(getActivity(),leaderboardModels);
        fragmentLeaderboardBinding.leaderboardRV.setAdapter(leaderboardAdapter);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        fragmentLeaderboardBinding.leaderboardRV.setLayoutManager(manager);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        reference.orderByChild("currentpoint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leaderboardModels.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        LeaderboardModel leaderboardModel=dataSnapshot.getValue(LeaderboardModel.class);
                        leaderboardModels.add(leaderboardModel);
                    }
                    leaderboardAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return fragmentLeaderboardBinding.getRoot();

    }
}