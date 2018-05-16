package com.example.android.classify;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yocoh on 3/20/2018.
 */

public class ClassStructure implements Serializable {
    private String profName;
    private String subject;
    private String timeStart, timeEnd;
    private String days;
    private String childId;
    private String letterGrade;
    private float quizWeight, examWeight, midtermWeight, finalWeight, projectWeight, homeworkWeight, attendanceWeight;
    private float rating, weightedGrade;
    public List<String> quizzesList, examsList, midtermsList, finalList, projectsList, homeworkList, attendanceList;
    // default constructor
    public ClassStructure(){
        quizzesList = new ArrayList<>();
        examsList = new ArrayList<>();
        midtermsList = new ArrayList<>();
        finalList = new ArrayList<>();
        projectsList = new ArrayList<>();
        homeworkList = new ArrayList<>();
        attendanceList = new ArrayList<>();
        letterGrade = "";
        weightedGrade = 0;
        rating = 0;

    };

    public void setChildId(String id) { childId = id; }
    public String getChildId() { return childId; }
    public void setProf(String name){
        profName = name;
    }
    public String getProfName(){
        return profName;
    }
    public void setSubject(String s){
        subject = s;
    }
    public String getSubject(){
        return subject;
    }
    public void setDays (String d){
        days = d;
    }
    public String getDays(){
        return days;
    }
    public void setTimes(String start, String end){
        timeStart = start;
        timeEnd = end;
    }
    public String getLetterGrade() { return letterGrade; }
    public float getWeightedGrade() {return weightedGrade; }
    public String getTimeStart(){
        return timeStart;
    }
    public String getTimeEnd(){
        return timeEnd;
    }
    public void setQuizWeight(int weight){
        quizWeight = weight;
    }
    public float getQuizWeight(){
        return quizWeight;
    }
    public void setExamWeight (int weight){
        examWeight = weight;
    }
    public float getExamWeight(){
        return examWeight;
    }
    public void setMidtermWeight(int weight){
        midtermWeight = weight;
    }
    public float getMidtermWeight() {
        return midtermWeight;
    }
    public void setFinalWeight(int weight){
        finalWeight = weight;
    }
    public float getFinalWeight(){
        return finalWeight;
    }
    public void setProjectWeight(int weight){
        projectWeight = weight;
    }
    public float getProjectWeight(){
        return projectWeight;
    }
    public void setHomeworkWeight(int weight){
        homeworkWeight = weight;
    }
    public float getHomeworkWeight(){
        return homeworkWeight;
    }
    public void setAttendanceWeight(int weight){
        attendanceWeight = weight;
    }
    public float getAttendanceWeight(){
        return attendanceWeight;
    }
    public void calculateRating(){
        rating = roundToHalf((weightedGrade/100) * 5);
    };
    public float getRating(){ return rating; };

    public void calculateGrade() {
        float tempWeight, result = 0, sum = 0;
        int count;
        // first check if a weight was entered
        if (quizWeight > 0) {
            // if weight exists, check if list is empty. If list isnt empty, calculate the grade
            // but if list is empty, speculate the grade as 100 for now
            if (!quizzesList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < quizzesList.size(); ++i) {
                    sum = Integer.parseInt(quizzesList.get(i)) + sum;
                    Log.i("ClassifyStructure", "sum is: " + sum);
                    count++;
                }
                sum = sum / count;
                tempWeight = quizWeight / 100;
                Log.i("ClassifyStructure", "sum average is: " + sum + " and temp weight: " + tempWeight);
                result = result + (tempWeight * sum);
                Log.i("ClassifyStructure", "result is: " + result);
            } else {
                sum = 100;
                tempWeight = quizWeight / 100;
                result = result + (tempWeight * sum);
            }
        }
        if (examWeight > 0) {
            if (!examsList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < examsList.size(); ++i) {
                    sum = Integer.parseInt(examsList.get(i)) + sum;
                    count++;
                }
                sum = sum / count;
                tempWeight = examWeight / 100;
                result = result + (tempWeight * sum);
            } else {
                sum = 100;
                tempWeight = examWeight / 100;
                result = result + (tempWeight * sum);
            }
        }
        if (midtermWeight > 0) {
            if (!midtermsList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < midtermsList.size(); ++i) {
                    sum = Integer.parseInt(midtermsList.get(i)) + sum;
                    count++;
                }
                sum = sum / count;
                tempWeight = midtermWeight / 100;
                result = result + (tempWeight * sum);
            } else {
                sum = 100;
                tempWeight = midtermWeight / 100;
                result = result + (tempWeight * sum);
            }
        }
        if (finalWeight > 0) {
            if (!finalList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < finalList.size(); ++i) {
                    sum = Integer.parseInt(finalList.get(i)) + sum;
                    count++;
                }
                sum = sum / count;
                tempWeight = finalWeight / 100;
                result = result + (tempWeight * sum);
            } else {
                sum = 100;
                tempWeight = finalWeight / 100;
                result = result + (tempWeight * sum);
            }
        }
        if (projectWeight > 0) {
            if (!projectsList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < projectsList.size(); ++i) {
                    sum = Integer.parseInt(projectsList.get(i)) + sum;
                    count++;
                }
                sum = sum / count;
                tempWeight = projectWeight / 100;
                result = result + (tempWeight * sum);
            } else {
                sum = 100;
                tempWeight = projectWeight / 100;
                result = result + (tempWeight * sum);
            }
        }
        if (homeworkWeight > 0) {
            if (!homeworkList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < homeworkList.size(); ++i) {
                    sum = Integer.parseInt(homeworkList.get(i)) + sum;
                    count++;
                }
                sum = sum / count;
                tempWeight = homeworkWeight / 100;
                result = result + (tempWeight * sum);
            } else {
                sum = 100;
                tempWeight = homeworkWeight / 100;
                result = result + (tempWeight * sum);
            }
        }
        if (attendanceWeight > 0) {
            if (!attendanceList.isEmpty()) {
                count = 0;
                sum = 0;
                for (int i = 0; i < attendanceList.size(); ++i) {
                    sum = Integer.parseInt(attendanceList.get(i)) + sum;
                    count++;
                }
                sum = sum / count;
                tempWeight = attendanceWeight / 100;
                result = result + (tempWeight * sum);
            } else {
                sum = 100;
                tempWeight = attendanceWeight / 100;
                result = result + (tempWeight * sum);
            }
        }

        weightedGrade = result;
        calculateLetterGrade();
        calculateRating();
    }

    public void calculateLetterGrade(){
         if (weightedGrade >= 97)
            letterGrade = "A+";
         else if (weightedGrade < 97 && weightedGrade >= 93)
            letterGrade = "A";
         else if (weightedGrade < 93 && weightedGrade >= 90)
            letterGrade = "A-";
         else if (weightedGrade < 90 && weightedGrade >= 87)
            letterGrade = "B+";
         else if (weightedGrade < 87 && weightedGrade >= 83)
            letterGrade = "B";
         else if (weightedGrade < 83 && weightedGrade >= 80)
            letterGrade = "B-";
         else if (weightedGrade < 80 && weightedGrade >= 77)
            letterGrade = "C+";
         else if (weightedGrade < 77 && weightedGrade >= 73)
            letterGrade = "C";
         else if (weightedGrade < 73 && weightedGrade >= 70)
            letterGrade = "C-";
         else if (weightedGrade < 70 && weightedGrade >= 67)
            letterGrade = "D+";
         else if (weightedGrade < 67 && weightedGrade >= 63)
            letterGrade = "D";
         else if (weightedGrade < 63 && weightedGrade >= 60)
            letterGrade = "D-";
         else
            letterGrade = "F";
    }

    public float roundToHalf(float num){
        return Math.round(num * 2) / 2.0f;
    }
}
