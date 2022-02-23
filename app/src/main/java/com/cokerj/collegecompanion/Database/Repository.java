package com.cokerj.collegecompanion.Database;

import android.app.Application;

import com.cokerj.collegecompanion.DAO.AssessmentDAO;
import com.cokerj.collegecompanion.DAO.CourseDAO;
import com.cokerj.collegecompanion.DAO.TermDAO;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
    }

}
