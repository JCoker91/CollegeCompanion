package com.cokerj.collegecompanion.Entity;

import java.time.LocalDate;

public class ObjectiveAssessment extends Assessment{
    public ObjectiveAssessment(int assessmentId, String name, LocalDate startDate, LocalDate endDate,String description, int courseId){
        super(name,startDate,endDate,"Objective",description,courseId);
        this.setAssessment_id(assessmentId);
    }

    @Override
    public String getAssessmentTypeDescription(){
        return "Objective assessments are tests with only incorrect and correct answers.";
    }
}
