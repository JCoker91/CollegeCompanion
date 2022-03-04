package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;

public class CourseDetailsScreen extends AppCompatActivity {
    int courseId;
    Course current;
    Repository repo;
    TextView courseName;
    TextView courseStartDate;
    TextView courseEndDate;
    TextView instructorPhone;
    TextView instructorEmail;
    TextView instructorName;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_screen);
        repo = new Repository(getApplication());
        courseId = getIntent().getIntExtra("id", 0);
        current = repo.getCourseById(courseId);

        courseName = findViewById(R.id.courseDetailsTitle);
        courseStartDate = findViewById(R.id.courseDetailsStartDate);
        courseEndDate = findViewById(R.id.courseDetailsEndDate);
        instructorPhone = findViewById(R.id.courseDetailsInstructorPhone);
        instructorEmail = findViewById(R.id.courseDetailsInstructorEmail);
        instructorName = findViewById(R.id.courseDetailsInstructorName);
        status = findViewById(R.id.courseDetailsStatus);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        courseName.setText(current.getName());
        courseStartDate.setText(current.getStartDate().format(formatter).toString());
        courseEndDate.setText(current.getEndDate().format(formatter).toString());
        instructorPhone.setText(current.getInstructorPhone());
        instructorName.setText(current.getInstructorName());
        instructorEmail.setText(current.getInstructorEmail());
        status.setText(current.getStatus());
    }

    public void viewAssessmentDetails(View view) {
        Intent intent = new Intent(CourseDetailsScreen.this, AssessmentDetailsScreen.class);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
//        if(courseCount == 0) {
            builder.setMessage("Would you like to delete course " + current.getName() + "?")
                    .setCancelable(false)
                    .setTitle("Delete Course")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            repo.delete(current);
                            Intent intent = new Intent(CourseDetailsScreen.this, TermDetailsScreen.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });
//        } else {
//            builder.setMessage("Term " + current.getTermTitle() + " has " + String.valueOf(courseCount) + " courses assigned to it. Deleting the term will also delete these courses. Are you sure?")
//                    .setCancelable(false)
//                    .setTitle("WARNING")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            repo.deleteCoursesByTermId(current.getTermId());
//                            repo.delete(current);
//                            Intent intent = new Intent(TermDetailsScreen.this, HomeScreen.class);
//                            startActivity(intent);
//                        }
//                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface dialog, int id){
//                    dialog.cancel();
//                }
//            });
//        }
        AlertDialog alert = builder.create();
        alert.show();
    }
}