import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handles saving and loading student and assessment data to/from plain text files,
 * using a simple comma-separated format. Demonstrates file handling and exception handling.
 */
public class StorageManager {
    private final String studentsFile;
    private final String assessmentsFile;

    public StorageManager(String studentsFile, String assessmentsFile) {
        this.studentsFile = studentsFile;
        this.assessmentsFile = assessmentsFile;
    }

    public void save(PerformanceManager manager) {
        try (PrintWriter sw = new PrintWriter(new FileWriter(studentsFile));
             PrintWriter aw = new PrintWriter(new FileWriter(assessmentsFile))) {

            for (Student s : manager.getAllStudents()) {
                sw.println(s.getStudentId() + "," + s.getName() + "," + s.getStudentClass());
                for (Assessment a : s.getAssessments()) {
                    aw.println(a.getExamType() + "," + a.getStudentId() + "," + a.getSubject()
                            + "," + a.getMarksObtained() + "," + a.getMaxMarks());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    public void load(PerformanceManager manager) {
        loadStudents(manager);
        loadAssessments(manager);
    }

    private void loadStudents(PerformanceManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(studentsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 3) continue;
                try {
                    manager.addStudent(new Student(parts[0], parts[1], parts[2]));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipped invalid student record: " + line);
                }
            }
        } catch (IOException e) {
            // No existing file yet on first run — this is expected, not an error.
        }
    }

    private void loadAssessments(PerformanceManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(assessmentsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;
                try {
                    String examType = parts[0];
                    String studentId = parts[1];
                    String subject = parts[2];
                    double marks = Double.parseDouble(parts[3]);
                    double maxMarks = Double.parseDouble(parts[4]);

                    if (!manager.hasStudent(studentId)) continue;

                    Assessment assessment = switch (examType) {
                        case "Unit Test" -> new UnitTestAssessment(studentId, subject, marks, maxMarks);
                        case "Mid-Term" -> new MidTermAssessment(studentId, subject, marks, maxMarks);
                        case "Final Exam" -> new FinalExamAssessment(studentId, subject, marks, maxMarks);
                        default -> null;
                    };
                    if (assessment != null) {
                        manager.addAssessment(assessment);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipped invalid assessment record: " + line);
                }
            }
        } catch (IOException e) {
            // No existing file yet on first run — this is expected, not an error.
        }
    }
}
