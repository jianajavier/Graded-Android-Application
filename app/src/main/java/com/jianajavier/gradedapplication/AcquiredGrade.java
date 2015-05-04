package com.jianajavier.gradedapplication;

import java.io.Serializable;

/**
 * AcquiredGrade is the grade you have received in the course to date or if its an evaluation then
 * it can only be set when finished is set to true.
 * Created by jianajavier on 15-04-18.
 */
public class AcquiredGrade extends Grade implements Serializable{

    private double percentCompleted;

    private double percentAchieved;

    private double avg;

    private static final long serialVersionUID = 5715448915207696794L;


    public AcquiredGrade(double grade) {
        super(grade);

        percentAchieved = 0;
        percentCompleted = 0;
        avg = 0;
    }

    public AcquiredGrade(double mark, double total) {
        super(mark, total);

        percentAchieved = 0;
        percentCompleted = 0;
        avg = 0;
    }

//Getter methods------------------------------------------------------------------------------------

    //NOT SURE IF THE FUNCTIONS GETTING COURSE AS ARGUMENTS SHOULD BE IN COURSE CLASS INSTEAD.

    public double getPercentCompleted(Course course) {
        calculatePercentCompleted(course); //Recalculates every time this method is called.
        return percentCompleted;
    }

    public double getPercentAchieved(Course course) {
        calculatePercentAchieved(course); //Recalculates every time this method is called.
        return percentAchieved;
    }

    public double getAvg(Course course) {
        this.avg = calculateCourseAvg(course);
        return this.avg;
    }

    /**
     * Calculate the percentage of the course completed so far. Only used when it's a course.
     *
     * Example: If 50% of the course is finished, return 50.
     *
     */
    public void calculatePercentCompleted(Course course){
        percentCompleted = 0;

        for (Evaluation eval : course.getEvalList()){
            if (eval.getFinished()){
                this.percentCompleted += eval.getWeight();
            }
        }
    }

    /**
     * Calculate the percentage of the course you have earned divided by the percentage of the
     * course completed so far. Only used when it's a course.
     *
     * Example: If 50% of the course is finished, and you earned 43% of it, it will return 86.
     *
     */
    public double calculateCourseAvg(Course course){
        calculatePercentCompleted(course);
        calculatePercentAchieved(course);

        if (percentCompleted == 0.0){
            return 0;
        }
        return Math.round(((this.percentAchieved/this.percentCompleted)*100)*100.0)/100.0;
    }

    /**
     * Calculate percentage of course you have earned as a raw percentage. How many marks you have
     * in the course at the moment. Only used when it is a course.
     *
     * Example: If 50% of the course is completed, and you earned 43% of it, it returns 43.
     *
     * @param course
     */
    public void calculatePercentAchieved(Course course){
        percentAchieved = 0;

        for (Evaluation eval : course.getEvalList()){
            if (eval.getFinished()){
                this.percentAchieved += (eval.getAcquiredGrade().getGrade())*0.01*eval.getWeight();
                //AcquiredGrade should be set because it is finished.
            }
        }
    }


}
