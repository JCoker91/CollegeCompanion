package com.cokerj.collegecompanion.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class AssessmentDetailsScreen extends AppCompatActivity {
    Assessment current;
    Repository repo;
    TextView assessmentTitle;
    TextView assessmentStartDate;
    TextView assessmentEndDate;
    TextView assessmentType;
    TextView assessmentDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesssment_details_screen);
        repo = new Repository(getApplication());
        current = repo.getAssessmentById(getIntent().getIntExtra("assessmentId", 0));
        assessmentTitle = findViewById(R.id.assessmentDetailsTitle);
        assessmentStartDate = findViewById(R.id.assessmentDetailsStartDate);
        assessmentEndDate = findViewById(R.id.assessmentDetailsEndDate);
        assessmentType = findViewById(R.id.assessmentDetailsType);
        assessmentDescription = findViewById(R.id.assessmentDetailsDescription);

        assessmentTitle.setText(current.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        assessmentStartDate.setText(current.getStartDate().format(formatter).toString());
        assessmentEndDate.setText(current.getEndDate().format(formatter).toString());
        assessmentType.setText(current.getType());
        assessmentDescription.setText(current.getDescription());
        if (current.getDescription().length() == 1){
            assessmentDescription.setText("This assessment has no description.\nClick the button below to edit the assessment.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(AssessmentDetailsScreen.this, CourseDetailsScreen.class);
                intent.putExtra("id", current.getCourseId());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editAssessment(View view) {
        Intent intent = new Intent(AssessmentDetailsScreen.this, EditAssessment.class);
        intent.putExtra("assessmentId", current.getAssessment_id());
        startActivity(intent);
    }

    public void deleteAssessment(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
//        if(courseCount == 0) {
        builder.setMessage("Would you like to delete this assessment?")
                .setCancelable(false)
                .setTitle("Delete Assessment")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        repo.delete(current);
                        Intent intent = new Intent(AssessmentDetailsScreen.this, CourseDetailsScreen.class);
                        intent.putExtra("id", current.getCourseId());
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
}