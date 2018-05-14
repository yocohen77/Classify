package com.example.android.classify;

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
    private int quizWeight, examWeight, midtermWeight, finalWeight, projectWeight, homeworkWeight, attendanceWeight;
    private float rating;
    public List<GradeType> quizzes, exams, midterms, finals, projects, homework, attendance;
    // default constructor
    public ClassStructure(){
        quizzes = new ArrayList<>();
        exams = new ArrayList<>();
        midterms = new ArrayList<>();
        finals = new ArrayList<>();
        projects = new ArrayList<>();
        homework = new ArrayList<>();
        attendance = new ArrayList<>();
    };

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
    public String getTimeStart(){
        return timeStart;
    }
    public String getTimeEnd(){
        return timeEnd;
    }
    public void setQuizWeight(int weight){
        quizWeight = weight;
    }
    public int getQuizWeight(){
        return quizWeight;
    }
    public void setExamWeight (int weight){
        examWeight = weight;
    }
    public int getExamWeight(){
        return examWeight;
    }
    public void setMidtermWeight(int weight){
        midtermWeight = weight;
    }
    public int getMidtermWeight() {
        return midtermWeight;
    }
    public void setFinalWeight(int weight){
        finalWeight = weight;
    }
    public int getFinalWeight(){
        return finalWeight;
    }
    public void setProjectWeight(int weight){
        projectWeight = weight;
    }
    public int getProjectWeight(){
        return projectWeight;
    }
    public void setHomeworkWeight(int weight){
        homeworkWeight = weight;
    }
    public int getHomeworkWeight(){
        return homeworkWeight;
    }
    public void setAttendanceWeight(int weight){
        attendanceWeight = weight;
    }
    public int getAttendanceWeight(){
        return attendanceWeight;
    }
    public void calculateRating(float result){
        result = result/100;
        rating = roundToHalf(result * 5);
    };
    public float getRating(){ return rating; };
//    public float calculateGrade(){
//
//
//
//        // calculate and set the rating
//        // calculateRating(result);
//
//        /*
//         if (result >= 97)
//            return "A+";
//         else if (result < 97 && result >= 93)
//            return "A";
//         else if (result < 93 && result >= 90)
//            return "A-";
//         else if (result < 90 && result >= 87)
//            return "B+";
//         else if (result < 87 && result >= 83)
//            return "B";
//         else if (result < 83 && result >= 80)
//            return "B-";
//         else if (result < 80 && result >= 77)
//            return "C+";
//         else if (result < 77 && result >= 73)
//            return "C";
//         else if (result < 73 && result >= 70)
//            return "C-";
//         else if (result < 70 && result >= 67)
//            return "D+";
//         else if (result < 67 && result >= 63)
//            return "D";
//         else if (result < 63 && result >= 60)
//            return "D-";
//         else
//            return "F";
//         */
//
//
//    }

    public float roundToHalf(float num){
        return Math.round(num * 2) / 2.0f;
    }
}
