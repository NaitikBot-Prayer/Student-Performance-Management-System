/**
 * Abstract base class for an academic assessment record.
 * Demonstrates abstraction and encapsulation; concrete exam types extend this.
 */
public abstract class Assessment {
    private final String studentId;
    private final String subject;
    private final double marksObtained;
    private final double maxMarks;

    public Assessment(String studentId, String subject, double marksObtained, double maxMarks) {
        if (maxMarks <= 0)
            throw new IllegalArgumentException("Maximum marks must be positive");
        if (marksObtained < 0 || marksObtained > maxMarks)
            throw new IllegalArgumentException("Marks obtained must be between 0 and " + maxMarks);

        this.studentId = studentId;
        this.subject = subject;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    /** Each concrete assessment type defines how it labels itself (polymorphism). */
    public abstract String getExamType();

    @Override
    public String toString() {
        return String.format("%-10s %-15s %6.1f / %-6.1f", getExamType(), subject, marksObtained, maxMarks);
    }
}
