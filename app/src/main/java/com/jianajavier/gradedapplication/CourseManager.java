package com.jianajavier.gradedapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CourseManager holds a list of all of the courses the student has.
 * Created by jianajavier on 15-04-17.
 */
public class CourseManager implements Serializable {

//Variables-----------------------------------------------------------------------------------------

    public static final CourseManager courseManager = new CourseManager();
    public static File dirPath;
    public BufferedWriter writer;

    /**
     * The serialization ID for the PatientManager so it can be passed around.
     */
    private static final long serialVersionUID = 2715448915207696794L;

    /**
     * List of all the courses student is taking.
     */
    private List<Course> courseList = new ArrayList<Course>();

    /**
     * Number of courses student is taking.
     */
    private int numCourses;

//Constructor---------------------------------------------------------------------------------------

    public CourseManager(File dPath, String filename) throws IOException {
        this.dirPath = dPath;
        File dir = new File(dirPath, filename);
        File [] directoryList = dir.listFiles();

        numCourses = 0;

        if (directoryList.length != 0){
            for(File child : directoryList){
                String name = child.getName(); //To get it without extension
                int pos = name.lastIndexOf(".");

                if (pos > 0){
                    name = name.substring(0, pos);
                }
                addToCourseList(child.getPath(), name);
            }
        } else {
            //Error handle when dir isn't a directory
        }

        getCourseManager().courseList = courseList;
    }

    public CourseManager(){}

//Functions-----------------------------------------------------------------------------------------

    /**
     * Reads from name text file and add course and evaluations to courseList.
     * @param filePath
     * @param name
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void addToCourseList(String filePath, String name) throws FileNotFoundException,
            IOException{
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;

        //First line will be information about the Course
        record = scanner.nextLine().split(",");

        boolean coursefin = Boolean.parseBoolean(record[0]); //Boolean
        Double cred = Double.parseDouble(record[1]); //Double
        Double cdesired = Double.parseDouble(record[2]); //Double
        Double crequired = Double.parseDouble(record[3]); //Double
        Double cacquired = Double.parseDouble(record[4]); //Double

        Course newCourse = new Course(name, cred, coursefin, cdesired, crequired, cacquired);
        newCourse.dirPath = dirPath;


        courseList.add(newCourse);

        numCourses += 1;

        int lineNumber = 1;

        while(scanner.hasNextLine()){
            //The rest are evaluations
            record = scanner.nextLine().split(",");

            String ename = record[0]; //Don't wanna separate name
            boolean evalfin = Boolean.parseBoolean(record[1]);
            double weight = Double.parseDouble(record[2]);
            double edesired = Double.parseDouble(record[3]);
            double erequired = Double.parseDouble(record[4]);
            double eacquired = Double.parseDouble(record[5]);

            lineNumber += 1;

            Evaluation newEval = new Evaluation(newCourse, ename, evalfin, weight, edesired, erequired,
                    eacquired);

            newEval.setLineNumber(lineNumber);

            //It was newCourse.addEval(newEval, null) before but I'm not sure if it makes a difference.
            newCourse.addEval(newEval, null);
        }

        scanner.close();
    }

    /**
     * Adds a new course text file and writes the first line about the course.
     * @param course
     * @throws IOException
     */
    public void add(Course course) throws IOException {
        courseList.add(course);

        //Want to create a new text file for the course
        File file = new File(dirPath, course.getName() + ".txt");

        if (!file.exists()) {
            file.createNewFile();

            String data;

            data = course.getFinished() + "," + course.getCredits() + "," + course.getDesiredGrade()
                    .getGrade() + "," + course.getRequiredGrade().getGrade() + "," + course
                    .getAcquiredGrade().getGrade();

            File outputFile = new File(dirPath, course.getName() + ".txt");
            writer = new BufferedWriter(new FileWriter(outputFile, true));
            writer.write(data);
            writer.newLine();

            if (!course.getEvalList().isEmpty()){
                for (Evaluation eval : course.getEvalList()) {
                    data = eval.getName() + "," + eval.getFinished() + "," + eval.getWeight() + "," +
                            eval.getDesiredGrade().getGrade() + "," + eval.getRequiredGrade().getGrade() +
                            "," + eval.getAcquiredGrade().getGrade();

                    writer.write(data);
                    writer.newLine();
                }
            }


            writer.close();

            numCourses += 1;
        }
    }

    /**
     * Returns string of courses in CourseManager
     * @return String of courses in CourseManager
     */
    @Override
    public String toString(){
        return "CourseManager [course=" + courseList + "]";
    }

//Getter Functions----------------------------------------------------------------------------------

    /**
     * Return courseManager. Singleton design pattern.
     * @return courseManager
     */
    public static CourseManager getCourseManager() {
        return courseManager;
    }

    /**
     * Return the courseList.
     * @return courseList
     */
    public List<Course> getCourseList(){
        return courseList;
    }

    /**
     * Return the course corresponding with name. Return null if not found.
     * @param name
     * @return course
     */
    public Course getCourse(String name){
        for (Course course : courseList){
            if (course.getName() == name){
                return course;
            }
        }
        return null;
    }

    /**
     * Return number of courses in courseList.
     * @return number of Courses
     */
    public int getNumCourses(){
        return numCourses;
    }

}
