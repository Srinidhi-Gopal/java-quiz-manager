package testMCQChoiceDAO;

import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;

public class TestMCQChoiceDAOCreateTable {

    public static void main(String[] args) throws SQLException {

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        mcqQuestionDAO.createTableMCQQuestion();
        System.out.println("MCQ_QUESTIONS TABLE CREATED");

        mcqChoiceDAO.createTableMCQChoice();
        System.out.println("MCQ_CHOICES TABLE CREATED");

    }

}

