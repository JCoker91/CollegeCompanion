package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cokerj.collegecompanion.R;

public class CourseDetailsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_screen);
    }

    public void viewAssessmentDetails(View view) {
        Intent intent = new Intent(CourseDetailsScreen.this, AssessmentDetailsScreen.class);
        startActivity(intent);
    }
}