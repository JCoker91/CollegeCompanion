package com.cokerj.collegecompanion.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cokerj.collegecompanion.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments WHERE assessment_id = :assessmentId LIMIT 1")
    Assessment getAssessmentById(int assessmentId);

    @Query("SELECT * FROM assessments WHERE courseId= :courseId ORDER BY assessment_id ASC")
    List<Assessment> getCourseAssessments(int courseId);
}
