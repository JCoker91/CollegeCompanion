package com.cokerj.collegecompanion.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;



@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String type;

    @Override
    public String toString() {
        return "Assessment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }

    public Assessment(String name, Date startDate, Date endDate, String type) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }
}
