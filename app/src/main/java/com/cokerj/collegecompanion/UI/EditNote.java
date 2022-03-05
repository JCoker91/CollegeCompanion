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
import android.widget.EditText;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.R;

public class EditNote extends AppCompatActivity {
    Repository repo;
    Note current;
    EditText noteTitle;
    EditText noteContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        int noteId = getIntent().getIntExtra("noteId", 0);
        current = repo.getNoteById(noteId);
        noteTitle = findViewById(R.id.editNoteTitle);
        noteContent = findViewById(R.id.editNoteContent);
        noteTitle.setText(current.getTitle().toString());
        noteContent.setText(current.getContent().toString());
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editExistingNote(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        if (noteTitle.getText().toString().equals("") ||
                noteContent.getText().toString().equals("")){
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
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();
                builder.setMessage("Update Note?")
                        .setCancelable(false)
                        .setTitle("Confirm")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                current.setTitle(title);
                                current.setContent(content);
                                repo.update(current);
                                Intent intent = new Intent(EditNote.this, NoteDetailsScreen.class);
                                intent.putExtra("noteId", current.getNoteId());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }});
                AlertDialog alert = builder.create();
                alert.show();

            } catch (Exception e) {
                e.printStackTrace();
                builder.setMessage("The values you entered are invalid.")
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