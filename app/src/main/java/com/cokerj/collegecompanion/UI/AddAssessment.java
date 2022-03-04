package com.cokerj.collegecompanion.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddAssessment extends AppCompatActivity {
    int courseId;
    TextView assessmentTitle;
    TextView assessmentStartDate;
    TextView assessmentEndDate;
    RadioGroup assessmentTypeRadioGroup;
    TextView assessmentDescription;
    DatePickerDialog picker;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseId = getIntent().getIntExtra("courseId", 0);
        assessmentTitle = findViewById(R.id.inputAssessmentTitle);
        assessmentStartDate = findViewById(R.id.inputAssessmentStartDate);
        assessmentEndDate = findViewById(R.id.inputAssessmentEndDate);
        assessmentDescription = findViewById(R.id.inputAssessmentDescription);
        assessmentTypeRadioGroup = findViewById(R.id.assessmentTypeRadioGroup);
        repo = new Repository(getApplication());
        assessmentTypeRadioGroup.clearCheck();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        assessmentTypeRadioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                    }
                });

        assessmentEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddAssessment.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        assessmentEndDate.setText(String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth) + "-" + String.valueOf(year));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        assessmentStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddAssessment.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        assessmentStartDate.setText(String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth) + "-" + String.valueOf(year));
                    }
                }, year, month, day);
                picker.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewAssessment(View view) {

        int selectedId = assessmentTypeRadioGroup.getCheckedRadioButtonId();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        if (assessmentStartDate.getText().toString().equals("") ||
                assessmentTitle.getText().toString().equals("") ||
                assessmentEndDate.getText().toString().equals("") ||
                selectedId == -1 ||
                courseId == -1){
            builder.setMessage("Please enter a value for all required fields.")
                    .setCancelable(false)
                    .setTitle("Missing Or Invalid Values")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            try {
                String title = assessmentTitle.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                LocalDate startDate = LocalDate.parse(assessmentStartDate.getText(), formatter);
                LocalDate endDate = LocalDate.parse(assessmentEndDate.getText(), formatter);
                RadioButton radioButton = (RadioButton)assessmentTypeRadioGroup.findViewById(selectedId);
                String type = radioButton.getText().toString();
                String description = assessmentDescription.getText().toString();
                if (endDate.isBefore(startDate.plusDays(1))){
                    builder.setMessage("The assessment end date must occur after the start date.")
                            .setCancelable(false)
                            .setTitle("Invalid Assessment Dates")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    builder.setMessage("Create Assessment " + title + "?")
                            .setCancelable(false)
                            .setTitle("Confirm")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Assessment assessment = new Assessment(title,startDate,endDate,type,description, courseId);
                                    repo.insert(assessment);
                                    Intent intent = new Intent(AddAssessment.this, CourseDetailsScreen.class);
                                    intent.putExtra("id", courseId);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }});
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                builder.setMessage("The values you entered are invalid. Please enter start and end date in the format Month-Day-Year (e.g. 3-15-2021).")
                        .setCancelable(false)
                        .setTitle("Invalid or Missing Values")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }


    }
}