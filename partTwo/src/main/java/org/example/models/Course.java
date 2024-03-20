package org.example.models;

public class Course {
    String courseName;
    String InstructorOfTheCourse;

    public Course( String courseName ,String InstructorOfTheCourse ){
        this.courseName = courseName;
        this.InstructorOfTheCourse = InstructorOfTheCourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public String toString(){
        return "Assigning " + this.courseName + " to " + this.InstructorOfTheCourse + " is done successfully";
    }


}
