package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.util.List;

public class TermsScreen extends AppCompatActivity {
    public static int counter = 1;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_screen);
        RecyclerView recyclerView = findViewById(R.id.termRView);
        TextView noTermsWarning = findViewById(R.id.noTermsWarningText);
        Repository repo = new Repository(getApplication());
        List<Term> terms = repo.getAllTerms();
        searchBar = findViewById(R.id.inputSearchBar);

        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

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

    public void searchTerms(View view){
        TextView noSearchTerms = findViewById(R.id.noSearchTerms);

        TextView noTermsWarning = findViewById(R.id.noTermsWarningText);
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        final TermAdapter adapter = new TermAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.termRView);
        Repository repo = new Repository(getApplication());
        String titleString = searchBar.getText().toString();
        List<Term> terms = repo.getTermsByString(titleString);

        if (terms.isEmpty()) {
            noTermsWarning.setVisibility(View.INVISIBLE);
            noSearchTerms.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            noSearchTerms.setVisibility(View.INVISIBLE);
            adapter.setTerms(terms);
            adapter.setRepository(repo);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }





    }

    public void toReportsScreen(View view) {
        Intent intent = new Intent(TermsScreen.this, ReportsScreen.class);
        startActivity(intent);
    }

    public void addTerm(View view) {
        Intent intent = new Intent(TermsScreen.this, AddTerm.class);
        startActivity(intent);

    }

    public void viewTermDetails(View view) {
        Intent intent = new Intent(TermsScreen.this, TermDetailsScreen.class);
        startActivity(intent);
    }
}