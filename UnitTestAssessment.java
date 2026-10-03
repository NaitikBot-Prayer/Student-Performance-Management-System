/**
 * Represents a short, low-weight unit test (inherits common fields from Assessment).
 */
public class UnitTestAssessment extends Assessment {

    public UnitTestAssessment(String studentId, String subject, double marksObtained, double maxMarks) {
        super(studentId, subject, marksObtained, maxMarks);
    }

    @Override
    public String getExamType() {
        return "Unit Test";
    }
}
