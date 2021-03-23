package com.ridoy.examnoticeusingfirebase.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridoy.examnoticeusingfirebase.ModelClass.HomeCategoryModel;
import com.ridoy.examnoticeusingfirebase.ModelClass.UniversityNamesModel;
import com.ridoy.examnoticeusingfirebase.PDFActivity;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.UnitActivity;
import com.ridoy.examnoticeusingfirebase.UniversityListActivity;
import com.ridoy.examnoticeusingfirebase.databinding.UniversityCategoryLayoutBinding;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UniversityListAdapter extends RecyclerView.Adapter<UniversityListAdapter.UniversitycategoryViewHolder> {

    Context context;
    ArrayList<UniversityNamesModel> universityList;

    public UniversityListAdapter(Context context, ArrayList<UniversityNamesModel> universityList) {
        this.context = context;
        this.universityList = universityList;
    }

    @NonNull
    @Override
    public UniversitycategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.university_category_layout, parent, false);
        return new UniversitycategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversitycategoryViewHolder holder, int position) {
        holder.setdata(universityList.get(position).getUniversityName(),
                universityList.get(position).getUniversityImageUrl(),
                universityList.get(position).getUniversityStaus()
        );
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }

    public class UniversitycategoryViewHolder extends RecyclerView.ViewHolder {

        CircleImageView universityimage;
        TextView universityName;

        public UniversitycategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            universityimage = itemView.findViewById(R.id.university_cat_profile_image);
            universityName = itemView.findViewById(R.id.university_cat_name_TV);
        }

        private void setdata(String universityname, String imageUrl, String universityStaus) {
            Glide.with(itemView.getContext()).load(imageUrl).into(universityimage);
            universityName.setText(universityname);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (universityStaus.equals("2")) {
                        database.getReference().child("NoUnitPDFUrl").child(universityname)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (!snapshot.exists()) {
                                            buildDialog(context).show();
                                        } else {
                                            String url = snapshot.getValue(String.class);
                                            Intent intent = new Intent(context, PDFActivity.class);
                                            intent.putExtra("uniturl", url);
                                            itemView.getContext().startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(context, "Some Error Occurs: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else if (universityStaus.equals("3")) {
                        buildDialog(context).show();
                    } else {
                        Intent intent = new Intent(itemView.getContext(), UnitActivity.class);
                        intent.putExtra("UniversityName", universityname);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });
        }
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
