package com.example.android.classify;

/**
 * Created by yocoh on 3/20/2018.
 */

public class ClassStructure {
    private String profName;
    private String subject;
    private String timeStart, timeEnd;
    private boolean days[] = new boolean[7];
    private int quizWeight, examWeight, midtermWeight, finalWeight, projectWeight, homeworkWeight, attendanceWeight;

    ClassStructure(String profName, String subject, String timeStart, String timeEnd, String updateDays){
        this.profName = profName;
        this.subject = subject;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        setDays(updateDays);
    }

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
        for (int i = 1; i <= 7; ++i){
            if (d.contains("Sun"))
                days[1] = true;
            if (d.contains("Mon"))
                days[2] = true;
            if (d.contains("Tue"))
                days[3] = true;
            if (d.contains("Wed"))
                days[4] = true;
            if (d.contains("Thu"))
                days[5] = true;
            if (d.contains("Fri"))
                days[6] = true;
            if (d.contains("Sat"))
                days[7] = true;
        }
    }
    public boolean[] getDays(){
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
}
