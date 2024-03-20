package com.example.webapppart3.models;


public class Instructor {


    private String  name;
    private String password;


    public Instructor(String name){
        this.name = name;
        this.password = PasswordInitializer.password(5);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return "Added instructor info : Name = " + name  + " Password =" + password;
    }



}
