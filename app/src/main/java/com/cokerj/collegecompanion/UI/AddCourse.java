package com.cokerj.collegecompanion.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCourse extends AppCompatActivity {
    DatePickerDialog picker;
    EditText startDateText;
    EditText endDateText;
    EditText courseTitle;
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhone;
    Repository repo;
    RadioGroup courseStatusRadioGroup;
    int termId;
    int courseCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        courseTitle = findViewById(R.id.inputCourseTitle);
        courseStatusRadioGroup = findViewById(R.id.courseStatusRadioGroup);
        startDateText = findViewById(R.id.inputCourseStartDate);
        endDateText = findViewById(R.id.inputCourseEndDate);
        instructorName = findViewById(R.id.inputCourseInstructorName);
        instructorPhone = findViewById(R.id.inputCourseInstructorPhone);
        instructorEmail = findViewById(R.id.inputCourseInstructorEmail);
        startDateText.setInputType(InputType.TYPE_NULL);
        endDateText.setInputType(InputType.TYPE_NULL);
        termId = getIntent().getIntExtra("id", -1);
        courseCount = getIntent().getIntExtra("courseCount", 0);
        courseStatusRadioGroup.clearCheck();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        instructorEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                    return true;
                }
                return false;
            }
        });
        courseStatusRadioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                    }
                });

        endDateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddCourse.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        endDateText.setText(String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth) + "-" + String.valueOf(year));
                    }
                }, year, month, day);
                picker.show();
            }
        });
        startDateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddCourse.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        startDateText.setText(String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth) + "-" + String.valueOf(year));
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

    public void addNewCourse(View view) {
        int selectedId = courseStatusRadioGroup.getCheckedRadioButtonId();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        String phone = instructorPhone.getText().toString();
        if (startDateText.getText().toString().equals("") ||
                courseTitle.getText().toString().equals("") ||
                endDateText.getText().toString().equals("") ||
                instructorName.getText().toString().equals("") ||
                instructorEmail.getText().toString().equals("") ||
                instructorPhone.getText().toString().equals("") ||
                selectedId == -1 ||
                termId == -1){
            builder.setMessage("Please enter a value for each field.")
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
                String title = courseTitle.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                LocalDate startDate = LocalDate.parse(startDateText.getText(), formatter);
                LocalDate endDate = LocalDate.parse(endDateText.getText(), formatter);
                String iName = instructorName.getText().toString();
                String iPhone = instructorPhone.getText().toString();
                String iEmail = instructorEmail.getText().toString();
                RadioButton radioButton = (RadioButton)courseStatusRadioGroup.findViewById(selectedId);
                String status = radioButton.getText().toString();
                if (endDate.isBefore(startDate.plusDays(1))){
                    builder.setMessage("The course end date must occur after the start date.")
                            .setCancelable(false)
                            .setTitle("Invalid Course Dates")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    builder.setMessage("Create Course " + title + "?")
                            .setCancelable(false)
                            .setTitle("Confirm")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Course newCourse = new Course(termId,title, startDate, endDate, iName,iEmail,iPhone,status);
                                    repo.insert(newCourse);
                                    String myFormat = "M-d-yyyy";
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                    Date startDateTriggerDate = null;
                                    Date endDateTriggerDate = null;
                                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                                    try {
                                        startDateTriggerDate = sdf.parse(startDateText.getText().toString());
                                        endDateTriggerDate = sdf.parse(endDateText.getText().toString());
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    Long startDateTrigger = startDateTriggerDate.getTime();
                                    Long endDateTrigger = endDateTriggerDate.getTime();
                                    Intent notifyIntentStartDate = new Intent(AddCourse.this, MyReceiver.class);
                                    notifyIntentStartDate.putExtra("content", "The course " + title + " starts today!");
                                    notifyIntentStartDate.putExtra("title", "Course Starting");
                                    Intent notifyIntentEndDate = new Intent(AddCourse.this, MyReceiver.class);
                                    notifyIntentEndDate.putExtra("title", "Course Ending");
                                    notifyIntentEndDate.putExtra("content", "The course " + title + " ends today!");
                                    PendingIntent senderStartDate = PendingIntent.getBroadcast(AddCourse.this,(int)System.currentTimeMillis(),notifyIntentStartDate, 0);
                                    PendingIntent senderEndDate = PendingIntent.getBroadcast(AddCourse.this,(int)System.currentTimeMillis()+1,notifyIntentEndDate, 0);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, senderStartDate);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, senderEndDate);
                                    Intent intent = new Intent(AddCourse.this, TermDetailsScreen.class);
                                    intent.putExtra("id", termId);
                                    intent.putExtra("courseCount", courseCount);
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