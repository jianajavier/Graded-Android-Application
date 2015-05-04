package com.jianajavier.gradedapplication;

import java.io.Serializable;

/**
 * For courses, RequiredGrade means the average grade needed to be achieved in order to bring
 * current acquired grade to the desired grade.
 *
 * For evaluations, RequiredGrade means the mark required on the evaluation to achieve the course
 * desired grade. By default it will be set to the average grade among all the evaluations, but can
 * be adjusted using increment/decrementers.
 *
 * Created by jianajavier on 15-04-18.
 */
public class RequiredGrade extends Grade implements Serializable{

    private static final long serialVersionUID = 4715448915207696794L;


    final int MAXGRADE = 100;

    public static double calcReq;
    public boolean beenChanged; //Public right now but will change it to private later.

    public RequiredGrade(double grade) {
        super(grade);
        this.beenChanged = false;
        this.grade = calcReq;
        //this.calculate = true;
        //Maybe make it so it only calculates the first time. Because changing any is just done through adjustgrades.
    }

    public RequiredGrade(double grade, double calculatedRequired){
        super(grade);
        this.grade = calculatedRequired;
        calcReq = calculatedRequired;
        this.beenChanged = false;
    }

    /**
     * Change the required grade of a specific evaluation.
     */
    public void changeGrade(Evaluation eval, double factor){
        //Sets the requiredGrade of the eval passed in
        eval.getRequiredGrade().setGrade(eval.getRequiredGrade().getGrade() +
                factor);

        //If check is negative, then I DO want to change even though it has beenChanged already
        if (check(eval.getOwner()) < 0 ){
            for (Evaluation e : eval.getOwner().getEvalList()){
                if (!e.getFinished()){
                    e.getRequiredGrade().beenChanged = false;
                }
            }
        }

        //Adjust the remaining grades.
        eval.getRequiredGrade().beenChanged = true;
        adjustGrades(eval.getOwner(), eval);
    }

    public void adjustGrades(Course course, Evaluation eval){
        double hypotheticalAccumulated = 0;
        double accountedFor = 0;
        double actualAccumulated = 0;

        for (Evaluation e : course.getEvalList()){
            if (e.getFinished()){
                hypotheticalAccumulated += e.getAcquiredGrade().getGrade()*e.getWeight()*0.01;
                actualAccumulated += e.getAcquiredGrade().getGrade()*e.getWeight()*0.01;
                accountedFor += e.getWeight();
            } else if (e.getRequiredGrade().beenChanged){
                hypotheticalAccumulated += e.getRequiredGrade().getGrade()*e.getWeight()*0.01;
                accountedFor += e.getWeight();
            }
        }
        //LET THE CHANGES REMAIN IF ITS OVERACHIEVING.

        //Deal with the case that they have all beenChanged before - Increase all the grades by the
        //same amount. This will only be for after you've completed a course?
        double distribution = 0;
        double needToAdd = 0;
        int notFinished = 0;

        if (accountedFor == MAXGRADE){
            if (hypotheticalAccumulated < course.getDesiredGrade().getGrade()){
                //Should notify user that changing to this requiredGrade will not let them achieve their desired.
                for (Evaluation e : course.getEvalList()) {
                    if (!e.getFinished()) {
                        distribution += e.getRequiredGrade().getGrade() * e.getWeight() * 0.01;
                        notFinished += 1;
                    }
                }
                needToAdd = Math.round((((course.getDesiredGrade().getGrade() - actualAccumulated) - distribution)/notFinished)*100.0)/100.0;
                for (Evaluation e : course.getEvalList()) {
                    if (!e.getFinished()) {
                        e.getRequiredGrade().setGrade(e.getRequiredGrade().getGrade() + needToAdd);
                    }
                }
            }
        }else {
            double newReqGrade = Math.round((((course.getDesiredGrade().getGrade() - hypotheticalAccumulated)
                    / (MAXGRADE - accountedFor)) * 100) * 100.0) / 100.0;

            for (Evaluation e : course.getEvalList()) {
                if (!e.getFinished() && !e.getRequiredGrade().beenChanged) {
                    e.getRequiredGrade().setGrade(newReqGrade);
                }
            }
        }
    }

    public double check(Course course){
        double total = 0;

        for (Evaluation e : course.getEvalList()){
            if (e.getFinished()){
                total += e.getAcquiredGrade().getGrade() * 0.01 * e.getWeight();
            } else {
                total += e.getRequiredGrade().getGrade() * 0.01 * e.getWeight();
            }
        }
        return Math.round((total-course.getDesiredGrade().getGrade())*100.0)/100.0;

    }
}
