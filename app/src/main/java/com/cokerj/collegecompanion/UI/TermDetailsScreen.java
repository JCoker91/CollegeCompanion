package com.cokerj.collegecompanion.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TermDetailsScreen extends AppCompatActivity {
    int termId;
    int courseCount;
    Term current;
    Repository repo;
    private TextView termDetailsTitle;
    private TextView termDetailsStartDate;
    private TextView termDetailsEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details_screen);
        termDetailsTitle = findViewById(R.id.termDetailsTitle);
        termDetailsStartDate = findViewById(R.id.termDetailsStartDate);
        termDetailsEndDate = findViewById(R.id.termDetailsEndDate);
        repo = new Repository(getApplication());
        termId = getIntent().getIntExtra("id", 0);
        courseCount = getIntent().getIntExtra("courseCount", 0);

        RecyclerView recyclerView = findViewById(R.id.courseRView);
        LinearLayout noCoursesWarning = findViewById(R.id.noCoursesWarning);
        List<Course> courses = repo.getTermCourses(termId);
        if (courseCount == 0){
            recyclerView.setVisibility(View.INVISIBLE);
        }else {
            noCoursesWarning.setVisibility(View.INVISIBLE);
        }
        current = repo.getTermById(termId);
        termDetailsTitle.setText(current.getTermTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        termDetailsStartDate.setText(current.getStartDate().format(formatter).toString());
        termDetailsEndDate.setText(current.getEndDate().format(formatter).toString());

        final CourseAdapter adapter = new CourseAdapter(this);
        adapter.setCourses(courses);
        adapter.setRepository(repo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void addCourse(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, AddCourse.class);
        startActivity(intent);
    }

    public void viewCourseDetails(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, CourseDetailsScreen.class);
        startActivity(intent);
    }

    public void deleteTerm(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        if(courseCount == 0) {
            builder.setMessage("Would you like to delete term " + current.getTermTitle() + "?")
                    .setCancelable(false)
                    .setTitle("Delete Term")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            repo.delete(current);
                            Intent intent = new Intent(TermDetailsScreen.this, HomeScreen.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            dialog.cancel();
                        }
            });
        } else {
            builder.setMessage("Term " + current.getTermTitle() + " has " + String.valueOf(courseCount) + " courses assigned to it. Deleting the term will also delete these courses. Are you sure?")
                    .setCancelable(false)
                    .setTitle("WARNING")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            repo.delete(current);
                            Intent intent = new Intent(TermDetailsScreen.this, HomeScreen.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void editTerm(View view) {
        Intent intent = new Intent(TermDetailsScreen.this, EditTerm.class);
        intent.putExtra("termId", current.getTermId());
        intent.putExtra("title", current.getTermTitle());
        intent.putExtra("startDate", current.getStartDate());
        intent.putExtra("endDate", current.getEndDate());
        startActivity(intent);
    }

    public void addNewCourseButton(View view) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(3);
        int termId = current.getTermId();
        String instructorName = "John Smith";
        String instructorEmail = "john.smith@email.com";
        String instructorPhone = "555-555";
        String status = "In Progress";
        Course course = new Course(termId, "Test Course", startDate, endDate, instructorName,instructorPhone,instructorEmail, status);
        repo.insert(course);
    }
}