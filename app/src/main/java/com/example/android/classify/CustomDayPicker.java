package com.example.android.classify;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.util.Log;
import android.widget.ToggleButton;

/**
 * Created by yocoh on 4/8/2018.
 */

public class CustomDayPicker extends Dialog {

    public Activity c;
    public Dialog d;
    ToggleButton dow[] = new ToggleButton[7];
    Button btn;
    String days = "";

    public CustomDayPicker(Activity a) {
        super(a);
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_day_picker);
        dow[0] = (ToggleButton) findViewById(R.id.mon);
        dow[1] = (ToggleButton) findViewById(R.id.tue);
        dow[2] = (ToggleButton) findViewById(R.id.wed);
        dow[3] = (ToggleButton) findViewById(R.id.thu);
        dow[4] = (ToggleButton) findViewById(R.id.fri);
        dow[5] = (ToggleButton) findViewById(R.id.sat);
        dow[6] = (ToggleButton) findViewById(R.id.sun);
        btn = (Button) findViewById(R.id.dialog_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = "";
                for (int i = 0; i < 7; ++i){
                    if (dow[i].isChecked()){
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
                Log.i("ERROR CHECKING","days length is: " + days.length() + " and days are: " + days);
                dismiss();
            }
        });

    }



}
