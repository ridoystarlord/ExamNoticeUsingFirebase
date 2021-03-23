package com.ridoy.examnoticeusingfirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.ModelClass.HomeCategoryModel;
import com.ridoy.examnoticeusingfirebase.ModelClass.UnitModel;
import com.ridoy.examnoticeusingfirebase.PDFActivity;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.UnitActivity;
import com.ridoy.examnoticeusingfirebase.UniversityListActivity;
import com.ridoy.examnoticeusingfirebase.databinding.UniversityCategoryLayoutBinding;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UniversitycategoryViewHolder> {

    Context context;
    ArrayList<UnitModel> unitList;

    public UnitAdapter(Context context, ArrayList<UnitModel> unitList) {
        this.context = context;
        this.unitList = unitList;
    }

    @NonNull
    @Override
    public UniversitycategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.unit_layout,parent,false);
        return new UniversitycategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversitycategoryViewHolder holder, int position) {
        holder.setdata(unitList.get(position).getUnitName(),unitList.get(position).getUnitUrl());

    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class UniversitycategoryViewHolder extends RecyclerView.ViewHolder {

        TextView unitName;
        public UniversitycategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            unitName=itemView.findViewById(R.id.unit_name_TV);
        }
        private void setdata(String unit, String unitUrl)
        {
            unitName.setText(unit);
            Intent intent=new Intent(itemView.getContext(), PDFActivity.class);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (unit.equals("A")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("B")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("C")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("D")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("E")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("F")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("G")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("H")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("I")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    if (unit.equals("J")){
                        intent.putExtra("uniturl",unitUrl);
                    }
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
