import java.util.Scanner;

public class StudentDatabaseApp {

    public static void main(String[] args) {

        Scanner inNumber = new Scanner(System.in);

        //Ask how many new students we want to add
        System.out.print("Enter number of new students to enroll: ");
        int studentsNumber = inNumber.nextInt();
        Student[] students = new Student[studentsNumber];

        //Create n number of new students
        for(int i = 0; i < studentsNumber; i++) {
            students[i] = new Student();
        }
    }


}
