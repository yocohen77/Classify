package com.example.android.classify;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class classView extends AppCompatActivity {

    final private String TAG = "TestingClassView";

    ClassStructure mClass;
    TextView txtSubject, txtProf, txtLocation, txtGrade, txtDates;
    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;
    List<String> expListParent;
    HashMap<String, List<String>> expListChild;
    DatabaseReference classRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);
        // get database reference
        classRef = FirebaseDatabase.getInstance().getReference("classes");
        // get the class that was passed from main activity
        Intent i = getIntent();
        mClass = (ClassStructure) i.getSerializableExtra("passedClass");
        Log.d(TAG, "onCreate:true, mClass: " + mClass.getSubject());
        // initialize lists for expanding list view and send to adapter
        expListParent = new ArrayList<String>();
        expListChild = new HashMap<String, List<String>>();

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

        // attach list's indicator
        expListView = (ExpandableListView) findViewById(R.id.exp_list);

        // populate class variables
        txtSubject = findViewById(R.id.class_view_subject);
        txtProf = findViewById(R.id.class_view_professor);
        txtGrade = findViewById(R.id.txt_grade);
        txtDates = findViewById(R.id.txt_dates);
        txtLocation = findViewById(R.id.txt_location);

        txtSubject.setText(mClass.getSubject());
        txtProf.setText(mClass.getProfName());
        txtDates.setText(timeDate);
        if (mClass.getWeightedGrade() == 0)
            txtGrade.setText("100%");
        else
            txtGrade.setText(String.valueOf(mClass.getWeightedGrade()) + "%");

        // populate the parents and childs for the expandable list
        updateGradingScheme();
        updateGrades();
        expListAdapter = new ExpandableListAdapter(this, expListParent, expListChild);
        expListView.setAdapter(expListAdapter);
        expListAdapter.mListener = new OnImageClickListener() {
            @Override
            public void OnImageClicked(int pos) {
                final String parentTitle = (String) expListAdapter.getGroup(pos);
                Log.i(TAG, "setOnGroupClickListener:true" +  expListAdapter.getGroupId(pos));
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
                        // update grades
                        mClass.calculateGrade();
                        if (mClass.getWeightedGrade() == 0)
                            txtGrade.setText("100%");
                        else
                            txtGrade.setText(String.valueOf(mClass.getWeightedGrade()) + "%");
                        // update database
                        classRef.child(mClass.getChildId()).setValue(mClass);
                    }
                });
                builder.show();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        // expand the views
        for (int j = 0; j < expListAdapter.getGroupCount(); ++j){
            Log.i(TAG, "groupCount: " + expListAdapter.getChildrenCount(j));
            // check if group has children, if not -- skip
            //if (expListAdapter.getChildrenCount(j) != 0)
            expListView.expandGroup(j);
        }
    }

    // populates the parent titles in the expandable list view
    private void updateGradingScheme(){
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

    // updats the list of grades with expListChild
    public void updateGrades(){
        for (int i = 0; i < expListParent.size(); ++i) {
            String parentTitle = expListParent.get(i);
            if (parentTitle.equals("Attendance")){
                // add to hashmap and update list
                expListChild.put(parentTitle, mClass.attendanceList);
            }
            else if (parentTitle.equals("Exams")){
                expListChild.put(parentTitle, mClass.examsList);
            }
            else if (parentTitle.equals("Final Exam")){
                expListChild.put(parentTitle, mClass.finalList);
            }
            else if (parentTitle.equals("Homework")){
                expListChild.put(parentTitle, mClass.homeworkList);
            }
            else if (parentTitle.equals("Midterm Exams")){
                expListChild.put(parentTitle, mClass.midtermsList);
            }
            else if (parentTitle.equals("Projects")){
                expListChild.put(parentTitle, mClass.projectsList);
            }
            else if (parentTitle.equals("Quizzes")){
                expListChild.put(parentTitle, mClass.quizzesList);
            }
        }
    }
    // constructs the childs inside the expandable list view
    private void generateScores(String parentTitle, String input){
        if (parentTitle.equals("Attendance")){
            Log.i(TAG, "generateScores:Attendance:true");
            // add the type of scheme plus the number of occurrences, and the score (input)
            mClass.attendanceList.add(input);
            // add to hashmap and update list
            expListChild.put(parentTitle, mClass.attendanceList);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Exams")){
            Log.i(TAG, "generateScores:Exams:true");
            mClass.examsList.add(input);
            expListChild.put(parentTitle, mClass.examsList);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Final Exam")){
            Log.i(TAG, "generateScores:Final Exam:true");
            mClass.finalList.add(input);
            expListChild.put(parentTitle, mClass.finalList);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Homework")){
            Log.i(TAG, "generateScores:Homework:true");
            mClass.homeworkList.add(input);
            expListChild.put(parentTitle, mClass.homeworkList);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Midterm Exams")){
            Log.i(TAG, "generateScores:Midterms:true");
            mClass.midtermsList.add(input);
            expListChild.put(parentTitle, mClass.midtermsList);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Projects")){
            Log.i(TAG, "generateScores:Projects:true");
            mClass.projectsList.add(input);
            expListChild.put(parentTitle, mClass.projectsList);
            expListAdapter.notifyDataSetChanged();
        }
        else if (parentTitle.equals("Quizzes")){
            Log.i(TAG, "generateScores:Quizzes:true");
            mClass.quizzesList.add(input);
            expListChild.put(parentTitle, mClass.quizzesList);
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
