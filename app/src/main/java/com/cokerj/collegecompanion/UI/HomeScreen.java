package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cokerj.collegecompanion.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTerm(View view) {
        Intent intent = new Intent(HomeScreen.this, AddTerm.class);
        startActivity(intent);
    }

    public void viewTermDetails(View view) {
        Intent intent = new Intent(HomeScreen.this, TermDetailsScreen.class);
        startActivity(intent);
    }
}