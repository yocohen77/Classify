package com.example.android.classify;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class classView extends AppCompatActivity {

    final private String TAG = "TestingClassView";

    ClassStructure mClass;
    TextView subject, professor;
    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;
    List<String> expListParent;
    HashMap<String, List<GradeType>> expListChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        // get the class that was passed from main activity
        Intent i = getIntent();
        mClass = (ClassStructure) i.getSerializableExtra("passedClass");
        Log.d(TAG, "onCreate:true, mClass: " + mClass.getSubject());
        // initialize lists for expanding list view and send to adapter
        expListParent = new ArrayList<String>();
        expListChild = new HashMap<String, List<GradeType>>();

        // set up an up button
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolBar.setTitle("");
        setSupportActionBar(myToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // set class variables
        String timeDate = mClass.getTimeStart() + " - " + mClass.getTimeEnd() + ", " +
                mClass.getDays();
        TextView txtDates = findViewById(R.id.txt_dates);
        txtDates.setText(timeDate);
        // hide list's indicator
        expListView = (ExpandableListView) findViewById(R.id.exp_list);
        expListView.setGroupIndicator(null);

        // populate class variables
        subject = findViewById(R.id.class_view_subject);
        professor = findViewById(R.id.class_view_professor);
        subject.setText(mClass.getSubject());
        professor.setText(mClass.getProfName());

        // populate the parents for the expandable list
        generateGradingScheme();
        expListAdapter = new ExpandableListAdapter(this, expListParent, expListChild);
        expListView.setAdapter(expListAdapter);
        // add a new grade score click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                final String parentTitle = (String) expListAdapter.getGroup(groupPosition);
                Log.i(TAG, "setOnGroupClickListener:true");
                LayoutInflater inf = LayoutInflater.from(classView.this);
                View dialogView = inf.inflate(R.layout.builder_add_grade_score, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(classView.this, R.style.AlertDialogTheme);
                builder.setCancelable(false);
                builder.setView(dialogView);
                final EditText input = dialogView.findViewById(R.id.editTextScore);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newGradeScore = input.getText().toString();
                        generateScores(parentTitle, newGradeScore);
                    }
                });


                builder.show();

                return true;
            }
        });


    }
    // populates the parent titles in the expandable list view
    private void generateGradingScheme(){
        if (mClass.getAttendanceWeight() > 0){
            expListParent.add("Attendance");
        }
        if (mClass.getExamWeight() > 0){
            expListParent.add("Exams");
        }
        if (mClass.getFinalWeight() > 0){
            expListParent.add("Final Exam");
        }
        if (mClass.getHomeworkWeight() > 0){
            expListParent.add("Homework");
        }
        if (mClass.getMidtermWeight() > 0){
            expListParent.add("Midterm Exams");
        }
        if (mClass.getProjectWeight() > 0){
            expListParent.add("Projects");
        }
        if (mClass.getQuizWeight() > 0){
            expListParent.add("Quizzes");
        }
    }
    // constructs the childs inside the expandable list view
    private void generateScores(String parentTitle, String input){
        if (parentTitle.equals("Attendance")){
            Log.i(TAG, "generateScores:Attendance:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.attendance.size() + 1, input);
            mClass.attendance.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.attendance);
            expListAdapter = new ExpandableListAdapter(this, expListParent, expListChild);
            expListView.setAdapter(expListAdapter);
        }
        else if (parentTitle.equals("Exams")){
            Log.i(TAG, "generateScores:Exams:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.exams.size() + 1, input);
            mClass.exams.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.exams);
            expListAdapter = new ExpandableListAdapter(classView.this, expListParent, expListChild);
            expListAdapter.notifyDataSetChanged();
            expListView.setAdapter(expListAdapter);
        }
        else if (parentTitle.equals("Final Exam")){
            Log.i(TAG, "generateScores:Final Exam:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.finals.size() + 1, input);
            mClass.finals.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.finals);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Homework")){
            Log.i(TAG, "generateScores:Homework:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.homework.size() + 1, input);
            mClass.homework.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.homework);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Midterm Exams")){
            Log.i(TAG, "generateScores:Midterms:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.midterms.size() + 1, input);
            mClass.midterms.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.midterms);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Projects")){
            Log.i(TAG, "generateScores:Projects:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.projects.size() + 1, input);
            mClass.projects.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.projects);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Quizzes")){
            Log.i(TAG, "generateScores:Quizzes:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            GradeType grade = new GradeType(parentTitle + " " + mClass.quizzes.size() + 1, input);
            mClass.quizzes.add(grade);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.quizzes);
            expListAdapter.notifyDataSetChanged();
        }
    }

    // create menu buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // edit button
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_class, menu);
        return true;
    }
    // handle menu click events
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_edit:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
