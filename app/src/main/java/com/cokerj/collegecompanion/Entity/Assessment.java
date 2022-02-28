package com.cokerj.collegecompanion.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.cokerj.collegecompanion.Database.Converters;

import java.time.LocalDate;
import java.util.Date;



@TypeConverters({Converters.class})
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;


    @Override
    public String toString() {
        return "Assessment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
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

    public Assessment(String name, LocalDate startDate, LocalDate endDate, String type) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }
}
