package com.jianajavier.gradedapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;


public class CourseActivity extends ActionBarActivity {

    private CourseManager courseManager = CourseManager.getCourseManager();

    public static final String TAG = "CourseActivity";

    TextView tv_desired;
    TextView tv_acquired;
    TextView tv_required;

    TextView tv_coursename;

    int index;

    Course course = new Course();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent = getIntent();

        tv_desired = (TextView) findViewById(R.id.desired_grade_text_view);
        tv_acquired = (TextView) findViewById(R.id.acquired_grade_text_view);
        tv_required = (TextView) findViewById(R.id.required_grade_text_view);

        tv_coursename = (TextView) findViewById(R.id.course_name_text_view);

        course = (Course) intent.getSerializableExtra("course");
        index = intent.getIntExtra("index", 99);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    public void onResume(){
        super.onResume();

        Intent intent = getIntent();

        index = intent.getIntExtra("index", 99);
        course = (Course) intent.getSerializableExtra("course");

        String[] evalList = null;

        if (courseManager.getCourseList().get(index) != null && course.getName() == null){
            course = courseManager.getCourseList().get(index);
        }

        tv_desired.setText(String.valueOf(course.getDesiredGrade().getGrade()));
        tv_acquired.setText(String.valueOf(course.getAcquiredGrade().getGrade()));
        tv_required.setText(String.valueOf(course.getRequiredGrade().getGrade()));

        tv_coursename.setText(String.valueOf(course.getName()));

        //Sets size of array for grid view list
        evalList = new String[course.getEvalList().size()];
        int i = 0;



        for (Evaluation eval : course.getEvalList()){
            Log.i(TAG, "Evaluation name: " + eval.getName());
            evalList[i] = eval.getName() + "\ndesired: " + eval.getDesiredGrade().getGrade() + "\nrequired: "+ eval.getRequiredGrade().getGrade() + "\nacquired: " + eval.getAcquiredGrade().getGrade();
            i++;
        }

        GridView gridView = (GridView) findViewById(R.id.gridView);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseList);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            }

        });

        gridView.setAdapter(new EvalCustomGridViewAdapter(this, android.R.layout.simple_list_item_1, evalList));


        Log.i(TAG, "END OF ONRESUME");

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

    public void addEval(View view){
        Intent intent = new Intent(CourseActivity.this, AddEvaluationActivity.class);

        intent.putExtra("course", course);

        //So there isn't an error when trying to get the intent.
        String name = "", weight= "", desired= "", acquired = "", evalName = "", evalWeight = "";

        intent.putExtra("name", name);
        intent.putExtra("weight", weight);
        intent.putExtra("desired", desired);
        intent.putExtra("acquired", acquired);

        intent.putExtra("index", index);

        startActivity(intent);
    }

}
