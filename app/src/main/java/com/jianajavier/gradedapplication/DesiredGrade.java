package com.jianajavier.gradedapplication;

import java.io.Serializable;

/**
 * DesiredGrade is for the grade you want to receive for the evaluation.
 * Created by jianajavier on 15-04-18.
 */
public class DesiredGrade extends Grade implements Serializable{

    private static final long serialVersionUID = 6715448915207696794L;


    public DesiredGrade(double grade) {
        super(grade);
    }

    public DesiredGrade(double mark, double total) {
        super(mark, total);
    }
}
