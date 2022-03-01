package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.termRView);
        LinearLayout noTermsWarning = findViewById(R.id.noTermsWarning);
        Repository repo = new Repository(getApplication());
        List<Term> terms = repo.getAllTerms();
        if (terms.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
        }else {
            noTermsWarning.setVisibility(View.INVISIBLE);
        }
        final TermAdapter adapter = new TermAdapter(this);
        adapter.setTerms(terms);
        adapter.setRepository(repo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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