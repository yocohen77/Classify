package com.example.android.classify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listParent;
    private HashMap<String, List<String>> _listChild;
    public OnImageClickListener mListener;

    public ExpandableListAdapter(Context context, List<String> listParent,
                              HashMap<String, List<String>> listChild){
        this._context = context;
        this._listParent = listParent;
        this._listChild = listChild;
    }
    @Override
    public int getGroupCount() {
        return _listParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (_listChild.get(_listParent.get(groupPosition)) == null)
            return 0;
        return _listChild.get(_listParent.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listParent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _listChild.get(_listParent.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final int groupId = groupPosition;
        String parentTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.parent_group_row, null);
        }

        TextView txtRubricType = convertView.findViewById(R.id.txt_rubric_type);
        ImageView groupHolder = convertView.findViewById(R.id.img_group_holder);
        ImageView imgAddChild = convertView.findViewById(R.id.img_add_grade);

        imgAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // implement custom interface
                mListener.OnImageClicked(groupId);
            }
        });

        txtRubricType.setText(parentTitle);

        if (isExpanded) {
            groupHolder.setImageResource(R.drawable.group_up_light_blue);
        }
        else {
            groupHolder.setImageResource(R.drawable.group_down_classify_blue);
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        Log.i("Testing", "getChildView:true");
        String gradeType = "";
        int typeCount = childPosition + 1;
        // set child titles
        if (getGroup(groupPosition).equals("Attendance"))
            gradeType = "Semester Attendance:";
        else if (getGroup(groupPosition).equals("Exams"))
            gradeType = "Exam " + typeCount + ":";
        else if (getGroup(groupPosition).equals("Final Exam"))
            gradeType = "Final Exam: ";
        else if (getGroup(groupPosition).equals("Homework"))
            gradeType = "Assignment " + typeCount + ":";
        else if (getGroup(groupPosition).equals("Midterm Exams"))
            gradeType = "Midterm " + typeCount + ":";
        else if (getGroup(groupPosition).equals("Projects"))
            gradeType = "Project " + typeCount + ":";
        else if (getGroup(groupPosition).equals("Quizzes"))
            gradeType = "Quiz " + typeCount + ":";


        final String gradeScore = (String) getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inf = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.child_group_row, null);
        }
        TextView txtGradeType = (TextView) convertView.findViewById(R.id.txt_grade);
        TextView txtGradeScore = (TextView) convertView.findViewById(R.id.txt_grade_score);
        txtGradeType.setText(gradeType);
        txtGradeScore.setText(gradeScore);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
