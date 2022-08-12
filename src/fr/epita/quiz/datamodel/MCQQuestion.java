package fr.epita.quiz.datamodel;

import java.util.List;

public class MCQQuestion extends AbstractQuestion {

    private String mcq_id;

    public MCQQuestion(String question, List<String> topics, Integer difficulty) {
        super(question, topics, difficulty);
    }

    public String getMcq_id() {
        return mcq_id;
    }

    public void setMcq_id(String mcq_id) {
        this.mcq_id = mcq_id;
    }

    @Override
    public String toString() {
        return "[Question: \"" + getQuestion() + "\"," +
                " topics: \"" + getTopics() + "\"," +
                " difficulty: \"" + getDifficulty() + "\"] ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return mcq_id == ((MCQQuestion) o).mcq_id;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(mcq_id);
    }
}


