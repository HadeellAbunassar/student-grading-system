package org.example;


import org.example.dao.AdminDAOv1;
import org.example.dao.InstructorDAOv1;
import org.example.dao.StudentDAOv1;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {


    public static void main(String[] args)  {
        try {

            ServerSocket serverSocket = new ServerSocket(5555);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/gradingsystem?useTimezone=true&serverTimezone=UTC",
                            "hadeel", "hadeel123");

            while (true) {
                Socket socket = serverSocket.accept();
                new serverThread(socket,connection).start();
            }
        }  catch (IOException e) {
            System.out.println(e.getMessage());     }
        catch (SQLException e) {
            System.out.println("Problem in connecting to Database");
        } catch (ClassNotFoundException e) {
            System.out.println("Problem in Loading JDBC Driver");
        }

    }





}
class serverThread extends Thread {
    private Socket socket;
    private String name;
    private  BufferedReader bufferReader;
    private  PrintWriter outputStream;
    Connection connection;

    public serverThread(Socket socket,Connection connection ) {
        this.socket = socket;
        this.connection = connection;
        try {
            this.bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream =  new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void run() {

        try {
                outputStream.print("Welcome To Our Grading System \n" +
                        "Register as: \n" +
                        "1 - Student \n" +
                        "2 - Instructor \n" +
                        "3 - Admin \n" );
                outputStream.flush();


                String type = bufferReader.readLine();
                String userType = checkUserType(type);

                if (type.equals("4")) {
                    outputStream.println("true");
                    outputStream.flush();
                    this.interrupt();
                }

                outputStream.println("Please enter your Name: ");
                outputStream.flush();
                name = bufferReader.readLine();

                outputStream.println("Please enter your Password: ");
                outputStream.flush();
                String password = bufferReader.readLine();

                UserAuthentication auth = new UserAuthentication();

                while (!auth.testCredentials(name, password, userType,connection)) {
                    send("inValid", outputStream);
                    outputStream.println("Wrong User Id or Password , Please Try Again");
                    outputStream.flush();
                    outputStream.println("Please enter your Name: ");
                    outputStream.flush();
                    name = bufferReader.readLine();

                    outputStream.println("Please enter your Password: ");
                    outputStream.flush();
                    password = bufferReader.readLine();
                }
                if (auth.testCredentials(name, password, userType,connection)) {
                    send("Valid", outputStream);
                }


                // do the action based on the user type.
                if (userType.equals("Admin"))
                    AdminAction(bufferReader, outputStream);

                else if (userType.equals("Instructor"))
                    InstructorAction(bufferReader, outputStream);

                else
                    StudentAction(bufferReader, outputStream);

            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public String checkUserType(String type) throws SQLException, ClassNotFoundException {
        if(type.equals("1")){
            return "Student";
        }
        else  if(type.equals("2")){
            return "Instructor";
        }
        else
            return "Admin";
    }

    public void AdminAction(BufferedReader bufferedReader, PrintWriter out) throws IOException, SQLException, ClassNotFoundException {
        while(true) {
            out.print("Welcome to our educational system - Admins \n" +
                    "1- Add new instructor \n" +
                    "2- Assign course to instructor \n" +
                    "3- Add new Student \n" +
                    "4- Exit \n");
            out.flush();

            String option = bufferedReader.readLine();
            AdminDAOv1 admin = new AdminDAOv1(connection);

            if (option.equals("1")) {
                send("Please write the added Instructor name:", outputStream);
                String name = bufferedReader.readLine();
                String insertingState = admin.addInstructor(name);
                send(insertingState, outputStream);
            }
            if (option.equals("2")) {
                send("Enter course name:", outputStream);
                String courseName = bufferedReader.readLine();
                send("Please write the Instructor name:", outputStream);
                String InstructorName = bufferedReader.readLine();
                String insertingState = admin.assignInstructorToCourse(courseName, InstructorName);
                send(insertingState, outputStream);
            }
            if (option.equals("3")) {
                send("Please write the added student name:", outputStream);
                String name = bufferedReader.readLine();
                String insertingState = admin.addStudent(name);
                send(insertingState, outputStream);
            }
            if (option.equals("4")) {
              return;
            }
        }
    }

    public void InstructorAction(BufferedReader bufferedReader, PrintWriter out) throws IOException, SQLException, ClassNotFoundException {
        while(true){
        out.print("Welcome to our educational system - Instructors \n" +
                "1- Show Instructor Courses \n" +
                "2- Show Student in each Course \n" +
                "3- Inserting Student grades \n" +
                "4- Exit \n");
        out.flush();

        String option = bufferedReader.readLine();
        InstructorDAOv1 instructor = new InstructorDAOv1(connection);

        if(option.equals("1")){
            ArrayList<String> courses = instructor.showInstructorCourses(name);
            try {
                ObjectOutputStream Oout = new ObjectOutputStream(socket.getOutputStream());
                Oout.writeObject(courses);
            } catch (IOException e) {
                System.out.println("An error occurred while process the request ");

            }
        }
        if(option.equals("2")){
            send("Write course name" , outputStream);
            String courseName=bufferedReader.readLine();
            String Students = instructor.showCourseStudents(courseName,name);
            send(Students,outputStream);
        }
        if(option.equals("3")){
            send("What is the course name you want to insert the marks for? ",outputStream);
            String courseName=bufferedReader.readLine();
            send("Enter student names and grades (enter -1 to finish):",outputStream);
            HashMap<String, Integer> gradesMap = new HashMap<>();
            String studentName;
            int grade;
            while(true){
                send("Enter student name (or -1 to finish): ",outputStream);
                studentName = bufferedReader.readLine();
                if(studentName.equals("-1")){
                    send("Press enter to insert the mark",outputStream);
                    break;
                }
                send("Enter grade for " + studentName + " " , outputStream);
                grade = Integer.parseInt(bufferedReader.readLine());
                gradesMap.put(studentName, grade);
            }
            instructor.insertingStudentsGrades(courseName,gradesMap);
            send("inserting marks done successfully" , outputStream);
        }
        if(option.equals("4")){
            return;
        }

    }
    }

    public void StudentAction(BufferedReader bufferedReader , PrintWriter out) throws IOException, SQLException, ClassNotFoundException {
        while(true){
        out.print("Welcome to our grading system - Students \n" +
                "1- Show your grades \n" +
                "2- Show Course Info \n" +
                "3- Show Your Courses \n" +
                "4- Enroll in Course  \n" +
                "5- Exit \n");
        out.flush();

        String option = bufferedReader.readLine();
        StudentDAOv1 student = new StudentDAOv1(connection);

        if(option.equals("1")){
            ArrayList<String> result = student.showGrades(name);
            ObjectOutputStream Oout = new ObjectOutputStream(socket.getOutputStream());
            Oout.writeObject(result);
        }
        if(option.equals("2")){
            out.println("Write course name");
            out.flush();
            String courseName = bufferedReader.readLine();
            ArrayList<String> result = student.showCourse(courseName);
            ObjectOutputStream Oout = new ObjectOutputStream(socket.getOutputStream());
            Oout.writeObject(result);
        }
        if(option.equals("3")){
            ArrayList<String> result = student.showEnrolledCoursesWithInstructor(name);
            ObjectOutputStream Oout = new ObjectOutputStream(socket.getOutputStream());
            Oout.writeObject(result);
        }
        if(option.equals("4")){
            String courses = student.showAvailableCourses();
            send(courses,outputStream);
            send("Enter course Id you want to enroll on: ",outputStream);
            String id = bufferedReader.readLine();
            String enrollmentStat = student.enrollInCourse(name,id);
            send(enrollmentStat,outputStream);
        }
        if(option.equals("5")){
           return;
        }
    }
    }


    public static void send(String msg, PrintWriter out) throws IOException {
        out.println(msg);
        out.flush();
    }




}
