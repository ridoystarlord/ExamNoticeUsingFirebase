package com.ridoy.examnoticeusingfirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ridoy.examnoticeusingfirebase.EligibilityActivity;
import com.ridoy.examnoticeusingfirebase.GroupChatActivity;
import com.ridoy.examnoticeusingfirebase.ModelClass.HomeCategoryModel;
import com.ridoy.examnoticeusingfirebase.PaymentActivity;
import com.ridoy.examnoticeusingfirebase.QuestionActivity;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.UniversityCategoryActivity;
import com.ridoy.examnoticeusingfirebase.databinding.HomeItemsLayoutBinding;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    Context context;
    ArrayList<HomeCategoryModel> homeCategoryModels;

    public HomeAdapter(Context context, ArrayList<HomeCategoryModel> homeCategoryModels) {
        this.context = context;
        this.homeCategoryModels = homeCategoryModels;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_items_layout,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.homeItemsLayoutBinding.catNameTV.setText(homeCategoryModels.get(position).getCatName());
        Glide.with(context).load(homeCategoryModels.get(position).getCatimageUrl()).placeholder(R.drawable.ic_item_list).into(holder.homeItemsLayoutBinding.catProfileImage);
    }

    @Override
    public int getItemCount() {
        return homeCategoryModels.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        HomeItemsLayoutBinding homeItemsLayoutBinding;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeItemsLayoutBinding=HomeItemsLayoutBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeItemsLayoutBinding.catNameTV.getText().equals("Admission Notice")){
                        Intent intent=new Intent(context, UniversityCategoryActivity.class);
                        context.startActivity(intent);
                    }
                    if (homeItemsLayoutBinding.catNameTV.getText().equals("Public Chat")){
                        context.startActivity(new Intent(context, GroupChatActivity.class));
                    }
                    if (homeItemsLayoutBinding.catNameTV.getText().equals("Check Eligibility")){
                        Intent intent=new Intent(context, EligibilityActivity.class);
                        context.startActivity(intent);
                    }
                    if (homeItemsLayoutBinding.catNameTV.getText().equals("Play Quiz")){
                        Intent intent=new Intent(context, QuestionActivity.class);
                        context.startActivity(intent);
                    }
                    if (homeItemsLayoutBinding.catNameTV.getText().equals("Payment")){
                        Intent intent=new Intent(context, PaymentActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
