package com.cokerj.collegecompanion.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cokerj.collegecompanion.DAO.AssessmentDAO;
import com.cokerj.collegecompanion.DAO.CourseDAO;
import com.cokerj.collegecompanion.DAO.NoteDAO;
import com.cokerj.collegecompanion.DAO.TermDAO;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.Entity.Term;

@Database(entities = {Assessment.class, Course.class, Term.class, Note.class}, version = 22, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract NoteDAO noteDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (DatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "collegeCompanionDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
