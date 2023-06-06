import java.sql.*;
import java.util.Scanner;

public class Student {
    private String firstName;
    private String lastName;
    private String gradeYear;
    private int gradeYearNumber;
    private String studentId;
    private String courses = "";
    private int tuitionBalance = 0;
    private static int costOfCourse = 600;
    private static int id = 10001;

    private Scanner inText = new Scanner(System.in);
    private Scanner inNumber = new Scanner(System.in);


    //Constructor: promt user to enter student's name and year
    public Student() {
        System.out.print("Enter student first name: ");
        this.firstName = setName();

        System.out.print("Enter student last name: ");
        this.lastName = setName();

        setGradeYear();

        this.studentId = gradeYearNumber + "" + id;
        id++;

        enroll();
        payTuition();
        showInfo();
        saveInformation();
    }

    //Set the names
    private String setName() {
        while (true) {
            String name = inText.nextLine();
            if (name.matches("[a-zA-z]+")) {
                return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            } else {
                System.out.print("The name must contain only letters. Try again: ");
            }
        }

    }

    //Set the class years
    private void setGradeYear() {
        while (true) {
            System.out.print("   1 - Freshmen\n   2 - Sophmore\n   3 - Junior\n   4 - Senior\nEnter student grade year: ");
            gradeYearNumber = inNumber.nextInt();
            switch (gradeYearNumber) {
                case 1:
                    this.gradeYear = "Freshmen";
                    return;
                case 2:
                    this.gradeYear = "Sophmore";
                    return;
                case 3:
                    this.gradeYear = "Junior";
                    return;
                case 4:
                    this.gradeYear = "Senior";
                    return;
                default:
                    System.out.println("You have not entered a valid option");
            }
        }
    }

    //Enroll in courses
    private void enroll() {
        System.out.println("Enrolling in a course costs: " + costOfCourse);
        while (true) {
            System.out.print("Enter course to enroll (Q to quit): ");
            String course = inText.nextLine();
            if (!course.equalsIgnoreCase("Q")) {
                courses += "\n   " + course;
                tuitionBalance += costOfCourse;
            } else {
                break;
            }
        }
    }

    //Pay tuition
    private void payTuition() {
        System.out.println("The student balance is: $" + tuitionBalance);
        if (tuitionBalance > 0) {
            while(true) {
                System.out.print("Enter the student payment: $");
                int payment = inNumber.nextInt();
                if (payment <= tuitionBalance) {
                    tuitionBalance -= payment;
                    System.out.println("A payment was made in the amount of: $" + payment);
                    break;
                } else {
                    System.out.println("The payment is too high. The student balance is: $" + tuitionBalance);
                }
            }
        }
    }

    //Show status
    private void showInfo() {
        System.out.println("\nName: " + firstName + " " + lastName +
                "\nGrade Level: " + gradeYear +
                "\nStudent ID: " + studentId +
                "\nCourses Enrolled:" + courses +
                "\nBalance: $" + tuitionBalance
        );
    }

    //Save de information
    private void saveInformation() {
        while(true) {
            System.out.println("Do you want to save the information? (y / n)");
            String answer = inText.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                saveToDatabase();
                return;
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("The information has not been saved");
                return;
            } else {
                System.out.println("You did not give a correct answer!");
            }
        }
    }

    //Execute query
    private void saveToDatabase() {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO students VALUES ('%s', '%s', '%s', '%s', '%s', %d)";
            int affectedRows = st.executeUpdate(String.format(template, firstName, lastName, gradeYear, studentId, courses, tuitionBalance));
            System.out.println(affectedRows > 0 ? "The information has been saved" : "The information has not been saved");
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println("The information has not been saved");
        }

    }

}
