package com.cokerj.collegecompanion.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cokerj.collegecompanion.DAO.CourseDAO;
import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termTitle;
        private final TextView startDate;
        private final TextView endDate;
        private final TextView courseCount;
        private TermViewHolder(View itemView){
            super(itemView);
            termTitle = itemView.findViewById(R.id.termTitle);
            startDate = itemView.findViewById(R.id.termStartDate);
            endDate = itemView.findViewById(R.id.termEndDate);
            courseCount = itemView.findViewById(R.id.courseCount);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    int courseCount = mRepo.getCourseCount(current.getTermId());
                    Intent intent = new Intent(context, TermDetailsScreen.class);
                    intent.putExtra("id", current.getTermId());
                    intent.putExtra("name", current.getTermTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("courseCount", courseCount);
                    context.startActivity(intent);
                }
            });
        }
    }
    private Repository mRepo;
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current = mTerms.get(position);
            int courseCount = mRepo.getCourseCount(current.getTermId());
            String title = current.getTermTitle();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
            String startDate = current.getStartDate().format(formatter).toString();
            String endDate = current.getEndDate().format(formatter).toString();
            holder.startDate.setText(startDate);
            holder.endDate.setText(endDate);
            holder.termTitle.setText(title);
            holder.courseCount.setText(String.valueOf(courseCount));
        }else {
            holder.termTitle.setText("There is no name");
        }
    }

    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    public void setRepository(Repository repo){
        mRepo = repo;
    }

    @Override
    public int getItemCount() {
        if (mTerms == null){
            return 0;
        } else {
            return mTerms.size();
        }
    }
}