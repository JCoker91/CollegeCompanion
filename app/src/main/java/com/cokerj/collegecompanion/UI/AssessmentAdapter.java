package com.cokerj.collegecompanion.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentTitle;
        private final TextView assessmentStartDate;
        private final TextView assessmentEndDate;
        private final TextView assessmentType;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentTitle = itemView.findViewById(R.id.assessmentTitle);
            assessmentStartDate = itemView.findViewById(R.id.assessmentStartDate);
            assessmentEndDate = itemView.findViewById(R.id.assessmentEndDate);
            assessmentType = itemView.findViewById(R.id.assessmentType);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailsScreen.class);
                    intent.putExtra("assessmentId", current.getAssessment_id());
                    context.startActivity(intent);
                }
            });
        }
    }

    private Repository mRepo;
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment current = mAssessments.get(position);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
            String startDate = current.getStartDate().format(formatter).toString();
            String endDate = current.getEndDate().format(formatter).toString();
            holder.assessmentStartDate.setText(startDate);
            holder.assessmentEndDate.setText(endDate);
            holder.assessmentTitle.setText(current.getName());
            String type = "P";
            if (current.getType().equals("Objective")){type="O";}
            holder.assessmentType.setText(type);
        }else {
            holder.assessmentTitle.setText("There is no name");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    public void setRepository(Repository repo){
        mRepo = repo;
    }

    @Override
    public int getItemCount() {
        if (mAssessments == null){
            return 0;
        } else {
            return mAssessments.size();
        }
    }
}
