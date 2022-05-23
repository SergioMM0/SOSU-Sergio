package BE;

public class StudentQuestion {
    private int id;
    private String category;
    private String title;
    private String question;

    private int questionaireId;
    private String color;
    private StudentQuestionnaireAnswer answer;


    public StudentQuestion(int id, String category, String title, String question, String color) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.question = question;

        this.color = color;
    }

    public StudentQuestion() {
    }

    public StudentQuestionnaireAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(StudentQuestionnaireAnswer answer) {
        this.answer = answer;
    }

    public int getQuestionaireId() {
        return questionaireId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }




    public void setQuestionaireId(int questionaireId) {
        this.questionaireId=questionaireId;
    }



    @Override
    public String toString() {
        return "StudentQuestion{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +

                ", questionaireId=" + questionaireId +
                '}';
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
