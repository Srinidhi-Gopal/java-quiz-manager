package testMCQChoiceDAO;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.Map;

public class TestMCQChoiceDAOReadAll {

    public static void main(String[] args) throws SQLException {

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        System.out.println("\nRecords of MCQ_CHOICES is as follows :-");
        Map<String, MCQChoice> mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();
        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {
            System.out.println("mcq_choice_id " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nRecords of MCQ_QUESTIONS is as follows :-");
        Map<String, MCQQuestion> mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }

    }

}

