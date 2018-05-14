package com.example.android.classify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listParent;
    private HashMap<String, List<GradeType>> _listChild;

    public ExpandableListAdapter(Context context, List<String> listParent,
                              HashMap<String, List<GradeType>> listChild){
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
        String parentTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.parent_group_row, null);
        }

        TextView txtRubricType = convertView.findViewById(R.id.txt_rubric_type);
        txtRubricType.setText(parentTitle);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        Log.i("Testing", "getChildView:true");
        GradeType temp = (GradeType) getChild(groupPosition, childPosition);
        final String gradeType = temp.gradeType;
        final String gradeScore = temp.gradeScore;
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
