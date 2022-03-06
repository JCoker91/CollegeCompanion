package com.cokerj.collegecompanion.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cokerj.collegecompanion.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses WHERE termId = :termId ORDER BY courseId ASC")
    List<Course> getTermCourses(int termId);

    @Query("SELECT * FROM courses WHERE courseId = :courseId LIMIT 1")
    Course getCourseById(int courseId);

    @Query("SELECT COUNT(courseId) FROM courses WHERE termId = :termId")
    int getCourseCount(int termId);

    @Query("DELETE FROM courses WHERE termId = :termId")
    void deleteCoursesByTermId(int termId);
}
