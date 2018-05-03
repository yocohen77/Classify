package com.example.android.classify;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;


/**
 * @param hour holds the start and end times for formatting purposes
 * @param minute holds the minutes for formatting purposes
 * @param spinnerPos used to copy the spinner position in order to manipulate from within function
 * @param weightInput grabs the weight from an edit text field and passes it to an array adapter
 */

public class newClass extends AppCompatActivity {
     int hour, minute;
    String format, days, weightInput;
    int spinnerPos;
    ClassStructure newClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        final TextInputEditText className = (TextInputEditText) findViewById(R.id.editName);
        final TextView startTime = (TextView) findViewById(R.id.startTime);
        final TextView endTime = (TextView) findViewById(R.id.endTime);
        final TextView classDate = (TextView) findViewById(R.id.editDate);
        final TextInputEditText profName = (TextInputEditText) findViewById(R.id.editProf);
        final Button saveButton = (Button) findViewById(R.id.saveButton);

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
        // create a dialog for days of week picker
        classDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // create a dialog
                final CustomDayPicker cdp = new CustomDayPicker(newClass.this);
                cdp.show();
                cdp.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        days = cdp.days;
                        classDate.setText(days);
                    }
                });
                Log.i("ERROR CHECKING", "cdp.days from newClass is: " + cdp.days);

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
        ListView listView = findViewById(R.id.list_view);
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
                            }
                            if(spinnerPos == 2) {
                                RubricType newRubric = new RubricType("Exams", weightInput+"%");
                                listAdapter.add(newRubric);
                            }
                            if(spinnerPos == 3) {
                                RubricType newRubric = new RubricType("Final Exam", weightInput+"%");
                                listAdapter.add(newRubric);
                            }
                            if(spinnerPos == 4) {
                                RubricType newRubric = new RubricType("Homework", weightInput+"%");
                                listAdapter.add(newRubric);
                            }
                            if(spinnerPos == 5) {
                                RubricType newRubric = new RubricType("Midterm Exam", weightInput+"%");
                                listAdapter.add(newRubric);
                            }
                            if(spinnerPos == 6) {
                                RubricType newRubric = new RubricType("Projects", weightInput+"%");
                                listAdapter.add(newRubric);
                            }
                            if(spinnerPos == 7){
                                RubricType newRubric = new RubricType("Quizes", weightInput+"%");
                                listAdapter.add(newRubric);
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
                String updateName = className.getText().toString();
                String updateStart = startTime.getText().toString();
                String updateEnd = endTime.getText().toString();
                String updateDate = classDate.getText().toString();
                newClass = new ClassStructure(updateProf, updateName, updateStart, updateEnd, updateDate);
                Log.i("TESTING", "Data: " + updateProf + " " + updateName + " " + updateStart
                + " " + updateEnd + " " + updateDate);
            }
        });
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



