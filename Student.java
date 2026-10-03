import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student and holds their list of assessment records.
 * Demonstrates encapsulation: fields are private, accessed via methods.
 */
public class Student {
    private final String studentId;
    private String name;
    private String studentClass;
    private final List<Assessment> assessments;

    public Student(String studentId, String name, String studentClass) {
        if (studentId == null || studentId.trim().isEmpty())
            throw new IllegalArgumentException("Student ID cannot be empty");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Student name cannot be empty");

        this.studentId = studentId;
        this.name = name;
        this.studentClass = studentClass;
        this.assessments = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public double getTotalObtained() {
        double total = 0;
        for (Assessment a : assessments) {
            total += a.getMarksObtained();
        }
        return total;
    }

    public double getTotalMax() {
        double total = 0;
        for (Assessment a : assessments) {
            total += a.getMaxMarks();
        }
        return total;
    }

    public double getPercentage() {
        double totalMax = getTotalMax();
        if (totalMax == 0) return 0;
        return (getTotalObtained() / totalMax) * 100.0;
    }

    public String getGrade() {
        double percentage = getPercentage();
        if (percentage >= 90) return "A+";
        if (percentage >= 75) return "A";
        if (percentage >= 60) return "B";
        if (percentage >= 40) return "C";
        return "F";
    }

    public String getResultStatus() {
        return getPercentage() >= 40 ? "Pass" : "Fail";
    }

    /** Returns the subject with the lowest percentage score, or "N/A" if no assessments. */
    public String getWeakestSubject() {
        String weakest = "N/A";
        double lowestPct = Double.MAX_VALUE;
        for (Assessment a : assessments) {
            double pct = (a.getMarksObtained() / a.getMaxMarks()) * 100.0;
            if (pct < lowestPct) {
                lowestPct = pct;
                weakest = a.getSubject();
            }
        }
        return weakest;
    }

    @Override
    public String toString() {
        return String.format("%-8s %-18s %-8s", studentId, name, studentClass);
    }
}
