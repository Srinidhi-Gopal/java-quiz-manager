package fr.epita.quiz.datamodel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractQuestion {

    private String question;
    private List<String> topics = new ArrayList<>();
    private Integer difficulty;

    public AbstractQuestion(String question, List<String> topics, Integer difficulty) {
        this.question = question;
        this.topics = topics;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getTopics() {
        return topics;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
