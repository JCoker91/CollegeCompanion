package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

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
        repo = new Repository(getApplication());
        termTitle = findViewById(R.id.inputTermTitle);
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

    public void addNewTerm(View view) {
        if(startDateText.getText().toString().equals("") ||
                termTitle.getText().toString().equals("") ||
                endDateText.getText().toString().equals("")){
            return;
        }
        try {
            String title = termTitle.getText().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
            LocalDate startDate = LocalDate.parse(startDateText.getText(), formatter);
            LocalDate endDate = LocalDate.parse(endDateText.getText(), formatter);
            System.out.println(title);
            System.out.println(startDate);
            System.out.println(endDate);
            Term newTerm = new Term(title,startDate,endDate);
            repo.insert(newTerm);
            Intent intent = new Intent(AddTerm.this, HomeScreen.class);
            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}