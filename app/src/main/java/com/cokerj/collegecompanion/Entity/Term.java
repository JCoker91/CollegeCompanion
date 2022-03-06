package com.cokerj.collegecompanion.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cokerj.collegecompanion.Database.Converters;

import java.time.LocalDate;

@TypeConverters({Converters.class})
@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String termTitle;
    private LocalDate startDate;
    private LocalDate endDate;

    public Term(String termTitle, LocalDate startDate, LocalDate endDate) {
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Term{" +
                "termTitle='" + termTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getTermId() {
        return termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
