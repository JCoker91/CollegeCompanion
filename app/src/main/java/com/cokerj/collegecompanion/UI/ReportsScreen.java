package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.util.List;

public class ReportsScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private TableRow createTableRow(Course course, List<Term> terms){
        TableRow tableRow = new TableRow(this);



        TextView courseTitle = new TextView(this);

        TextView courseStatus = new TextView(this);
        TextView courseStartDate = new TextView(this);
        TextView courseEndDate = new TextView(this);


        courseTitle.setText(course.getName());
        courseStatus.setText(course.getStatus());
        courseStartDate.setText(course.getStartDate().toString());
        courseEndDate.setText(course.getEndDate().toString());

        courseTitle.setPadding(10, 10, 10, 10);
        courseTitle.setGravity(Gravity.CENTER);
        courseStatus.setPadding(10, 10, 10, 10);
        courseStatus.setGravity(Gravity.CENTER);
        courseStartDate.setPadding(10, 10, 10, 10);
        courseStartDate.setGravity(Gravity.CENTER);
        courseEndDate.setPadding(10, 10, 10, 10);
        courseEndDate.setGravity(Gravity.CENTER);

        courseTitle.setTextColor(Color.rgb(255, 255, 255));
        courseStatus.setTextColor(Color.rgb(255, 255, 255));
        courseStartDate.setTextColor(Color.rgb(255, 255, 255));
        courseEndDate.setTextColor(Color.rgb(255, 255, 255));



        tableRow.addView(courseTitle);
        for (Term term: terms){
            if (course.getTermId() == term.getTermId()){
                TextView termTitle = new TextView(this);
                termTitle.setText(term.getTermTitle());
                termTitle.setPadding(10, 10, 10, 10);
                termTitle.setGravity(Gravity.CENTER);
                termTitle.setTextColor(Color.rgb(255, 255, 255));
                tableRow.addView(termTitle);
            }
        }
//        tableRow.addView(courseStartDate);
//        tableRow.addView(courseEndDate);
        tableRow.addView(courseStatus);
        return tableRow;
    }

    public void displayAllCourses(View view){
        TableLayout courseTable = findViewById(R.id.courseTable);
        TextView emptyReportsTextView = findViewById(R.id.emptyReportsTextView);
        courseTable.removeAllViews();

        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        courseTable.addView(v);

        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        List<Term> terms = repo.getAllTerms();
        if (courses.isEmpty()) {
            emptyReportsTextView.setVisibility(View.VISIBLE);
            emptyReportsTextView.setText("You have not created any courses yet.\nThis report is empty.");
            courseTable.setVisibility(View.INVISIBLE);
        }
        else{
            emptyReportsTextView.setVisibility(View.INVISIBLE);
            courseTable.setVisibility(View.VISIBLE);
            for (Course course: courses){
                TableRow newRow = createTableRow(course, terms);
                courseTable.addView(newRow);
            }
        }
    }
}