package com.cokerj.collegecompanion.Entity;

import java.time.LocalDate;

public class PerformanceAssessment extends Assessment {
    public PerformanceAssessment(int assessmentId, String name, LocalDate startDate, LocalDate endDate, String description, int courseId){
        super(name,startDate,endDate,"Performance",description,courseId);
        this.setAssessment_id(assessmentId);
    }

    @Override
    public String getAssessmentTypeDescription(){
        return "Performance assessments are subjective projects that are graded by a rubric.";
    }
}