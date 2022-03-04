package com.cokerj.collegecompanion.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseTitleView;
        private final TextView courseStartDateView;
        private final TextView courseEndDateView;
        private final TextView courseStatusView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseTitleView = itemView.findViewById(R.id.courseListItemTitle);
            courseStartDateView = itemView.findViewById(R.id.courseListItemStartDate);
            courseEndDateView = itemView.findViewById(R.id.courseListItemEndDate);
            courseStatusView = itemView.findViewById(R.id.courseListItemStatus);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    int courseCount = mRepo.getCourseCount(current.getTermId());
                    Intent intent = new Intent(context, CourseDetailsScreen.class);
                    intent.putExtra("id", current.getCourseId());
                    intent.putExtra("courseCount", courseCount);
                    context.startActivity(intent);
                }
            });
        }
    }

    private Repository mRepo;
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current = mCourses.get(position);
            String courseStatus = current.getStatus();
            String title = current.getName();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
            String startDate = current.getStartDate().format(formatter).toString();
            String endDate = current.getEndDate().format(formatter).toString();
            holder.courseStartDateView.setText(startDate);
            holder.courseEndDateView.setText(endDate);
            holder.courseTitleView.setText(title);
            holder.courseStatusView.setText(courseStatus);
            Drawable background = context.getResources().getDrawable(R.drawable.course_status_in_progress);
            if (courseStatus.equals("In Progress")){background = context.getResources().getDrawable(R.drawable.course_status_in_progress);}
            if (courseStatus.equals("Completed")){background = context.getResources().getDrawable(R.drawable.course_status_completed);}
            if (courseStatus.equals("Dropped")){background = context.getResources().getDrawable(R.drawable.course_status_dropped);}
            if (courseStatus.equals("Plan to take")){background = context.getResources().getDrawable(R.drawable.course_status_plan_to_take);}
            holder.courseStatusView.setBackground(background);
            holder.courseStatusView.setPadding(10,5,10,5);
            holder.courseStatusView.setHeight(75);
            holder.courseStatusView.setWidth(250);
        }else {
            holder.courseTitleView.setText("There is no name");
        }
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    public void setRepository(Repository repo){
        mRepo = repo;
    }

    @Override
    public int getItemCount() {
        if (mCourses == null){
            return 0;
        } else {
            return mCourses.size();
        }
    }
}
