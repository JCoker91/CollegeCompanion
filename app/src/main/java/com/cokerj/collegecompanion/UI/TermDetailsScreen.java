package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cokerj.collegecompanion.R;

public class TermDetailsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details_screen);
    }

    public void addCourse(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, AddCourse.class);
        startActivity(intent);
    }

    public void viewCourseDetails(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, CourseDetailsScreen.class);
        startActivity(intent);
    }
}