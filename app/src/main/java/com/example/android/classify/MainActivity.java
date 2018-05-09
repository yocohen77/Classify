package com.example.android.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.ListView;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    /*
     * @param classListView the list view that holds the classes
     * @param databaseClasses reference to the firebase database
     * @param classList a list to hold all classes added to the database
     */
    ListView classListView ;
    DatabaseReference databaseClasses;
    List<ClassStructure> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseClasses = FirebaseDatabase.getInstance().getReference("classes");
        classList = new ArrayList<>();
        FloatingActionButton classButton = (FloatingActionButton) findViewById(R.id.menu_class);
        FloatingActionButton reminderButton = (FloatingActionButton) findViewById(R.id.menu_reminder);
        classListView = findViewById(R.id.listView);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // set a click listener for menu buttons
        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent newClassIntent = new Intent(MainActivity.this, newClass.class);
                startActivity(newClassIntent);
            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent newReminderIntent = new Intent(MainActivity.this, newReminder.class);
                startActivity(newReminderIntent);
            }
        });

    }

    // attach the data event listener to the database
    @Override
    protected void onStart() {
        super.onStart();
        databaseClasses.addValueEventListener(new ValueEventListener() {
            @Override
            // executed whenever data is changed in the database
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Testing", "databaseClasses:onDataChange:true");
                // clear the list if previously loaded
                classList.clear();
                // grab the values from the database
                for (DataSnapshot classSnapshot : dataSnapshot.getChildren()){
                    ClassStructure c = classSnapshot.getValue(ClassStructure.class);
                    Log.d("Testing", "c subject is: " + c.getSubject());
                    classList.add(c);
                }
                ClassAdapter adapter = new ClassAdapter(MainActivity.this, classList);
                classListView.setAdapter(adapter);
            }
            // executed when error occured
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Testing", "databaseClasses:onCancelled:true");

            }
        });
    }

}
