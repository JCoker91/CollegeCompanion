package com.cokerj.collegecompanion.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String termTitle;
    private Date startDate;
    private Date endDate;

    public Term(String termTitle, Date startDate, Date endDate) {
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termTitle='" + termTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTermId() {
        return termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
