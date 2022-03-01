package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

public class TermDetailsScreen extends AppCompatActivity {
    int termId;
    Term current;
    Repository repo;
    private TextView termDetailsTitle;
    private TextView termDetailsStartDate;
    private TextView termDetailsEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details_screen);
        termDetailsTitle = findViewById(R.id.termDetailsTitle);
        termDetailsStartDate = findViewById(R.id.termDetailsStartDate);
        termDetailsEndDate = findViewById(R.id.termDetailsEndDate);
        repo = new Repository(getApplication());
        termId = getIntent().getIntExtra("id", 0);
        current = repo.getTermById(termId);
        termDetailsTitle.setText(current.getTermTitle());
        termDetailsStartDate.setText(current.getStartDate().toString());
        termDetailsEndDate.setText(current.getEndDate().toString());



    }

    public void addCourse(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, AddCourse.class);
        startActivity(intent);
    }

    public void viewCourseDetails(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, CourseDetailsScreen.class);
        startActivity(intent);
    }

    public void deleteTerm(View view) {
        repo.delete(current);
        Intent intent = new Intent(TermDetailsScreen.this, HomeScreen.class);
        startActivity(intent);
    }
}