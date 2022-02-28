package com.cokerj.collegecompanion.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cokerj.collegecompanion.Database.Converters;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@TypeConverters({Converters.class})
@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int couresId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void setCouresId(int couresId) {
        this.couresId = couresId;
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

    public Course(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public int getCouresId() {
        return couresId;
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
