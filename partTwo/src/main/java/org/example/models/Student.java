package org.example.models;

public class Student {

    private String password;

    private String name;

    public String getName() {
        return name;
    }

    public Student(String name){
        this.name = name;
        this.password = passwordInitializer.password(5);
    }

    public String getPassword() {
        return password;
    }


    public String toString(){
        return "Added Student info : Name = " + name  + " Password =" + password;
    }



}
