package com.ridoy.examnoticeusingfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ridoy.examnoticeusingfirebase.Adapter.UniversityCategoryAdapter;
import com.ridoy.examnoticeusingfirebase.ModelClass.HomeCategoryModel;
import com.ridoy.examnoticeusingfirebase.databinding.ActivityUniversityCategoryBinding;

import java.util.ArrayList;

public class UniversityCategoryActivity extends AppCompatActivity {

    ActivityUniversityCategoryBinding activityUniversityCategoryBinding;

    ArrayList<HomeCategoryModel> universityCategorylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUniversityCategoryBinding=ActivityUniversityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityUniversityCategoryBinding.getRoot());

        universityCategorylist=new ArrayList<>();

        universityCategorylist.add(new HomeCategoryModel("General University"));
        universityCategorylist.add(new HomeCategoryModel("Engineering University"));
        universityCategorylist.add(new HomeCategoryModel("Agricaltural University"));
        universityCategorylist.add(new HomeCategoryModel("Science & Technology University"));
        universityCategorylist.add(new HomeCategoryModel("Medical University"));

        UniversityCategoryAdapter universityCategoryAdapter=new UniversityCategoryAdapter(this,universityCategorylist);
        activityUniversityCategoryBinding.universitycategoryRV.setAdapter(universityCategoryAdapter);
    }
}