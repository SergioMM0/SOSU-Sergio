package BE;

public class StudentQuestionnaireAnswer {
    private int id;
    private int questionId;
    private int state;
    private int questionnaireId;

    public StudentQuestionnaireAnswer(int id, int questionId, int state, int questionnaireId) {
        this.id = id;
        this.questionId = questionId;
        this.state = state;
        this.questionnaireId = questionnaireId;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public StudentQuestionnaireAnswer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
