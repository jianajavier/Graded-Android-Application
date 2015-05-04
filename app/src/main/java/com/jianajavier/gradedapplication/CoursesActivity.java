package com.jianajavier.gradedapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;


public class CoursesActivity extends ActionBarActivity{

    private CourseManager courseManager = CourseManager.getCourseManager();

    public static final String TAG = "CoursesActivity";

    Course course = new Course();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        Intent intent = getIntent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_courses, menu);
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

    public void onResume(){
        super.onResume();

        Intent intent = getIntent();

        String[] courseList = null;

        //Sets size of array for grid view list
        courseList = new String[courseManager.getCourseList().size()];
        int i = 0;



        for (Course course : courseManager.getCourseList()){
            Log.i(TAG, "Course name: " + course.getName());
            courseList[i] = course.getName() + "\ndesired: " + course.getDesiredGrade().getGrade() + "\nrequired: "+ course.getRequiredGrade().getGrade() + "\nacquired: " + course.getAcquiredGrade().getGrade();
            i++;
        }

        GridView gridView = (GridView) findViewById(R.id.gridView2);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseList);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                final Intent intent = new Intent(getApplicationContext(), CourseActivity.class);

                intent.putExtra("index", position);
                intent.putExtra("course", course);

                //Need some way to indicate which course was chosen.

                startActivity(intent);
            }

        });

        gridView.setAdapter(new CustomGridViewAdapter(this, android.R.layout.simple_list_item_1, courseList));


        Log.i(TAG, "END OF ONRESUME");

    }

    /**
     * Navigate to AddCourseActivity
     * @param view
     */
    public void addCourse(View view){
        Intent intent = new Intent(this, AddCourseActivity.class);

        String name = "", weight= "", desired= "", acquired = "", evalName = "", evalWeight = "";

        intent.putExtra("name", name);
        intent.putExtra("weight", weight);
        intent.putExtra("desired", desired);
        intent.putExtra("acquired", acquired);

        //Send back the evaluation name and weight for when they click save
        intent.putExtra("evalName", evalName);
        intent.putExtra("evalWeight", evalWeight);


        startActivity(intent);
    }

}
