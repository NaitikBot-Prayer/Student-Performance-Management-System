import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Entry point for the Student Performance & Assessment Management System.
 * Presents a menu-driven console interface built on top of PerformanceManager.
 */
public class Main {
    private static final PerformanceManager manager = new PerformanceManager();
    private static final StorageManager storage = new StorageManager("students.txt", "assessments.txt");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        storage.load(manager);
        System.out.println("STUDENT PERFORMANCE & ASSESSMENT MANAGEMENT SYSTEM");

        boolean running = true;
        while (running) {
            printMenu();
            String choiceInput = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(choiceInput);
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> addAssessment();
                    case 3 -> viewStudentRecords();
                    case 4 -> calculateGrade();
                    case 5 -> viewPerformanceSummary();
                    case 6 -> viewClassRankList();
                    case 7 -> {
                        storage.save(manager);
                        System.out.println("Data saved. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Please choose a valid option (1-7).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add Student");
        System.out.println("2. Add Assessment Marks");
        System.out.println("3. View Student Records");
        System.out.println("4. Calculate Grade");
        System.out.println("5. View Performance Summary");
        System.out.println("6. View Class Rank List");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addStudent() {
        try {
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Class: ");
            String studentClass = scanner.nextLine().trim();

            manager.addStudent(new Student(id, name, studentClass));
            System.out.println("Student added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addAssessment() {
        try {
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine().trim();

            System.out.print("Enter Exam Type (1-Unit Test, 2-Mid-Term, 3-Final Exam): ");
            String examChoice = scanner.nextLine().trim();

            System.out.print("Enter Marks Obtained: ");
            double marks = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter Maximum Marks: ");
            double maxMarks = Double.parseDouble(scanner.nextLine().trim());

            Assessment assessment = switch (examChoice) {
                case "1" -> new UnitTestAssessment(id, subject, marks, maxMarks);
                case "2" -> new MidTermAssessment(id, subject, marks, maxMarks);
                case "3" -> new FinalExamAssessment(id, subject, marks, maxMarks);
                default -> throw new IllegalArgumentException("Invalid exam type selected");
            };

            manager.addAssessment(assessment);
            System.out.println("Assessment added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewStudentRecords() {
        List<Student> allStudents = manager.getAllStudents();
        if (allStudents.isEmpty()) {
            System.out.println("No students recorded yet.");
            return;
        }
        System.out.println();
        System.out.printf("%-8s %-18s %-8s%n", "ID", "Name", "Class");
        for (Student s : allStudents) {
            System.out.println(s);
        }
    }

    private static void calculateGrade() {
        try {
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine().trim();
            Student student = manager.getStudent(id);

            System.out.println();
            System.out.println("----- GRADE REPORT -----");
            System.out.println("Student ID  : " + student.getStudentId());
            System.out.println("Name        : " + student.getName());
            for (Assessment a : student.getAssessments()) {
                System.out.println("  " + a);
            }
            System.out.printf("Total Marks : %.1f / %.1f%n", student.getTotalObtained(), student.getTotalMax());
            System.out.printf("Percentage  : %.2f%%%n", student.getPercentage());
            System.out.println("Grade       : " + student.getGrade());
            System.out.println("Result      : " + student.getResultStatus());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewPerformanceSummary() {
        try {
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine().trim();
            Student student = manager.getStudent(id);

            System.out.println();
            System.out.println("----- PERFORMANCE SUMMARY -----");
            System.out.println("Student ID      : " + student.getStudentId());
            System.out.printf("Total Marks     : %.0f / %.0f%n", student.getTotalObtained(), student.getTotalMax());
            System.out.printf("Percentage      : %.2f%%%n", student.getPercentage());
            System.out.println("Grade           : " + student.getGrade());
            System.out.println("Weakest Subject : " + student.getWeakestSubject());
            System.out.println("Result Status   : " + student.getResultStatus());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewClassRankList() {
        List<Student> ranked = manager.getRankList();
        if (ranked.isEmpty()) {
            System.out.println("No students recorded yet.");
            return;
        }
        System.out.println();
        System.out.printf("%-6s %-8s %-18s %-10s %-6s%n", "Rank", "ID", "Name", "Percent", "Grade");
        int rank = 1;
        for (Student s : ranked) {
            System.out.printf("%-6d %-8s %-18s %-10.2f %-6s%n",
                    rank++, s.getStudentId(), s.getName(), s.getPercentage(), s.getGrade());
        }
    }
}
