package com.example.android.classify;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.ToggleButton;

import java.util.ArrayList;




public class newClass extends AppCompatActivity {

    String TAG = "ClassifyNewClass";
    /**
     * @param hour holds the start and end times for formatting purposes
     * @param minute holds the minutes for formatting purposes
     * @param spinnerPos used to copy the spinner position in order to manipulate from within function
     * @param weightInput grabs the weight from an edit text field and passes it to an array adapter
     */
    int hour, minute;
    String format, weightInput;
    int spinnerPos;
    ClassStructure newClass = new ClassStructure();

    DatabaseReference databaseClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        // set up an up button
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolBar.setTitle("");
        setSupportActionBar(myToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        final TextInputEditText className = (TextInputEditText) findViewById(R.id.editName);
        final TextView startTime = (TextView) findViewById(R.id.startTime);
        final TextView endTime = (TextView) findViewById(R.id.endTime);
        //final TextView classDate = (TextView) findViewById(R.id.editDate);
        final TextInputEditText profName = (TextInputEditText) findViewById(R.id.editProf);
        final Button saveButton = (Button) findViewById(R.id.saveButton);

        databaseClasses = FirebaseDatabase.getInstance().getReference("classes");

        // create a fragment for time set
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(newClass.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(minute);
                        startTime.setText(hourOfDay + ":" + minute + format);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });
        // end time
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TimePickerDialog timePicker = new TimePickerDialog(newClass.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(minute);
                        endTime.setText(hourOfDay + ":" + minute + format);
                    }
                }, hour, minute, true);
                timePicker.show();

            }
        });

        final Spinner spinner = findViewById(R.id.rubric_spinner);
        // create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rubric_array, R.layout.spinner_item);
        // specify the layout to use to list the choices
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // apply adapter to spinner
        spinner.setAdapter(adapter);

        // data source for array list
        ArrayList<RubricType> arrayOfRubrics = new ArrayList<RubricType>();
        // create the adapter to convert the array to views
        final RubricAdapter listAdapter = new RubricAdapter(this, arrayOfRubrics);
        // attach
        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                spinnerPos = position;
                LayoutInflater inflater = LayoutInflater.from(newClass.this);
                View v = inflater.inflate(R.layout.weight_input, null);
                // alert dialog to input the weights
                AlertDialog.Builder builder = new AlertDialog.Builder(newClass.this);
                // don't show the view when first item is selected (the title)
                if (position != 0) {
                    builder.setTitle("Add a weight");
                    builder.setCancelable(false);
                    // inflate the view
                    builder.setView(v);

                    final TextInputEditText input = (TextInputEditText) v.findViewById(R.id.input);

                    // builder buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            weightInput = input.getText().toString();
                            if(spinnerPos == 1) {
                                RubricType newRubric = new RubricType("Attendance",weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setAttendanceWeight(Integer.parseInt(weightInput));
                            }
                            if(spinnerPos == 2) {
                                RubricType newRubric = new RubricType("Exams", weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setExamWeight(Integer.parseInt(weightInput));
                            }
                            if(spinnerPos == 3) {
                                RubricType newRubric = new RubricType("Final Exam", weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setFinalWeight(Integer.parseInt(weightInput));
                            }
                            if(spinnerPos == 4) {
                                RubricType newRubric = new RubricType("Homework", weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setHomeworkWeight(Integer.parseInt(weightInput));
                            }
                            if(spinnerPos == 5) {
                                RubricType newRubric = new RubricType("Midterm Exam", weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setMidtermWeight(Integer.parseInt(weightInput));
                            }
                            if(spinnerPos == 6) {
                                RubricType newRubric = new RubricType("Projects", weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setProjectWeight(Integer.parseInt(weightInput));
                            }
                            if(spinnerPos == 7){
                                RubricType newRubric = new RubricType("Quizes", weightInput+"%");
                                listAdapter.add(newRubric);
                                if (weightInput != null && !weightInput.isEmpty())
                                    newClass.setQuizWeight(Integer.parseInt(weightInput));
                            }
                            // set spinner back to default value (head)
                            spinner.setSelection(0);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            spinner.setSelection(0);
                            hideKeyboard();
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                hideKeyboard();
            }
            // no action needed other than setting the spinner position back to 0
            public void onNothingSelected(AdapterView<?> arg0){
                spinner.setSelection(0);
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateProf = profName.getText().toString();
                String updateSubject = className.getText().toString();
                String updateStart = startTime.getText().toString();
                String updateEnd = endTime.getText().toString();
                String updateDate = getCheckedDays();
                newClass.setProf(updateProf);
                newClass.setTimes(updateStart, updateEnd);
                newClass.setSubject(updateSubject);
                newClass.setDays(updateDate);

                // create unique id for this class to be used in the database
                String classId = databaseClasses.push().getKey();
                newClass.setChildId(classId);
                databaseClasses.child(classId).setValue(newClass);
                Log.i(TAG, "DatabaseClasses added with ID: " + classId);
                startActivity(new Intent(newClass.this, MainActivity.class));
                finish();

            }
        });
    }

    // get the chosen dates from the multi-group weekday buttons
    public String getCheckedDays(){
        ToggleButton daysOfWeek[] = new ToggleButton[7];
        String days, temp = "";
        daysOfWeek[0] = findViewById(R.id.sun);
        daysOfWeek[1] = findViewById(R.id.mon);
        daysOfWeek[2] = findViewById(R.id.tue);
        daysOfWeek[3] = findViewById(R.id.wed);
        daysOfWeek[4] = findViewById(R.id.thu);
        daysOfWeek[5] = findViewById(R.id.fri);
        daysOfWeek[6] = findViewById(R.id.sat);
        for (int i = 0; i < 7; ++i){
            if (daysOfWeek[i].isChecked()){
                if (i == 0)
                    temp += "Sun, ";
                if (i == 1)
                    temp += "Mon, ";
                if (i == 2)
                    temp += "Tue, ";
                if (i == 3)
                    temp += "Wed, ";
                if (i == 4)
                    temp += "Thu, ";
                if (i == 5)
                    temp += "Fri, ";
                if (i == 6)
                    temp += "Sat, ";
            }
        }
        days = temp.substring(0, temp.length()-2);
        return days;
    }

    // auxiliary functions
    public void selectedTimeFormat(int minute) {
        if (minute == 0)
            format = "0";
        else
            format = "";
    }
    private void hideKeyboard() {
        // check if no view has focus
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    private void showKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}



