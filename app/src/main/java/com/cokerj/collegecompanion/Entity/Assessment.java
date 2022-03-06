package com.cokerj.collegecompanion.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cokerj.collegecompanion.Database.Converters;

import java.time.LocalDate;



@TypeConverters({Converters.class})
@Entity(tableName = "assessments", foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "courseId",
        childColumns = "courseId",
        onDelete = ForeignKey.CASCADE)})
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;

    private int courseId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


    @NonNull
    @Override
    public String toString() {
        return "Assessment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
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

    public void setType(String type) {
        this.type = type;
    }

    public int getAssessment_id() {
        return assessment_id;
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

    public String getType() {
        return type;
    }

    public Assessment(String name, LocalDate startDate, LocalDate endDate, String type,String description, int courseId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.description = description;
        this.courseId = courseId;
    }
}
