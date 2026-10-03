/**
 * Represents a mid-term exam (inherits common fields from Assessment).
 */
public class MidTermAssessment extends Assessment {

    public MidTermAssessment(String studentId, String subject, double marksObtained, double maxMarks) {
        super(studentId, subject, marksObtained, maxMarks);
    }

    @Override
    public String getExamType() {
        return "Mid-Term";
    }
}
