package org.example;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {


    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 5555);

        Scanner scanner = new Scanner(System.in);
        BufferedReader Bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
            String line;
            while (Bin.ready()) {
                System.out.println(Bin.readLine());
            }

            String type = scanner.nextLine();
            out.println(type);
            out.flush();

            // username

            String username = Bin.readLine();
            System.out.println(username);
            String name = scanner.nextLine();
            out.println(name);
            out.flush();

            // password
            System.out.println(Bin.readLine());
            String pass = scanner.nextLine();
            out.println(pass);
            out.flush();

            boolean inValid = Bin.readLine().equals("inValid");
            while (inValid) {
                System.out.println(Bin.readLine());
                // username
                System.out.println(Bin.readLine());
                name = scanner.nextLine();
                out.println(name);
                out.flush();

                // password
                System.out.println(Bin.readLine());
                pass = scanner.nextLine();
                out.println(pass);
                out.flush();
                inValid = Bin.readLine().equals("inValid");
            }


            int option;
            switch (type) {
                case "1":
                    while (true) {
                        // read menu
                        while (Bin.ready()) {
                            line = Bin.readLine();
                            System.out.println(line);
                        }
                        // choose option
                        option = scanner.nextInt();
                        out.println(option);
                        out.flush();

                        if (option == 1 || option == 3) {
                            try {
                                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                                ArrayList<String> result = (ArrayList<String>) in.readObject();
                                for (String r : result) {
                                    System.out.println(r);
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.out.println(" ");

                        }

                        if (option == 2) {
                            System.out.println(Bin.readLine());
                            String s = scanner.nextLine();
                            String courseName = scanner.nextLine();
                            out.println(courseName);
                            out.flush();
                            try {
                                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                                ArrayList<String> result = (ArrayList<String>) in.readObject();
                                for (String r : result) {
                                    System.out.println(r);
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.out.println(" ");

                        }

                        if (option == 4) {
                            System.out.println(Bin.readLine());
                            while (Bin.ready()) {
                                System.out.println(Bin.readLine());
                            }
                            String x = scanner.nextLine();
                            String num = scanner.nextLine();
                            out.println(num);
                            out.flush();
                            System.out.println(Bin.readLine());
                        }
                        if (option == 5) {
                            socket.close();
                            out.close();
                            Bin.close();
                            System.exit(0);
                        }
                    }

                case "2":
                    int i=0;
                    while(true){
                    while (Bin.ready()) {
                        line = Bin.readLine();
                        System.out.println(line);
                    }

                    option = scanner.nextInt();
                    out.println(option);
                    out.flush();

                    if (option == 1) {
                        i=1;
                        try {
                            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                            ArrayList<String> courses = (ArrayList<String>) in.readObject();
                            for (String course : courses) {
                                System.out.println(course);
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                    if (option == 2) {
                        i=1;
                        System.out.println(Bin.readLine());
                        String x = scanner.nextLine();
                        String courseName = scanner.nextLine();
                        out.println(courseName);
                        out.flush();
                        String newLine;
                        while ((newLine = Bin.readLine()) != null) {
                            System.out.println(newLine);
                        }

                    }
                    if (option == 3) {
                        System.out.println(Bin.readLine());
                        if(i==1){
                        String skip3 = scanner.nextLine();
                        }
                        String k = scanner.nextLine();
                        out.println(k);
                        out.flush();

                        System.out.println(Bin.readLine());
                        String studentName;
                        String grade;
                        while (true) {
                            System.out.print(Bin.readLine());
                            studentName = scanner.next();
                            out.println(studentName);
                            out.flush();
                            if (studentName.equals("-1")) {
                                String sc= scanner.nextLine();
                                break;
                            }
                            System.out.print(Bin.readLine());
                            String x = scanner.nextLine();
                            grade = scanner.nextLine();
                            out.println(grade);
                            out.flush();
                        }
                        System.out.println(Bin.readLine());
                        String sc = scanner.nextLine();
                        System.out.println(Bin.readLine());
                        System.out.println("\n");
                     }
                        if(option == 4){
                            socket.close();
                            out.close();
                            Bin.close();
                            System.exit(0);
                        }
                    }

                case "3":
                    while(true) {
                        // Admin menu
                        while (Bin.ready()) {
                            line = Bin.readLine();
                            System.out.println(line);
                        }

                        option = scanner.nextInt();
                        out.println(option);
                        out.flush();

                        if (option == 1) {
                            System.out.println(Bin.readLine());
                            String skip3 = scanner.nextLine();
                            String instructorName = scanner.nextLine();
                            out.println(instructorName);
                            out.flush();
                            System.out.println(Bin.readLine());
                        }
                        if (option == 2) {
                            System.out.println(Bin.readLine());
                            String skip3 = scanner.nextLine();
                            String CourseName = scanner.nextLine();
                            out.println(CourseName);
                            out.flush();
                            System.out.println(Bin.readLine());
                            String instructorName = scanner.nextLine();
                            out.println(instructorName);
                            out.flush();
                            System.out.println(Bin.readLine());
                        }
                        if (option == 3) {
                            System.out.println(Bin.readLine());
                            String skip2 = scanner.nextLine();
                            String studentName = scanner.nextLine();
                            out.println(studentName);
                            out.flush();
                            System.out.println(Bin.readLine());

                        }
                        if(option == 4){
                            socket.close();
                            out.close();
                            Bin.close();
                            System.exit(0);
                        }
                    }

            }



    }

}