package fr.epita.quiz.datamodel;

public class MCQChoice {

    private String choice;
    private Boolean valid;
    private String mcq_choice_id;
    private String mcq_id;
    private MCQQuestion mcqQuestion;

    public MCQChoice(String choice, Boolean valid, MCQQuestion mcqQuestion) {
        this.choice = choice;
        this.valid = valid;
        this.mcqQuestion = mcqQuestion;
    }

    public String getChoice() {
        return choice;
    }

    public Boolean getValid() {
        return valid;
    }

    public MCQQuestion getMcqQuestion() {
        return mcqQuestion;
    }

    public String getMcq_choice_id() {
        return mcq_choice_id;
    }

    public String getMcq_id() {
        return mcq_id;
    }

    public void setMcq_choice_id(String mcq_choice_id) {
        this.mcq_choice_id = mcq_choice_id;
    }

    public void setMcq_id(String mcq_id) {
        this.mcq_id = mcq_id;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public void setMcqQuestion(MCQQuestion mcqQuestion) {
        this.mcqQuestion = mcqQuestion;
    }

    @Override
    public String toString() {
        return "[Question : \n" + getMcqQuestion() + "\n" +
                "[Choice: \"" + getChoice() + ", Valid: \"" + getValid() + "\"]";
    }
}
