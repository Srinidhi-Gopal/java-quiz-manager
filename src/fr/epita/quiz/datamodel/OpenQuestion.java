package fr.epita.quiz.datamodel;

import java.util.List;

public class OpenQuestion extends AbstractQuestion{

    private String open_question_id;

    public OpenQuestion(String question, List<String> topics, Integer difficulty) {
        super(question, topics, difficulty);
    }

    public String getOpen_question_id() {
        return open_question_id;
    }

    public void setOpen_question_id(String open_question_id) {
        this.open_question_id = open_question_id;
    }

    @Override
    public String toString() {
        return " [Question: \"" + getQuestion() + "\"," +
                " topics: \"" + getTopics() + "\"," +
                " difficulty: \"" + getDifficulty() + "\"] ";
    }

}
