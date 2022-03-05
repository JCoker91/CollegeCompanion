package com.cokerj.collegecompanion.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseDetailsScreen extends AppCompatActivity {
    int courseId;
    Course current;
    Repository repo;
    List<Assessment> courseAssessments;
    TextView courseName;
    TextView courseStartDate;
    TextView courseEndDate;
    TextView instructorPhone;
    TextView instructorEmail;
    TextView instructorName;
    TextView status;
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        courseId = getIntent().getIntExtra("id", 0);
        courseAssessments = repo.getCourseAssessments(courseId);
        current = repo.getCourseById(courseId);
        termId = current.getTermId();
        courseName = findViewById(R.id.courseDetailsTitle);
        courseStartDate = findViewById(R.id.courseDetailsStartDate);
        courseEndDate = findViewById(R.id.courseDetailsEndDate);
        instructorPhone = findViewById(R.id.courseDetailsInstructorPhone);
        instructorEmail = findViewById(R.id.courseDetailsInstructorEmail);
        instructorName = findViewById(R.id.courseDetailsInstructorName);
        status = findViewById(R.id.courseDetailsStatus);
        RecyclerView recyclerView = findViewById(R.id.assessmentRView);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        courseName.setText(current.getName());
        courseStartDate.setText(current.getStartDate().format(formatter).toString());
        courseEndDate.setText(current.getEndDate().format(formatter).toString());
        instructorPhone.setText(current.getInstructorPhone());
        instructorName.setText(current.getInstructorName());
        instructorEmail.setText(current.getInstructorEmail());
        status.setText(current.getStatus());
        List<Assessment> assessments = repo.getCourseAssessments(courseId);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        adapter.setAssessments(assessments);
        adapter.setRepository(repo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        View noAssessmentsWarning = findViewById(R.id.noAssessmentsWarningView);
        if (courseAssessments.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
        } else{
            noAssessmentsWarning.setVisibility(View.INVISIBLE);
        }
    }

    public void viewAssessmentDetails(View view) {
        Intent intent = new Intent(CourseDetailsScreen.this, AssessmentDetailsScreen.class);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to delete course " + current.getName() + " and all associated assessments?")
                    .setCancelable(false)
                    .setTitle("Delete Course")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            repo.delete(current);
                            Intent intent = new Intent(CourseDetailsScreen.this, TermDetailsScreen.class);
                            intent.putExtra("id", termId);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(CourseDetailsScreen.this, TermDetailsScreen.class);
                intent.putExtra("id", termId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editCourse(View view) {
        Intent intent = new Intent(CourseDetailsScreen.this, EditCourse.class);
        intent.putExtra("courseId", current.getCourseId());
        startActivity(intent);
    }

    public void toAddAssessmentScreen(View view) {
        Intent intent = new Intent(CourseDetailsScreen.this, AddAssessment.class);
        intent.putExtra("courseId", current.getCourseId());
        startActivity(intent);
    }

    public void toCourseNotes(View view) {
        Intent intent = new Intent(CourseDetailsScreen.this, CourseNotes.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }
}