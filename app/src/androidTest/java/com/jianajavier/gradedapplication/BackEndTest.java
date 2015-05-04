package com.jianajavier.gradedapplication;

import android.content.Context;
import android.test.ActivityUnitTestCase;

import junit.framework.Test;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * Created by jianajavier on 15-04-18.
 */
public class BackEndTest extends TestCase {

    private BackEndTest backEndTest;
    private static final CourseManager courseManager = new CourseManager();

    public BackEndTest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void TestCase(){ //PROBLEM: getApplicationContext won't work here, so must find alternative just for testing
        try {
            //courseManager = new CourseManager(this.getApplicationContext().getFilesDir(),""); //this.getApplicationContext().getFilesDir() won't work

            //Testing adding a new course
            Course testCourse = new Course("TEST202", 0.5, false, 0.0, 0.0, 0.0);
            Evaluation testEval = new Evaluation(testCourse, "Test 2", false, 0.0, 0.0, 0.0, 0.0);
            Evaluation testEval2 = new Evaluation(testCourse, "Test 4", false, 0.6,0.5,0.3,0.2);

            courseManager.add(testCourse);

            //testCourse.addEval(testEval, this.getApplicationContext().getFilesDir());
            ///testCourse.addEval(testEval2, this.getApplicationContext().getFilesDir());

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void BasicTest(){
        //try {
            //courseManager = new CourseManager(this.getApplicationContext().getFilesDir(),"");

            //Add a Course TEST202, 0.5, False, 85, 0, 0.
            //courseManager.add(new Course("TEST202", 0.5, false, 85, 0, 0));

            //Add an evaluation Assignment 1
            //courseManager.getCourse("TEST202").addEval(new Evaluation("Assignment 1", false, 50, 90, 0, 0), this.getApplicationContext().getFilesDir());

            //Add an evaluation Assignment 2
            //courseManager.getCourse("TEST202").addEval(new Evaluation("Assignment 2", false, 30, 80, 0, 0), this.getApplicationContext().getFilesDir());

            //Add an evaluation Exam
            //courseManager.getCourse("TEST202").addEval(new Evaluation("Exam", false, 20, 70, 0, 0), this.getApplicationContext().getFilesDir());

            Course current = courseManager.getCourse("TEST202");

            //Initial
            printCourse(current, "TEST202", 0.5, false, 85, 85, 0);

            Evaluation curreval = current.getEval("Assignment 1");

            printEval(current, curreval, "Assignment 1", 50, false, 90, 85, 0);

            curreval = current.getEval("Assignment 2");

            printEval(current, curreval, "Assignment 2", 30, false, 80, 85, 0);

            curreval = current.getEval("Exam");

            printEval(current, curreval, "Exam", 20, false, 70, 85, 0);


            //After completing assignment 1 and getting 80%
            current.getEval("Assignment 1").complete(80);
            System.out.println("\n\n After completing Assignment 1 and getting 80%\n");

            //Print all
            printCourse(current, "TEST202", 0.5, false, 85, 90, 80);

            curreval = current.getEval("Assignment 1");

            printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);

            curreval = current.getEval("Assignment 2");

            printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);

            curreval = current.getEval("Exam");

            printEval(current, curreval, "Exam", 20, false, 70, 90, 0);

            //Change the required grade for Assignment 2 to be 100%
            current.getEval("Assignment 2").getRequiredGrade().changeGrade(current.getEval("Assignment 2"), 10);
            System.out.println("\n\n After changing Assignment 2 Required to 100%\n");

            //Print all
            printCourse(current, "TEST202", 0.5, false, 85, 90, 80);

            curreval = current.getEval("Assignment 1");

            printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);

            curreval = current.getEval("Assignment 2");

            printEval(current, curreval, "Assignment 2", 30, false, 80, 100, 0);

            curreval = current.getEval("Exam");

            printEval(current, curreval, "Exam", 20, false, 70, 75, 0);

            //Complete Assignment 2 with a 95
            //I know this is bad but I am going to adjust the remaining grades here by setting them back to beenChanged = false.
            current.getEval("Assignment 2").complete(95);
            for (Evaluation e : current.getEvalList()){
                if (!e.getFinished()){
                    e.getRequiredGrade().beenChanged = false;
                }
            }

            //Print all
            printCourse(current, "TEST202", 0.5, false, 85, 82.5, 85.625);

            curreval = current.getEval("Assignment 1");

            printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);

            curreval = current.getEval("Assignment 2");

            printEval(current, curreval, "Assignment 2", 30, true, 80, 100, 95);

            curreval = current.getEval("Exam");

            printEval(current, curreval, "Exam", 20, false, 70, 82.5, 0);

            //Complete the final evaluation, an exam with an 85.
            current.getEval("Exam").complete(85);

            printCourse(current, "TEST202", 0.5, true, 85, 82.5, 85.5);

            curreval = current.getEval("Assignment 1");

            printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);

            curreval = current.getEval("Assignment 2");

            printEval(current, curreval, "Assignment 2", 30, true, 80, 100, 95);

            curreval = current.getEval("Exam");

            printEval(current, curreval, "Exam", 20, true, 70, 82.5, 85);


        //Test 3

        //Initial
        printCourse(current, "TEST202", 0.5, false, 85, 85, 0);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 10, false, 0, 85, 0);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 20, false, 0, 85, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 40, false, 0, 85, 0);
        System.out.println("\nCheck 1: " + check(current) + "\n");

        current.getEval("Assignment 1").complete(90);
        System.out.println("\n\n After completing Assignment 1 and getting 90%\n");

        //Print all
        printCourse(current, "TEST202", 0.5, false, 85, 90, 80);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 20, false, 70, 90, 0);
        System.out.println("\nCheck 2: " + check(current) + "\n");

        //After completing assignment 1 and getting 80%
        current.getEval("Assignment 2").getRequiredGrade().changeGrade(current.getEval("Assignment 2"), 10);
        System.out.println("\n\n After changing RequiredGrade for A2 to 10% higher\n");

        //Print all
        printCourse(current, "TEST202", 0.5, false, 85, 90, 80);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 20, false, 70, 90, 0);
        System.out.println("\nCheck 3: " + check(current) + "\n");

        current.getEval("Exam").getRequiredGrade().changeGrade(current.getEval("Exam"), -10);

        //Print all
        printCourse(current, "TEST202", 0.5, false, 85, 90, 80);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 20, false, 70, 90, 0);
        System.out.println("\nCheck 4: " + check(current) + "\n");

        current.getEval("Midterm").getRequiredGrade().changeGrade(current.getEval("Midterm"), -5);

        //Print all
        printCourse(current, "TEST202", 0.5, false, 85, 90, 80);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 20, false, 70, 90, 0);
        System.out.println("\nCheck 5: " + check(current) + "\n");

        current.getEval("Assignment 2").complete(80);

        //Print all
        printCourse(current, "TEST202", 0.5, false, 85, 90, 80);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 20, false, 70, 90, 0);
        System.out.println("\nCheck 6: " + check(current) + "\n");

        current.getEval("Midterm").complete(20);

        printCourse(current, "TEST202", 0.5, false, 85, 90, 80);
        curreval = current.getEval("Assignment 1");
        printEval(current, curreval, "Assignment 1", 50, true, 90, 85, 80);
        curreval = current.getEval("Assignment 2");
        printEval(current, curreval, "Assignment 2", 30, false, 80, 90, 0);
        curreval = current.getEval("Midterm");
        printEval(current, curreval, "Midterm", 30, false, 0, 85, 0);
        curreval = current.getEval("Exam");
        printEval(current, curreval, "Exam", 20, false, 70, 90, 0);
        System.out.println("\nCheck 7: " + check(current) + "\n");


        //} catch (IOException e) {
            //e.printStackTrace();
        //}
    }

    public void printCourse(Course current, String expectedName, double expectedCred, boolean expectedFin, double edGrade, double erGrade, double eaGrade){

        System.out.println("Testing Course:\n");
        System.out.println("Actual: " + current.getName() + ", " + current.getCredits() + ", " + current.getFinished() + ", " + current.getDesiredGrade().getGrade() + ", " + current.getRequiredGrade().getGrade() + ", " + current.getAcquiredGrade().getAvg(current) + "\n");
        System.out.println("Expected: " + expectedName + ", " + expectedCred + ", " + expectedFin + ", " + edGrade + ", " + erGrade + ", " + eaGrade + "\n");

    }

    public void printEval(Course course, Evaluation current, String expectedName, double expectedWeight, boolean expectedFin, double edGrade, double erGrade, double eaGrade){

        System.out.println("\nTesting Evaluation - " + current.getName() + ": \n");
        System.out.println("Actual: " + current.getName() + ", " + current.getWeight() + ", " + current.getFinished() + ", " + current.getDesiredGrade().getGrade() + ", " + current.getRequiredGrade().getGrade() + ", " + current.getAcquiredGrade().getGrade() + "\n");
        System.out.println("Expected: " + expectedName + ", " + expectedWeight + ", " + expectedFin + ", " + edGrade + ", " + erGrade + ", " + eaGrade + "\n");

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
        total = Math.round((total-course.getDesiredGrade().getGrade())*100.0)/100.0;
        return (total);

    }



}
