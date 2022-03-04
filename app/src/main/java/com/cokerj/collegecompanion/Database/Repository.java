package com.cokerj.collegecompanion.Database;

import android.app.Application;

import com.cokerj.collegecompanion.DAO.AssessmentDAO;
import com.cokerj.collegecompanion.DAO.CourseDAO;
import com.cokerj.collegecompanion.DAO.NoteDAO;
import com.cokerj.collegecompanion.DAO.TermDAO;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private NoteDAO mNoteDAO;
    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;
    private List<Course> mTermCourses;
    private List<Assessment> mCourseAssessments;
    private int mTermCount;
    private Term mTerm;
    private Course mCourse;
    private Assessment mAssessment;
    private List<Note> mCourseNotes;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mNoteDAO = db.noteDAO();
    }

    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
    }

    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
    }

    public void insert(Note note){
        databaseExecutor.execute(()->{
            mNoteDAO.insert(note);
        });
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
    }

    public void update(Note note){
        databaseExecutor.execute(()->{
            mNoteDAO.update(note);
        });
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
    }
    public void delete(Note note){
        databaseExecutor.execute(()->{
            mNoteDAO.delete(note);
        });
    }

    public void deleteCoursesByTermId(int termId){
        databaseExecutor.execute(()->{
            mCourseDAO.deleteCoursesByTermId(termId);
        });
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Course> getTermCourses(int termId){
        databaseExecutor.execute(()->{
            mTermCourses = mCourseDAO.getTermCourses(termId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTermCourses;
    }

    public int getCourseCount(int termId){
        databaseExecutor.execute(()->{
            mTermCount = mCourseDAO.getCourseCount(termId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTermCount;
    }
    public Course getCourseById(int courseId){
        databaseExecutor.execute(()->{
            mCourse = mCourseDAO.getCourseById(courseId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourse;
    }
    public Assessment getAssessmentById(int assessmentId){
        databaseExecutor.execute(()->{
            mAssessment = mAssessmentDAO.getAssessmentById(assessmentId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssessment;
    }

    public Term getTermById(int termId){
        databaseExecutor.execute(()->{
            mTerm = mTermDAO.getTermById(termId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTerm;
    }

    public List<Assessment> getCourseAssessments(int courseId){
        databaseExecutor.execute(()->{
            mCourseAssessments = mAssessmentDAO.getCourseAssessments(courseId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourseAssessments;
    }

    public List<Note> getCourseNotes(int courseId){
        databaseExecutor.execute(()->{
            mCourseNotes = mNoteDAO.getCourseNotes(courseId);
        });
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourseNotes;
    }
}
