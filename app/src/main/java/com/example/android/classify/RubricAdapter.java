package com.example.android.classify;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RubricAdapter extends ArrayAdapter<RubricType> {
    ArrayList rubrics;
    public RubricAdapter(Context context, ArrayList<RubricType> rubrics){
        super(context, 0, rubrics);
        this.rubrics = rubrics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.i("TESTING", "Clicking on position: " + position);
        // get the data item for this position
        RubricType rubricType = getItem(position);
        // check if an existing view is being used, otherwise inflate the view
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_row, parent, false);
        }
        // Lookup value for data population
        TextView rubricName = (TextView) convertView.findViewById(R.id.rubric_type);
        TextView rubricWeight = (TextView) convertView.findViewById(R.id.weight);
        final ImageView deleteRubric = (ImageView) convertView.findViewById(R.id.delete_image);
        /*
         tag the button with its position in the view
         this is used to identify the position of the list for the deletion purposes
          */
        deleteRubric.setTag(position);
        // remove item from list adapter when clicking on delete icon
        deleteRubric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)deleteRubric.getTag();
                rubrics.remove(pos);
                RubricAdapter.this.notifyDataSetChanged();
            }
        });
        // Populate data
        rubricName.setText(rubricType.type);
        rubricWeight.setText(rubricType.weight);
        // return the completed view to render
        return convertView;
    }
}
