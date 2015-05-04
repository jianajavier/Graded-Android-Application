package com.jianajavier.gradedapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Course class holds all information about a course and can add evaluations.
 * Created by jianajavier on 15-04-17.
 */
public class Course implements Serializable{

//Variables-----------------------------------------------------------------------------------------
    /**
     * The name of the Course.
     */
    private String name;

    /**
     * The number of credits the Course is worth.
     */
    private double credits;

    /**
     * Whether or not the Course is finished.
     */

    private boolean finished;

    /**
     * A list of all the evaluations in the Course.
     */
    private List<Evaluation> evaluationList = new ArrayList<>();

    /**
     * The desired grade associated with the Course.
     */
    private DesiredGrade desiredGrade;

    /**
     * The acquired grade associated with the Course.
     */
    private AcquiredGrade acquiredGrade;

    /**
     * The required grade associated with the Course.
     */
    private RequiredGrade requiredGrade;

    /**
     * Path to add things to the course.
     */
    public static File dirPath;


    private static final long serialVersionUID = 3715448915207696794L;

    public int index;


//Constructor---------------------------------------------------------------------------------------

    /**
     * Course class constructor.
     */
    public Course(String nameInput, double cred, boolean fin, double dGrade, double rGrade, double
            aGrade){
        //Initialize the Course
        this.name = nameInput;
        this.credits = cred;
        this.finished = fin;
        this.desiredGrade = new DesiredGrade(dGrade);
        this.acquiredGrade = new AcquiredGrade(aGrade);
        this.requiredGrade = new RequiredGrade(rGrade, calculateReq());
    }

    public Course(){

    }



//Setter functions--------------------------------------------------------------------------

    /**
     * Set number of credits the Course is worth.
     * @param cred
     */
    public void setCredits(double cred){
        this.credits = cred;
    }

    /**
     * Set whether the course is finished or not.
     * @param fin
     */
    public void setFinished(boolean fin){
        this.finished = fin;
    }

    /**
     * Set the requiredGrade for the course.
     * @param requiredGrade
     */
    public void setRequiredGrade(RequiredGrade requiredGrade) {
        this.requiredGrade = requiredGrade;
    }

    /**
     * Set the desiredGrade for the course.
     * @param desiredGrade
     */
    public void setDesiredGrade(DesiredGrade desiredGrade) {
        this.desiredGrade = desiredGrade;
    }

    /**
     * Set the acquiredGrade for the course.
     * @param acquiredGrade
     */
    public void setAcquiredGrade(AcquiredGrade acquiredGrade) {
        this.acquiredGrade = acquiredGrade;
    }

//Getter functions-----------------------------------------------------------------------------

    /**
     * Return number of credits the Course is worth.
     * @return double credits
     */
    public double getCredits(){
        return credits;
    }

    /**
     * Return name of the Course.
     * @return String name
     */
    public String getName(){
        return name;
    }

    /**
     * Return evaluationList of the Course.
     * @return List evaluationList
     */
    public List<Evaluation> getEvalList(){
        return evaluationList;
    }

    /**
     * Return the RequiredGrade associated with the course.
     * @return RequiredGrade requiredGrade
     */
    public RequiredGrade getRequiredGrade() {
        return requiredGrade;
    }

    /**
     * Return the DesiredGrade associated with the course.
     * @return DesiredGrade desiredGrade
     */
    public DesiredGrade getDesiredGrade() {
        return desiredGrade;
    }

    /**
     * Return the AcquiredGrade associated with the course.
     * @return AcquiredGrade acquiredGrade
     */
    public AcquiredGrade getAcquiredGrade() {
        return acquiredGrade;
    }

    /**
     * Return if the Course is finished.
     * @return boolean finished
     */
    public boolean getFinished(){
        int numFinished = 0;
        for (Evaluation e : evaluationList){
            if (e.getFinished() == true){
                numFinished += 1;
            }
        }

        if (numFinished == evaluationList.size()){
            finished = true;
        }
        return finished;
    }

//The other functions-------------------------------------------------------------------------------

    /**
     * Add an evaluation to the course.
     * @param eval, dPath
     */
    public void addEval(Evaluation eval) throws IOException {
        /**
         * For writing to the course file.
         */
        BufferedWriter writer;

        evaluationList.add(eval);
        if (dirPath != null) {
            String data = eval.getName() + "," + eval.getFinished() + "," + eval.getWeight() + "," +
                    eval.getDesiredGrade().getGrade() + "," + eval.getRequiredGrade().getGrade() +
                    "," + eval.getAcquiredGrade().getGrade();
            //Maybe should error check if the file exists or not. But it should already exist.

            File outputFile = new File(dirPath, this.name + ".txt");

            writer = new BufferedWriter(new FileWriter(outputFile, true));
            writer.write(data);
            writer.newLine();

            writer.close();
        }
    }

    public void addEval(Evaluation eval, File dirPath) throws IOException {
        /**
         * For writing to the course file.
         */
        BufferedWriter writer;

        evaluationList.add(eval);
        if (dirPath != null) {
            String data = eval.getName() + "," + eval.getFinished() + "," + eval.getWeight() + "," +
                    eval.getDesiredGrade().getGrade() + "," + eval.getRequiredGrade().getGrade() +
                    "," + eval.getAcquiredGrade().getGrade();
            //Maybe should error check if the file exists or not. But it should already exist.

            File outputFile = new File(dirPath, this.name + ".txt");

            writer = new BufferedWriter(new FileWriter(outputFile, true));
            writer.newLine();
            writer.write(data);
            writer.newLine();

            writer.close();
        }
    }




    /**
     * Return Evaluation with name name.
     * @param name
     * @return Evaluation e
     */
    public Evaluation getEval(String name){
        Evaluation e = null;
        for (Evaluation eval : getEvalList()){
            if (eval.getName().equalsIgnoreCase(name)){
                e = eval;
            }
        }
        return e;
    }

    /**
     * Return the average grade needed to be achieved in order to bring the acquired grade to the
     * desired grade. Used for courses.
     *
     * Example: If the desired grade is 85%, it will take the cumulative percentage of acquired
     * grade so far, say it is 60%. Then it will take the percentage of the course completed so far,
     * say 70%. Then it will take the remaining amount of the course to be completed - 30%, and the
     * percentage needed to be earned to get the desired grade - 25% and return (25/30)*100 = 83.33.
     *
     * @return required grade
     */

    public double calculateReq(){ //Need in beginning and if desired grade is changed.

        double percentRemaining;
        double percentNeeded;

        percentRemaining = 100 - this.getAcquiredGrade().getPercentCompleted(this);
        percentNeeded = this.getDesiredGrade().getGrade() - this.getAcquiredGrade().
                getPercentAchieved(this);

        return Math.round(((percentNeeded/percentRemaining)*100)*100.0)/100.0;
    }

}
