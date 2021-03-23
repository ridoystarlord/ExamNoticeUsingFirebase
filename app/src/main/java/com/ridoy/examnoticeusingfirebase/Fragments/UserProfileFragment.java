package com.ridoy.examnoticeusingfirebase.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.ModelClass.Userinformation;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.SharedPrefManager.SharedPreManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends Fragment {



    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private EditText username,sscpoint_ET,sscyear_ET,hscpoint_ET,hscyear_ET;
    private TextView userphone,currentpoint,totalpoint,totalearn;
    private Button edit,save;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    CircleImageView imageView;

    int check_login_status=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_user_profile, container, false);

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        username=v.findViewById(R.id.username_ET);
        sscpoint_ET=v.findViewById(R.id.sscpoint_ET);
        sscyear_ET=v.findViewById(R.id.sscyear_ET);
        hscpoint_ET=v.findViewById(R.id.hscpoint_ET);
        hscyear_ET=v.findViewById(R.id.hscyear_ET);
        userphone=v.findViewById(R.id.phone_TV);
        edit=v.findViewById(R.id.editprofile_btn);
        save=v.findViewById(R.id.saveprofile_btn);
        imageView=v.findViewById(R.id.profile_image);

        currentpoint=v.findViewById(R.id.currentpoint_TV);
        totalpoint=v.findViewById(R.id.totalpoint_TV);
        totalearn=v.findViewById(R.id.totalearn_TV);

        username.setFocusableInTouchMode(false);
        username.setFocusable(false);
        sscpoint_ET.setFocusableInTouchMode(false);
        sscpoint_ET.setFocusable(false);
        sscyear_ET.setFocusableInTouchMode(false);
        sscyear_ET.setFocusable(false);
        hscpoint_ET.setFocusableInTouchMode(false);
        hscpoint_ET.setFocusable(false);
        hscyear_ET.setFocusableInTouchMode(false);
        hscyear_ET.setFocusable(false);


        Glide.with(getContext()).load(SharedPreManager.getInstance(getContext()).getUserImageUrl()).placeholder(R.drawable.ic_man).into(imageView);
        userphone.setText(SharedPreManager.getInstance(getContext()).getUserphone());
        username.setText(SharedPreManager.getInstance(getContext()).getUsername());
        sscpoint_ET.setText(SharedPreManager.getInstance(getContext()).getUsersscpoint());
        sscyear_ET.setText(SharedPreManager.getInstance(getContext()).getUsersscyear());
        hscpoint_ET.setText(SharedPreManager.getInstance(getContext()).getUserhscpoint());
        hscyear_ET.setText(SharedPreManager.getInstance(getContext()).getUserhscyear());

        currentpoint.setText(String.valueOf(SharedPreManager.getInstance(getContext()).getUsercurrentScore()));
        totalpoint.setText(String.valueOf(SharedPreManager.getInstance(getContext()).getUsertotalScore()));
        totalearn.setText(String.valueOf(SharedPreManager.getInstance(getContext()).getUsertotalEarn()));


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

                username.setFocusableInTouchMode(true);
                username.setFocusable(true);
                username.requestFocus();
                sscpoint_ET.setFocusableInTouchMode(true);
                sscpoint_ET.setFocusable(true);
                sscyear_ET.setFocusableInTouchMode(true);
                sscyear_ET.setFocusable(true);
                hscpoint_ET.setFocusableInTouchMode(true);
                hscpoint_ET.setFocusable(true);
                hscyear_ET.setFocusableInTouchMode(true);
                hscyear_ET.setFocusable(true);

                InputMethodManager inputMethodManager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(username,InputMethodManager.SHOW_IMPLICIT);
                inputMethodManager.showSoftInput(sscpoint_ET,InputMethodManager.SHOW_IMPLICIT);
                inputMethodManager.showSoftInput(sscyear_ET,InputMethodManager.SHOW_IMPLICIT);
                inputMethodManager.showSoftInput(hscpoint_ET,InputMethodManager.SHOW_IMPLICIT);
                inputMethodManager.showSoftInput(hscyear_ET,InputMethodManager.SHOW_IMPLICIT);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                saveuserdetails();
            }
        });
        return v;
    }

    private void saveuserdetails() {
        String name=username.getText().toString();
        String sscpoint=sscpoint_ET.getText().toString();
        String sscyear=sscyear_ET.getText().toString();
        String hscpoint=hscpoint_ET.getText().toString();
        String hscyear=hscyear_ET.getText().toString();

        if (name.isEmpty()){
            username.setError("Name Required");
            username.requestFocus();
            return;
        }

        edit.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);

        check_login_status++;

        username.setFocusableInTouchMode(false);
        username.setFocusable(false);
        sscpoint_ET.setFocusableInTouchMode(true);
        sscpoint_ET.setFocusable(true);
        sscyear_ET.setFocusableInTouchMode(true);
        sscyear_ET.setFocusable(true);
        hscpoint_ET.setFocusableInTouchMode(true);
        hscpoint_ET.setFocusable(true);
        hscyear_ET.setFocusableInTouchMode(true);
        hscyear_ET.setFocusable(true);

        if (sscpoint.isEmpty()){
            sscpoint="N/A";
        }
        if (hscpoint.isEmpty()){
            hscpoint="N/A";
        }
        if (sscyear.isEmpty()){
            sscyear="N/A";
        }
        if (hscyear.isEmpty()){
            hscyear="N/A";
        }

        if (SharedPreManager.getInstance(getContext()).getUserloginstatus().equals("0")){

            HashMap<String, Object> map=new HashMap<>();
            map.put("name",name);
            map.put("sscpoint",sscpoint);
            map.put("sscyear",sscyear);
            map.put("hscpoint",hscpoint);
            map.put("hscyear",hscyear);
            map.put("loginstatus",String.valueOf(check_login_status));
            map.put("currentpoint",0);
            map.put("totalpoint",0);
            map.put("totalearn",0);
            map.put("imageUrl",SharedPreManager.getInstance(getContext()).getUserImageUrl());
            map.put("uId",SharedPreManager.getInstance(getContext()).getUserUid());
            map.put("phoneNumber",SharedPreManager.getInstance(getContext()).getUserphone());
            map.put("paymentRequestStatus","0");

            String finalSscpoint = sscpoint;
            String finalSscyear = sscyear;
            String finalHscpoint = hscpoint;
            String finalHscyear = hscyear;

            database.getReference().child("UsersInformation").child(mAuth.getUid())
                    .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap<String, Object> hashMap=new HashMap<>();
                    hashMap.put("name",name);

                    database.getReference().child("Users").child(mAuth.getUid())
                            .updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            SharedPreManager.getInstance(getContext()).userInformationUpdate(
                                    name, finalSscpoint, finalSscyear, finalHscpoint, finalHscyear,
                                    String.valueOf(check_login_status),0,0,0);
                            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }else {
            HashMap<String, Object> map=new HashMap<>();
            map.put("name",name);
            map.put("sscpoint",sscpoint);
            map.put("sscyear",sscyear);
            map.put("hscpoint",hscpoint);
            map.put("hscyear",hscyear);

            String finalSscpoint = sscpoint;
            String finalSscyear = sscyear;
            String finalHscpoint = hscpoint;
            String finalHscyear = hscyear;

            database.getReference().child("UsersInformation").child(mAuth.getUid())
                    .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap<String, Object> hashMap=new HashMap<>();
                    hashMap.put("name",name);

                    database.getReference().child("Users").child(mAuth.getUid())
                            .updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            SharedPreManager.getInstance(getContext()).userInformationUpdate(
                                    name, finalSscpoint, finalSscyear, finalHscpoint, finalHscyear);
                            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}