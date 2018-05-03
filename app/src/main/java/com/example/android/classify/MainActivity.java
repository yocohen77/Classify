package com.example.android.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton classButton = (FloatingActionButton) findViewById(R.id.menu_class);
        FloatingActionButton reminderButton = (FloatingActionButton) findViewById(R.id.menu_reminder);

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


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
