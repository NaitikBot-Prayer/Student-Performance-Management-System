import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Coordinates students, assessments, calculations and reports.
 * Central "manager" class for the system (demonstrates use of Collections).
 */
public class PerformanceManager {
    private final Map<String, Student> students = new LinkedHashMap<>();

    public void addStudent(Student student) {
        if (students.containsKey(student.getStudentId()))
            throw new IllegalArgumentException("A student with this ID already exists");
        students.put(student.getStudentId(), student);
    }

    public Student getStudent(String studentId) {
        Student s = students.get(studentId);
        if (s == null)
            throw new NoSuchElementException("No student found with ID " + studentId);
        return s;
    }

    public boolean hasStudent(String studentId) {
        return students.containsKey(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public void addAssessment(Assessment assessment) {
        Student student = getStudent(assessment.getStudentId());
        student.addAssessment(assessment);
    }

    /** Returns students ordered by percentage, highest first, for a class rank list. */
    public List<Student> getRankList() {
        List<Student> ranked = new ArrayList<>(students.values());
        ranked.sort(Comparator.comparingDouble(Student::getPercentage).reversed());
        return ranked;
    }
}
