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

    private TableRow createRowHeader(){
        TableRow tableRow = new TableRow(this);
        TextView courseHeader = new TextView(this);
        TextView termHeader = new TextView(this);
        TextView statusHeader = new TextView(this);

        courseHeader.setText("Course");
        termHeader.setText("Term");
        statusHeader.setText("Status");

        courseHeader.setPadding(10, 10, 10, 30);
        courseHeader.setGravity(Gravity.CENTER);
        courseHeader.setTextColor(Color.rgb(255, 255, 255));
        termHeader.setPadding(10, 10, 10, 30);
        termHeader.setGravity(Gravity.CENTER);
        termHeader.setTextColor(Color.rgb(255, 255, 255));
        statusHeader.setPadding(10, 10, 10, 30);
        statusHeader.setGravity(Gravity.CENTER);
        statusHeader.setTextColor(Color.rgb(255, 255, 255));

        courseHeader.setTextSize(20);
        termHeader.setTextSize(20);
        statusHeader.setTextSize(20);



        tableRow.addView(courseHeader);
        tableRow.addView(termHeader);
        tableRow.addView(statusHeader);
        return tableRow;
    }

    public void displayAllCourses(View view){
        TableLayout courseTable = findViewById(R.id.courseTable);
        TextView emptyReportsTextView = findViewById(R.id.emptyReportsTextView);
        TextView reportTitle = findViewById(R.id.reportTitle);
        TextView dateText = findViewById(R.id.dateText);
        courseTable.removeAllViews();
        courseTable.addView(createRowHeader());

        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        courseTable.addView(v);

        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        List<Term> terms = repo.getAllTerms();
        reportTitle.setText("All Courses");
        if (courses.isEmpty()) {
            dateText.setText("");
            emptyReportsTextView.setVisibility(View.VISIBLE);
            emptyReportsTextView.setText("You have not created any courses yet.\nThis report is empty.");
            courseTable.setVisibility(View.INVISIBLE);
        }
        else{

            java.util.Date date = new java.util.Date();
            String dateTime = date.toString();
            dateText.setText(dateTime);
            System.out.println(date);
            emptyReportsTextView.setVisibility(View.INVISIBLE);
            courseTable.setVisibility(View.VISIBLE);
            for (Course course: courses){
                TableRow newRow = createTableRow(course, terms);
                courseTable.addView(newRow);
            }
        }
    }

    public void displayCompletedCourses(View view){
        TableLayout courseTable = findViewById(R.id.courseTable);
        TextView emptyReportsTextView = findViewById(R.id.emptyReportsTextView);
        TextView reportTitle = findViewById(R.id.reportTitle);
        TextView dateText = findViewById(R.id.dateText);
        courseTable.removeAllViews();
        courseTable.addView(createRowHeader());

        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        courseTable.addView(v);

        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCompletedCourses();
        List<Term> terms = repo.getAllTerms();
        reportTitle.setText("Completed Courses");
        if (courses.isEmpty()) {
            dateText.setText("");
            emptyReportsTextView.setVisibility(View.VISIBLE);
            emptyReportsTextView.setText("You do not have any completed courses yet.\nThis report is empty.");
            courseTable.setVisibility(View.INVISIBLE);
        }
        else{

            java.util.Date date = new java.util.Date();
            String dateTime = date.toString();
            dateText.setText(dateTime);
            System.out.println(date);
            emptyReportsTextView.setVisibility(View.INVISIBLE);
            courseTable.setVisibility(View.VISIBLE);
            for (Course course: courses){
                TableRow newRow = createTableRow(course, terms);
                courseTable.addView(newRow);
            }
        }
    }

    public void displayInProgressCourses(View view){
        TableLayout courseTable = findViewById(R.id.courseTable);
        TextView emptyReportsTextView = findViewById(R.id.emptyReportsTextView);
        TextView reportTitle = findViewById(R.id.reportTitle);
        TextView dateText = findViewById(R.id.dateText);
        courseTable.removeAllViews();
        courseTable.addView(createRowHeader());

        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        courseTable.addView(v);

        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllInProgressCourses();
        List<Term> terms = repo.getAllTerms();
        reportTitle.setText("Current Courses");
        if (courses.isEmpty()) {
            dateText.setText("");
            emptyReportsTextView.setVisibility(View.VISIBLE);
            emptyReportsTextView.setText("You do not have any current courses.\nThis report is empty.");
            courseTable.setVisibility(View.INVISIBLE);
        }
        else{

            java.util.Date date = new java.util.Date();
            String dateTime = date.toString();
            dateText.setText(dateTime);
            System.out.println(date);
            emptyReportsTextView.setVisibility(View.INVISIBLE);
            courseTable.setVisibility(View.VISIBLE);
            for (Course course: courses){
                TableRow newRow = createTableRow(course, terms);
                courseTable.addView(newRow);
            }
        }
    }

}