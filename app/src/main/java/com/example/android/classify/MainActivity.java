package com.example.android.classify;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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
    FloatingActionButton classButton;
    FloatingActionButton reminderButton;
    FloatingActionMenu menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseClasses = FirebaseDatabase.getInstance().getReference("classes");
        classList = new ArrayList<>();
        classButton = (FloatingActionButton) findViewById(R.id.menu_class);
        reminderButton = (FloatingActionButton) findViewById(R.id.menu_reminder);
        menuButton = findViewById(R.id.menu);
        classListView = findViewById(R.id.listView);
        classListView.setEmptyView(findViewById(R.id.emptyview));
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
        redrawLayout();
        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassStructure c = classList.get(position);
                Intent classIntent = new Intent(MainActivity.this, classView.class);
                classIntent.putExtra("passedClass", c);
                startActivity(classIntent);
            }
        });
    }

    /* when a class is clicked, grab the class object from the list
     * and launch a new activity with the object
     */


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
                    Log.d("Testing", "c time is: " + c.getTimeStart() + " - " + c.getTimeEnd());
                    classList.add(c);
                }
                ClassAdapter adapter = new ClassAdapter(MainActivity.this, classList);
                classListView.setAdapter(adapter);
                redrawLayout();
            }
            // executed when error occured
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Testing", "databaseClasses:onCancelled:true");

            }
        });
    }

    // if the list view adapter is empty, restyle the layout
    private void redrawLayout(){
        if (classListView.getCount() == 0){
            //this.getSupportActionBar().hide();
            // set colors for menu button
            menuButton.setMenuButtonColorNormal(Color.WHITE);
            // change the color of the icon inside the menu button
            menuButton.getMenuIconView().setColorFilter(Color.BLUE);
            // set colors for floating buttons
            classButton.setColorNormal(Color.WHITE);
            reminderButton.setColorNormal(Color.WHITE);
            // set icon colors for floating buttons
            Drawable myFabSrc = ContextCompat.getDrawable(this, R.drawable.ic_class_icon);
            Drawable whiteIcon = myFabSrc.getConstantState().newDrawable();
            whiteIcon.mutate().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
            classButton.setImageDrawable(whiteIcon);
            myFabSrc = ContextCompat.getDrawable(this, R.drawable.ic_reminder_icon);
            whiteIcon = myFabSrc.getConstantState().newDrawable();
            whiteIcon.mutate().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
            reminderButton.setImageDrawable(whiteIcon);

        }
        else if (classListView.getCount() > 1) {
            //this.getSupportActionBar().show();
            // set menu color back to default
            menuButton.setMenuButtonColorNormalResId(R.color.colorPrimaryDark);
            // change menu icon color back to white
            menuButton.getMenuIconView().setColorFilter(Color.WHITE);
            // same for floating buttons
            classButton.setColorNormalResId(R.color.colorPrimaryDark);
            reminderButton.setColorNormalResId(R.color.colorPrimaryDark);
            // icons
            Drawable myFabSrc = ContextCompat.getDrawable(this, R.drawable.ic_class_icon);
            Drawable blueIcon = myFabSrc.getConstantState().newDrawable();
            blueIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            classButton.setImageDrawable(blueIcon);
            myFabSrc = ContextCompat.getDrawable(this, R.drawable.ic_reminder_icon);
            blueIcon = myFabSrc.getConstantState().newDrawable();
            blueIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            reminderButton.setImageDrawable(blueIcon);
        }
    }

}
