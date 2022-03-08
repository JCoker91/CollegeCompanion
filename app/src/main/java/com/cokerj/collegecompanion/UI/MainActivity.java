package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cokerj.collegecompanion.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterApp(View view) {
        Intent intent = new Intent(MainActivity.this, TermsScreen.class);
        startActivity(intent);
    }
}