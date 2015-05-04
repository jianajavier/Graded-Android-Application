package com.jianajavier.gradedapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianajavier on 15-04-17.
 */
public abstract class Grade {
//Variables-----------------------------------------------------------------------------------------
    /**
     * The total that it's out of.
     */
    public double total;

    /**
     * The mark earned out of the total.
     */
    public double mark;

    /**
     * The percentage grade out of 100.
     */
    public double grade;


//Constructor---------------------------------------------------------------------------------------

    /**
     * Grade class constructed with the percentage.
     *
     * @param grade
     */
    public Grade(double grade) {
        this.grade = grade; //Might wanna change this later

    }

    /**
     * Grade class constructed with mark and total.
     *
     * @param mark
     * @param total
     */
    public Grade(double mark, double total) {
        this.mark = mark;
        this.total = total;
        calculateGrade();
    }

    /**
     * Grade class constructor with no arguments defaults grade to 0.
     */
    public Grade() {
        this.grade = 0;
    }


//Setter functions--------------------------------------------------------------------------

    /**
     * Set the total marks for the Grade.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Set the mark for the grade over the total.
     */
    public void setMark(double mark) {
        this.mark = mark;
    }

    /**
     * Set the percentage grade for the Grade.
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }


//Getter functions----------------------------------------------------------------------------------

    /**
     * Get total for Grade.
     *
     * @return total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Get mark for Grade.
     *
     * @return mark
     */
    public double getMark() {
        return mark;
    }

    /**
     * Get percentage grade for Grade.
     *
     * @return grade
     */
    public double getGrade() {
        return grade;
    }


//Other functions-----------------------------------------------------------------------------------

    /**
     * Get grade for Grade.
     *
     * @return grade
     */
    public void calculateGrade() {
        this.grade = ((this.mark/this.total)*100);
    }

}