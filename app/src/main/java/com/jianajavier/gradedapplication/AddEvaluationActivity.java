package com.jianajavier.gradedapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class AddEvaluationActivity extends ActionBarActivity {

    private CourseManager courseManager = CourseManager.getCourseManager();
    String name;
    String weight;
    String desired;
    String acquired;

    EditText et_evalName;
    EditText et_evalWeight;

    String evalName;
    String evalWeight;

    Course course;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evaluation);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        weight = intent.getStringExtra("weight");
        desired = intent.getStringExtra("desired");
        acquired = intent.getStringExtra("acquired");

        course = (Course) intent.getSerializableExtra("course");

        index = intent.getIntExtra("index", 99);

        et_evalName = (EditText) findViewById(R.id.evaluationNameEditText);
        et_evalWeight = (EditText) findViewById(R.id.weightEditText);

        evalName = et_evalName.getText().toString();
        evalWeight = et_evalWeight.getText().toString();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_evaluation, menu);
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

    public void saveEval(View view){
        Context context = getApplicationContext();

        evalName = et_evalName.getText().toString();
        evalWeight = et_evalWeight.getText().toString();

        if (!evalName.equals("") && !evalWeight.equals("")) {
            CharSequence text = "Evaluation added.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        if (course.getName() == null) {
            Intent intent = new Intent(this, AddCourseActivity.class);

            intent.putExtra("name", name);
            intent.putExtra("weight", weight);
            intent.putExtra("desired", desired);
            intent.putExtra("acquired", acquired);

            //Send back the evaluation name and weight for when they click save
            intent.putExtra("evalName", evalName);
            intent.putExtra("evalWeight", evalWeight);

            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CourseActivity.class);

            intent.putExtra("index", index);
            intent.putExtra("course", course);
            //Defaulting the grades to 0 for now
            Evaluation eval = new Evaluation(course, evalName, false, Double.parseDouble(evalWeight), 0.0, 0.0, 0.0);
            eval.setLineNumber(1 + course.getEvalList().size() + 1); //1 for course info, then size, then it is the line

            try{
                course.addEval(eval); //Should write to the text file and it does
            }
            catch (IOException e){
                e.printStackTrace();
            }

            startActivity(intent);
        }


    }
}
