/**
 * Represents a high-weight final exam (inherits common fields from Assessment).
 */
public class FinalExamAssessment extends Assessment {

    public FinalExamAssessment(String studentId, String subject, double marksObtained, double maxMarks) {
        super(studentId, subject, marksObtained, maxMarks);
    }

    @Override
    public String getExamType() {
        return "Final Exam";
    }

    /** Final exams count for 60% of the overall subject weighting in this system. */
    public double getWeightedScore() {
        return getMarksObtained() * 0.6;
    }
}
