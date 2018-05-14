package com.example.android.classify;

/*
    This class is used for the expandable list view for the class page
    When a user adds a new grading score, it will be added to this class and sent to
    the ExpandableListAdapter class as an object
 */

public class GradeType {
    public String gradeType;
    public String gradeScore;

    public GradeType(String type, String score){
        this.gradeType = type;
        this.gradeScore = score;
    }
}
