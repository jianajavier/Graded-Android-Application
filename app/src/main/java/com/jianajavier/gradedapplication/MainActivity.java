package com.jianajavier.gradedapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";

    public CourseManager courseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            courseManager = new CourseManager(this.getApplicationContext().getFilesDir(),"");

            

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * For printing out course
     * @param current
     * @param expectedName
     * @param expectedCred
     * @param expectedFin
     * @param edGrade
     * @param erGrade
     * @param eaGrade
     */
    public void printCourse(Course current, String expectedName, double expectedCred, boolean expectedFin, double edGrade, double erGrade, double eaGrade){

        System.out.println("Testing Course:\n");
        System.out.println("Actual: " + current.getName() + ", " + current.getCredits() + ", " + current.getFinished() + ", " + current.getDesiredGrade().getGrade() + ", " + current.getRequiredGrade().getGrade() + ", " + current.getAcquiredGrade().getAvg(current) + "\n");
        //System.out.println("Expected: " + expectedName + ", " + expectedCred + ", " + expectedFin + ", " + edGrade + ", " + erGrade + ", " + eaGrade + "\n");

    }

    /**
     * For printing out evaluation
     * @param course
     * @param current
     * @param expectedName
     * @param expectedWeight
     * @param expectedFin
     * @param edGrade
     * @param erGrade
     * @param eaGrade
     */
    public void printEval(Course course, Evaluation current, String expectedName, double expectedWeight, boolean expectedFin, double edGrade, double erGrade, double eaGrade){
        System.out.println("\nTesting Evaluation - " + current.getName() + ": \n");
        System.out.println("Actual: " + current.getName() + ", " + current.getWeight() + ", " + current.getFinished() + ", " + current.getDesiredGrade().getGrade() + ", " + current.getRequiredGrade().getGrade() + ", " + current.getAcquiredGrade().getGrade() + "\n");
        //System.out.println("Expected: " + expectedName + ", " + expectedWeight + ", " + expectedFin + ", " + edGrade + ", " + erGrade + ", " + eaGrade + "\n");
    }

    /**
     * Check if the grades add up to the desired
     * @param course
     * @return total
     */
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

    /**
     * Responds to the courses text field
     * @param view
     */
    public void courses(View view){
        //Not sure if should put it in try block

        //Go to courses activity
        Intent intent = new Intent(this, CoursesActivity.class);
        //intent.putExtra("courseManager", courseManager);
        //Log.i(TAG, "courseManager tossed into Extra");

        startActivity(intent);

    }


}
