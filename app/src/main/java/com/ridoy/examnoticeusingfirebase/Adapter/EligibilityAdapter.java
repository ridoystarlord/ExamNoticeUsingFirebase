package com.ridoy.examnoticeusingfirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ridoy.examnoticeusingfirebase.ModelClass.EligibilityModel;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.UnitActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EligibilityAdapter extends RecyclerView.Adapter<EligibilityAdapter.EligibilityViewHolder>
implements Filterable {

    Context context;
    ArrayList<EligibilityModel> eligibilityUniversityList;
    ArrayList<EligibilityModel> filteruniversitynameslist;

    public EligibilityAdapter(Context context, ArrayList<EligibilityModel> eligibilityUniversityList) {
        this.context = context;
        this.eligibilityUniversityList = eligibilityUniversityList;
        this.filteruniversitynameslist=eligibilityUniversityList;
    }

    @NonNull
    @Override
    public EligibilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.university_category_layout,parent,false);
        return new EligibilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EligibilityViewHolder holder, int position) {
        holder.setdata(filteruniversitynameslist.get(position).getImageurl(),filteruniversitynameslist.get(position).getUniversityname());
    }

    @Override
    public int getItemCount() {
        return filteruniversitynameslist.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charecter=constraint.toString();
                if (charecter.isEmpty()){
                    filteruniversitynameslist=eligibilityUniversityList;

                }else {
                    ArrayList<EligibilityModel> filterlist=new ArrayList<>();
                    for (EligibilityModel model: eligibilityUniversityList){
                        if (model.getUniversityname().toLowerCase().contains(charecter.toLowerCase())){
                            filterlist.add(model);
                        }
                    }
                    filteruniversitynameslist=filterlist;
                }
                FilterResults results=new FilterResults();
                results.values=filteruniversitynameslist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteruniversitynameslist= (ArrayList<EligibilityModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class EligibilityViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView universityname;
        public EligibilityViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.university_cat_profile_image);
            universityname=itemView.findViewById(R.id.university_cat_name_TV);
        }
        private void setdata(String imageUrl, String uniname)
        {
            Glide.with(itemView.getContext()).load(imageUrl).into(circleImageView);
            universityname.setText(uniname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(), UnitActivity.class);
                    intent.putExtra("UniversityName",uniname);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
