package com.example.android.classify;

/*
    This class is used for the array adapter for the new class page
    When a user selects a grading rubric, it adds it to a listview
 */
public class RubricType {
    public String type;
    public String weight;

    public RubricType(String type, String weight){
        this.type = type;
        this.weight = weight;
    }
}
