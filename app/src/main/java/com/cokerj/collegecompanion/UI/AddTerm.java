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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
    DatePickerDialog picker;
    EditText startDateText;
    EditText endDateText;
    EditText termTitle;
    Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        termTitle = findViewById(R.id.inputTermTitle);

        termTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        startDateText = findViewById(R.id.inputTermStartDate);
        endDateText = findViewById(R.id.inputTermEndDate);
        startDateText.setInputType(InputType.TYPE_NULL);
        endDateText.setInputType(InputType.TYPE_NULL);
        endDateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddTerm.this, new DatePickerDialog.OnDateSetListener(){
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
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddTerm.this, new DatePickerDialog.OnDateSetListener(){
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
        getMenuInflater().inflate(R.menu.menu_add_term, menu);
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

    public void addNewTerm(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        if (startDateText.getText().toString().equals("") ||
                termTitle.getText().toString().equals("") ||
                endDateText.getText().toString().equals("")) {
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
                String title = termTitle.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                LocalDate startDate = LocalDate.parse(startDateText.getText(), formatter);
                LocalDate endDate = LocalDate.parse(endDateText.getText(), formatter);
                if (endDate.isBefore(startDate.plusDays(1))){
                    builder.setMessage("The term end date must occur after the start date.")
                            .setCancelable(false)
                            .setTitle("Invalid Term Dates")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    builder.setMessage("Create Term " + title + "?")
                            .setCancelable(false)
                            .setTitle("Confirm")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Term newTerm = new Term(title, startDate, endDate);
                                    repo.insert(newTerm);
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
                                    Intent notifyIntentStartDate = new Intent(AddTerm.this, MyReceiver.class);
                                    notifyIntentStartDate.putExtra("content", "The term " + title + " starts today!");
                                    notifyIntentStartDate.putExtra("title", "Term Starting");
                                    Intent notifyIntentEndDate = new Intent(AddTerm.this, MyReceiver.class);
                                    notifyIntentEndDate.putExtra("title", "Term Ending");
                                    notifyIntentEndDate.putExtra("content", "The term " + title + " ends today!");
                                    PendingIntent senderStartDate = PendingIntent.getBroadcast(AddTerm.this,(int)System.currentTimeMillis(),notifyIntentStartDate, 0);
                                    PendingIntent senderEndDate = PendingIntent.getBroadcast(AddTerm.this,(int)System.currentTimeMillis()+1,notifyIntentEndDate, 0);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, senderStartDate);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, senderEndDate);


                                    Intent intent = new Intent(AddTerm.this, TermsScreen.class);
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