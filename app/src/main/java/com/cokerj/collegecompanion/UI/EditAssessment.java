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
import com.cokerj.collegecompanion.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class EditAssessment extends AppCompatActivity {
    int assessmentId;
    Assessment current;
    TextView assessmentTitle;
    TextView assessmentStartDate;
    TextView assessmentEndDate;
    RadioGroup assessmentTypeRadioGroup;
    TextView assessmentDescription;
    DatePickerDialog picker;
    Repository repo;
    RadioButton performance;
    RadioButton objective;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        assessmentId = getIntent().getIntExtra("assessmentId", 0);
        current = repo.getAssessmentById(assessmentId);


        assessmentTitle = findViewById(R.id.editAssessmentTitle);
        assessmentTitle.setText(current.getName());
        assessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        assessmentStartDate.setText(current.getStartDate().format(formatter).toString());
        assessmentEndDate = findViewById(R.id.editAssessmentEndDate);
        assessmentEndDate.setText(current.getEndDate().format(formatter).toString());
        assessmentDescription = findViewById(R.id.editAssessmentDescription);
        assessmentDescription.setText(current.getDescription());
        assessmentTypeRadioGroup = findViewById(R.id.editAssessmentTypeRadioGroup);
        assessmentTypeRadioGroup.clearCheck();
        performance = findViewById(R.id.editPerformanceRadioButton);
        objective = findViewById(R.id.editObjectiveRadioButton);
        if (performance.getText().equals(current.getType())){performance.setChecked(true);}
        if (objective.getText().equals(current.getType())){objective.setChecked(true);}
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
                picker = new DatePickerDialog(EditAssessment.this, new DatePickerDialog.OnDateSetListener(){
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
                picker = new DatePickerDialog(EditAssessment.this, new DatePickerDialog.OnDateSetListener(){
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
        getMenuInflater().inflate(R.menu.menu_edit_course, menu);
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

    public void saveAssessment(View view) {
        int selectedId = assessmentTypeRadioGroup.getCheckedRadioButtonId();
        int courseId = current.getCourseId();
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
                    builder.setMessage("Save Assessment?")
                            .setCancelable(false)
                            .setTitle("Confirm")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    current.setName(title);
                                    current.setDescription(description);
                                    current.setType(type);
                                    current.setStartDate(startDate);
                                    current.setEndDate(endDate);
                                    repo.update(current);
                                    Intent intent = new Intent(EditAssessment.this, AssessmentDetailsScreen.class);
                                    intent.putExtra("assessmentId", current.getAssessment_id());
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