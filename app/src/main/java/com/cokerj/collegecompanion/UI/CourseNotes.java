package com.cokerj.collegecompanion.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.R;

import java.util.List;

public class CourseNotes extends AppCompatActivity {

    Repository repo;
    Course current;
    List<Note> courseNotes;
    TextView courseTitle;
    int courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        courseId = getIntent().getIntExtra("courseId",0);
        current = repo.getCourseById(courseId);
        courseTitle = findViewById(R.id.courseNotesCoursetitle);
        courseTitle.setText(current.getName() + " Notes");
        List<Note> courseNotes = repo.getCourseNotes(courseId);
        final NoteAdapter adapter = new NoteAdapter(this);
        adapter.setNotes(courseNotes);
        adapter.setRepository(repo);
        RecyclerView recyclerView = findViewById(R.id.notesRView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        View noNotesWarning = findViewById(R.id.noNotesWarning);
        if (courseNotes.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
        } else{
            noNotesWarning.setVisibility(View.INVISIBLE);
        }
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
                Intent intent = new Intent(CourseNotes.this, CourseDetailsScreen.class);
                intent.putExtra("id", courseId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toAddNote(View view) {
        Intent intent = new Intent(CourseNotes.this, AddNote.class);
        intent.putExtra("courseId", current.getCourseId());
        startActivity(intent);
    }
}