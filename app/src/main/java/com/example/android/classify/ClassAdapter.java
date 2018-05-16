package com.example.android.classify;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<ClassStructure> {

    private List<ClassStructure> classList;

    public ClassAdapter(Activity context, List<ClassStructure> classList){
        super(context, 0, classList);
        this.classList = classList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for this pos
        ClassStructure c = getItem(position);
        // check if existing view is used, otherwise inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.classes_list_view_row, parent, false);
        }
        // data population
        TextView className = convertView.findViewById(R.id.txt_class);
        TextView grade = convertView.findViewById(R.id.txt_grade);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        // populate data
        className.setText(c.getSubject());
        if (c.getLetterGrade().equals(""))
            grade.setText("A+");
        else
            grade.setText(c.getLetterGrade());
        if (c.getRating() == 0)
            ratingBar.setRating(5.0f);
        else
            ratingBar.setRating(c.getRating());
        return convertView;
    }
}
