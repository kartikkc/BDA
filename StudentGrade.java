
import java.util.Scanner;

public class StudentGrade {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students: ");
        int numStudents = scanner.nextInt();
        scanner.nextLine(); // Consume newline character 
        String[] names = new String[numStudents];
        int[] rollNumbers = new int[numStudents];
        String[] subjects = new String[numStudents];
        char[] grades = new char[numStudents];
        for (int i = 0; i < numStudents; i++) {
            System.out.println("Enter details for student " + (i + 1) + ":");
            System.out.print("Name: ");
            names[i] = scanner.nextLine();
            System.out.print("Roll Number: ");
            rollNumbers[i] = scanner.nextInt();
            scanner.nextLine(); // Consume newline character 
            System.out.print("Subject: ");
            subjects[i] = scanner.nextLine();
            System.out.print("Grade: ");
            grades[i] = scanner.nextLine().charAt(0);
        }
        // Print details for all students 
        System.out.println("\nStudent Details:");
        for (int i = 0; i < numStudents; i++) {
            System.out.println("Student " + (i + 1) + ":");
            System.out.println("Name: " + names[i]);
            System.out.println("Roll Number: " + rollNumbers[i]);
            System.out.println("Subject: " + subjects[i]);
            System.out.println("Grade: " + grades[i]);
            System.out.println();
        }

        scanner.close();
    }
}
