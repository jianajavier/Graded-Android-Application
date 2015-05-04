package com.jianajavier.gradedapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends ActionBarActivity {

    private CourseManager courseManager = CourseManager.getCourseManager();

    public static final String TAG = "AddCourseActivity";

    EditText et_name;
    EditText et_weight;
    EditText et_desired;
    EditText et_acquired;

    TextView tv_acquired;

    CheckBox cb_completed;

    String name;
    String weight;
    String desired;
    String acquired;

    String evalName;
    String strEvalWeight;
    Double eWeight;

    static List<Evaluation> evaluations = new ArrayList<Evaluation>();
    //Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        et_name = (EditText) findViewById(R.id.name_edit_text);
        et_weight = (EditText) findViewById(R.id.weight_edit_text);
        et_desired = (EditText) findViewById(R.id.desired_edit_text);
        et_acquired = (EditText) findViewById(R.id.acquired_edit_text);

        cb_completed = (CheckBox) findViewById(R.id.course_completed_checkbox);

        tv_acquired = (TextView) findViewById(R.id.acquired_text_view);


        //evaluations = new ArrayList<Evaluation>();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        weight = intent.getStringExtra("weight");
        desired = intent.getStringExtra("desired");
        acquired = intent.getStringExtra("acquired");

        //Need to accumulate the evaluations and then add them to the list
        evalName = intent.getStringExtra("evalName");
        strEvalWeight = intent.getStringExtra("evalWeight");
        //Will need for true/false completed

        eWeight = 0.0;

        if (!strEvalWeight.equals("")){
            eWeight = Double.parseDouble(strEvalWeight);
        }

        if (!evalName.equals("") && !strEvalWeight.equals("")) {
            //Course owner is null for now..
            Evaluation eval = new Evaluation(null, evalName, false, eWeight, 0, 0, 0);
            evaluations.add(eval);
        }

        et_name.setText(name);
        et_weight.setText(weight);
        et_desired.setText(desired);
        et_acquired.setText(acquired);
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
        String name = et_name.getText().toString();
        String strWeight = et_weight.getText().toString();
        String strDesired = et_desired.getText().toString();
        String strAcquired = et_acquired.getText().toString();

        double weight = 0;
        double desiredGrade = 0;
        double acquiredGrade = 0;

        boolean finished = false;

        if (cb_completed.isChecked()){
            finished = true;
        }

        Intent intent = new Intent(this, AddEvaluationActivity.class);

        Course course = new Course();

        intent.putExtra("name", name);
        intent.putExtra("weight", strWeight);
        intent.putExtra("desired", strDesired);
        intent.putExtra("acquired", strAcquired);

        intent.putExtra("course", course);

        startActivity(intent);
    }

    public void save(View view){

        addCourse();

        Intent intent = new Intent(this, CoursesActivity.class);

        startActivity(intent);

    }

    public void addCourse(){
        try{
            String name = et_name.getText().toString();
            String strWeight = et_weight.getText().toString();
            String strDesired = et_desired.getText().toString();
            String strAcquired = et_acquired.getText().toString();

            double weight = 0;
            double desiredGrade = 0;
            double acquiredGrade = 0;

            if (!strWeight.isEmpty()){
                weight = Double.parseDouble(strWeight);
            }

            String strDesiredGrade = et_desired.getText().toString();

            if (!strDesiredGrade.isEmpty()){
                desiredGrade = Double.parseDouble(strDesired);
            }

            String strAcquiredGrade = et_acquired.getText().toString();

            if (!strAcquiredGrade.isEmpty()){
                acquiredGrade = Double.parseDouble(strAcquired);
            }

            boolean finished = false;

            if (cb_completed.isChecked()){
                finished = true;
            }

            Course course = new Course(name, weight, finished, desiredGrade, 0.0, acquiredGrade);

            if (!evaluations.isEmpty()) {
                for (Evaluation eval : evaluations) {
                    eval.setOwner(course);
                    course.addEval(eval, null); //null parameter so it won't write to anything
                }
            }

            //Will wipe it out for next set of data
            evaluations = new ArrayList<Evaluation>();

            courseManager.add(course);

            //courseManager.add(course);

        } catch (IOException e){
            e.printStackTrace();

        } finally{

        }

    }

    public void showAcquired(View view){

        if(cb_completed.isChecked()){
            tv_acquired.setVisibility(View.VISIBLE);
            et_acquired.setVisibility(View.VISIBLE);
        }else{
            tv_acquired.setVisibility(View.INVISIBLE);
            et_acquired.setVisibility(View.INVISIBLE);
        }

    }


}
