package com.cokerj.collegecompanion.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.R;

public class NoteDetailsScreen extends AppCompatActivity {
    TextView noteTitle;
    TextView noteContent;
    Note current;
    Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details_screen);
        repo = new Repository(getApplication());
        int noteId = getIntent().getIntExtra("noteId", 0);
        current = repo.getNoteById(noteId);
        noteTitle = findViewById(R.id.noteDetailsNoteTitle);
        noteContent = findViewById(R.id.noteDetailsContent);
        noteTitle.setText(current.getTitle());
        noteContent.setText(current.getContent());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(NoteDetailsScreen.this, CourseNotes.class);
                intent.putExtra("courseId", current.getCourseId());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editNote(View view) {
        Intent intent = new Intent(NoteDetailsScreen.this, EditNote.class);
        intent.putExtra("noteId", current.getNoteId());
        startActivity(intent);
    }

    public void shareNote(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, current.getTitle());
        sendIntent.putExtra(Intent.EXTRA_TEXT, current.getContent());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void deleteNote(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to delete this note?")
                .setCancelable(false)
                .setTitle("Delete Note")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        repo.delete(current);
                        Intent intent = new Intent(NoteDetailsScreen.this, CourseNotes.class);
                        intent.putExtra("courseId", current.getCourseId());
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}