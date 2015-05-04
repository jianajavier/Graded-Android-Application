package com.jianajavier.gradedapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Evaluation class holds the weight, and grades for an evaluation.
 * Created by jianajavier on 15-04-17.
 */
public class Evaluation implements Serializable{
//Variables-----------------------------------------------------------------------------------------
    /**
     * The name of the Evaluation.
     */
    private String name;

    /**
     * The weight of the Evaluation. Percentage out of 100.
     */
    private double weight;

    /**
     * Whether or not the Evaluation is finished.
     */
    private boolean finished;

    /**
     * The desired grade associated with the Evaluation.
     */
    private DesiredGrade desiredGrade;

    /**
     * The acquired grade associated with the Evaluation.
     */
    private AcquiredGrade acquiredGrade;

    /**
     * The required grade associated with the Evaluation.
     */
    private RequiredGrade requiredGrade;

    /**
     * The course that owns the evaluation.
     */
    private Course owner;


    private static final long serialVersionUID = 7715448915207696794L;


    /**
     * The line number the evaluation is in the text file.
     */
    private int lineNumber;


//Constructor---------------------------------------------------------------------------------------

    /**
     * Evaluation constructor, initialized with name and weight.
     */
    public Evaluation(Course owner, String nameInput, boolean fin, double weight, double dGrade, double rGrade,
                      double aGrade) {
        this.owner = owner;
        this.name = nameInput;
        this.weight = weight;
        this.finished = fin;
        this.acquiredGrade = new AcquiredGrade(aGrade);
        this.requiredGrade = new RequiredGrade(rGrade);
        this.desiredGrade = new DesiredGrade(dGrade);
    }

//Setter functions----------------------------------------------------------------------------------

    /**
     * Reset number of credits the Evaluation is worth.
     */
    public void resetWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Set whether the Evaluation is finished or not.
     */
    public void setFinished(boolean fin) {
        this.finished = fin;
    }


    public void setRequiredGrade(RequiredGrade requiredGrade) {
        this.requiredGrade = requiredGrade;
    }

    public void setDesiredGrade(DesiredGrade desiredGrade) {
        this.desiredGrade = desiredGrade;
    }

    public void setAcquiredGrade(AcquiredGrade acquiredGrade) {
        this.acquiredGrade = acquiredGrade;
    }

    public void setOwner(Course owner) {
        this.owner = owner;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }




//Getter functions----------------------------------------------------------------------------------

    public Course getOwner() {
        return owner;
    }

    /**
     * Get number of credits the Evaluation is worth.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Get name of the Evaluation.
     */
    public String getName() {
        return name;
    }

    /**
     * Check if the Evaluation is finished.
     */
    public boolean getFinished() {
        return finished;
    }

    /**
     * Get the Grades associated with the Evaluation.
     */

    public DesiredGrade getDesiredGrade() {
        return desiredGrade;
    }

    public AcquiredGrade getAcquiredGrade() {
        return acquiredGrade;
    }

    public RequiredGrade getRequiredGrade() {
        return requiredGrade;
    }

    public int getLineNumber() {
        return lineNumber;
    }

//Other functions-----------------------------------------------------------------------------------

    public void complete(double aGrade){
        setFinished(true);
        setAcquiredGrade(new AcquiredGrade(aGrade));

        //After completing, should adjust the required grades for the remaining courses
        getRequiredGrade().adjustGrades(this.owner, this);

        for (Evaluation e : this.getOwner().getEvalList()){
            if (!e.getFinished()){
                e.getRequiredGrade().beenChanged = false;
            }
        }
    }

    public void complete(double mark, double total){
        setFinished(true);
        setAcquiredGrade(new AcquiredGrade(mark, total));

        getRequiredGrade().adjustGrades(this.owner, this);

        for (Evaluation e : this.getOwner().getEvalList()){
            if (!e.getFinished()){
                e.getRequiredGrade().beenChanged = false;
            }
        }
    }
}