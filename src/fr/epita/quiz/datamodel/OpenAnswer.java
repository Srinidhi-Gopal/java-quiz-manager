package fr.epita.quiz.datamodel;

public class OpenAnswer {

    private String text;

    private Quiz quiz;
    private OpenQuestion openQuestion;
    private Student student;

    public OpenAnswer(String text, Quiz quiz, OpenQuestion openQuestion, Student student) {
        this.text = text;
        this.quiz = quiz;
        this.openQuestion = openQuestion;
        this.student = student;
    }

    public String getText() {
        return text;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public OpenQuestion getOpenQuestion() {
        return openQuestion;
    }

    public Student getStudent() {
        return student;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setOpenQuestion(OpenQuestion openQuestion) {
        this.openQuestion = openQuestion;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
