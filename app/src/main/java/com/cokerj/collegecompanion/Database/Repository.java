package com.cokerj.collegecompanion.Database;

import android.app.Application;

import com.cokerj.collegecompanion.DAO.AssessmentDAO;
import com.cokerj.collegecompanion.DAO.CourseDAO;
import com.cokerj.collegecompanion.DAO.NoteDAO;
import com.cokerj.collegecompanion.DAO.TermDAO;
import com.cokerj.collegecompanion.DAO.UserDAO;
import com.cokerj.collegecompanion.Entity.Assessment;
import com.cokerj.collegecompanion.Entity.Course;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.Entity.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    final private AssessmentDAO mAssessmentDAO;
    final private TermDAO mTermDAO;
    final private CourseDAO mCourseDAO;
    final private NoteDAO mNoteDAO;
    final private UserDAO mUserDAO;
    private User mUserLogIn;
    private User mloggedInUser;
    private User mUserNameAvailable;
    private List<Term> mAllTerms;
    private List<Term> mTitleTerms;
    private List<Course> mAllCourses;
    private List<Course> mAllCompletedCourses;
    private List<Course> mAllInProgressCourses;
    private List<Course> mTermCourses;
    private List<Assessment> mCourseAssessments;
    private int mTermCount;
    private Term mTerm;
    private Course mCourse;
    private Assessment mAssessment;
    private List<Note> mCourseNotes;
    private Note mNote;

    final private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mNoteDAO = db.noteDAO();
        mUserDAO = db.userDAO();
    }

    public void insert(Term term){
        databaseExecutor.execute(()->mTermDAO.insert(term));
    }

    public void insert(Course course){
        databaseExecutor.execute(()-> mCourseDAO.insert(course));
    }
    public void insert(User user){
        databaseExecutor.execute(()-> mUserDAO.insert(user));
    }
    public void insert(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.insert(assessment));
    }

    public void insert(Note note){
        databaseExecutor.execute(()-> mNoteDAO.insert(note));
    }

    public void update(Term term){
        databaseExecutor.execute(()-> mTermDAO.update(term));
    }

    public void update(Course course){
        databaseExecutor.execute(()-> mCourseDAO.update(course));
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.update(assessment));
    }

    public void update(Note note){
        databaseExecutor.execute(()-> mNoteDAO.update(note));
    }

    public void delete(Term term){
        databaseExecutor.execute(()-> mTermDAO.delete(term));
    }

    public void delete(Course course){
        databaseExecutor.execute(()-> mCourseDAO.delete(course));
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.delete(assessment));
    }
    public void delete(Note note){
        databaseExecutor.execute(()-> mNoteDAO.delete(note));
    }

    public void logOutUser(){databaseExecutor.execute(()-> mUserDAO.logOutUser());}
    public void deleteCoursesByTermId(int termId){
        databaseExecutor.execute(()-> mCourseDAO.deleteCoursesByTermId(termId));
    }
    public void logInUser(int userId){databaseExecutor.execute(()-> mUserDAO.logInUser(userId));}
    public User getLoggedInUser(){
        databaseExecutor.execute(()-> mloggedInUser = mUserDAO.getLoggedInUser());
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mloggedInUser;
    }
    public List<Term> getAllTerms(int userId){
        databaseExecutor.execute(()-> mAllTerms = mTermDAO.getAllTerms(userId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Term> getTermsByString(String termTitle, int userId){
        databaseExecutor.execute(() -> mTitleTerms = mTermDAO.getTermsByString(termTitle, userId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTitleTerms;
    }

    public List<Course> getTermCourses(int termId){
        databaseExecutor.execute(()-> mTermCourses = mCourseDAO.getTermCourses(termId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTermCourses;
    }

    public User checkUserNameAvailability(String userName){
        databaseExecutor.execute(()-> mUserNameAvailable = mUserDAO.checkUserNameAvailability(userName));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mUserNameAvailable;
    }

    public User testLogIn(String userName, String userPassword){
        databaseExecutor.execute(()-> mUserLogIn = mUserDAO.testLogIn(userName,userPassword));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mUserLogIn;
    }

    public List<Course> getAllCourses(int userId){
        databaseExecutor.execute(()-> mAllCourses = mCourseDAO.getAllCourses(userId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Course> getAllCompletedCourses(int userId){
        databaseExecutor.execute(()-> mAllCompletedCourses = mCourseDAO.getAllCompletedCourses(userId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCompletedCourses;
    }

    public List<Course> getAllInProgressCourses(int userId){
        databaseExecutor.execute(()-> mAllInProgressCourses = mCourseDAO.getAllInProgressCourses(userId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllInProgressCourses;
    }

    public int getCourseCount(int termId){
        databaseExecutor.execute(()-> mTermCount = mCourseDAO.getCourseCount(termId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTermCount;
    }
    public Course getCourseById(int courseId){
        databaseExecutor.execute(()-> mCourse = mCourseDAO.getCourseById(courseId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourse;
    }
    public Note getNoteById(int noteId){
        databaseExecutor.execute(()-> mNote = mNoteDAO.getNoteById(noteId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mNote;
    }
    public Assessment getAssessmentById(int assessmentId){
        databaseExecutor.execute(()-> mAssessment = mAssessmentDAO.getAssessmentById(assessmentId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssessment;
    }

    public Term getTermById(int termId){
        databaseExecutor.execute(()-> mTerm = mTermDAO.getTermById(termId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTerm;
    }

    public List<Assessment> getCourseAssessments(int courseId){
        databaseExecutor.execute(()-> mCourseAssessments = mAssessmentDAO.getCourseAssessments(courseId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourseAssessments;
    }

    public List<Note> getCourseNotes(int courseId){
        databaseExecutor.execute(()-> mCourseNotes = mNoteDAO.getCourseNotes(courseId));
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourseNotes;
    }
}
