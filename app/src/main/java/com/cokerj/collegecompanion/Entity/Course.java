package com.cokerj.collegecompanion.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cokerj.collegecompanion.Database.Converters;

import java.time.LocalDate;

@TypeConverters({Converters.class})
@Entity(tableName = "courses",
        foreignKeys = {@ForeignKey(entity = Term.class,
                parentColumns = "termId",
                childColumns = "termId",
                onDelete = ForeignKey.CASCADE)})
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Course(int termId, String name, LocalDate startDate, LocalDate endDate, String instructorName, String instructorEmail, String instructorPhone, String status) {
        this.termId = termId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.status = status;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
