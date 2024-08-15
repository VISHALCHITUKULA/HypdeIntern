package TASK5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        GradeManager gradeManager = new GradeManager();

        while (true) {
            System.out.println("\nStudent Grade Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Students");
            System.out.println("5. Add Grade");
            System.out.println("6. Update Grade");
            System.out.println("7. Delete Grade");
            System.out.println("8. View Grades");
            System.out.println("9. Calculate GPA");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student major: ");
                    String major = scanner.nextLine();
                    studentManager.addStudent(name, major);
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new major: ");
                    String newMajor = scanner.nextLine();
                    studentManager.updateStudent(updateId, newName, newMajor);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int deleteId = scanner.nextInt();
                    studentManager.deleteStudent(deleteId);
                    break;
                case 4:
                    studentManager.viewStudents();
                    break;
                case 5:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    gradeManager.addGrade(studentId, courseName, grade);
                    break;
                case 6:
                    System.out.print("Enter grade ID: ");
                    int gradeId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new course name: ");
                    String newCourseName = scanner.nextLine();
                    System.out.print("Enter new grade: ");
                    String newGrade = scanner.nextLine();
                    gradeManager.updateGrade(gradeId, newCourseName, newGrade);
                    break;
                case 7:
                    System.out.print("Enter grade ID: ");
                    int deleteGradeId = scanner.nextInt();
                    gradeManager.deleteGrade(deleteGradeId);
                    break;
                case 8:
                    System.out.print("Enter student ID: ");
                    int viewStudentId = scanner.nextInt();
                    gradeManager.viewGrades(viewStudentId);
                    break;
                case 9:
                    System.out.print("Enter student ID: ");
                    int gpaStudentId = scanner.nextInt();
                    gradeManager.calculateGPA(gpaStudentId);
                    break;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
