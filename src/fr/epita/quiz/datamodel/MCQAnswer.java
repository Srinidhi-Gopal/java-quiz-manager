package fr.epita.quiz.datamodel;

public class MCQAnswer {

    private Quiz quiz;
    private MCQChoice mcqChoice;
    private Student student;

    public MCQAnswer(Quiz quiz, MCQChoice mcqChoice, Student student) {
        this.quiz = quiz;
        this.mcqChoice = mcqChoice;
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public MCQChoice getMcqChoice() {
        return mcqChoice;
    }

    public Student getStudent() {
        return student;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setMcqChoice(MCQChoice mcqChoice) {
        this.mcqChoice = mcqChoice;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}


