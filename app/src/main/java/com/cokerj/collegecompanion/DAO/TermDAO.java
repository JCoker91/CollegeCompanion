package com.cokerj.collegecompanion.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cokerj.collegecompanion.Entity.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms WHERE userId = :userId ORDER BY termId ASC")
    List<Term> getAllTerms(int userId);

    @Query("SELECT * FROM terms WHERE termId = :termId LIMIT 1")
    Term getTermById(int termId);

    @Query("SELECT * FROM terms WHERE instr(UPPER(termTitle), UPPER(:termTitle)) and userId = :userId ORDER BY termTitle ASC")
    List<Term> getTermsByString(String termTitle, int userId);
}
