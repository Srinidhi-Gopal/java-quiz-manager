package fr.epita.quizTest.testMCQChoiceDAO;

import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;

public class TestMCQChoiceDAOCreateTable {

    public static void test() throws SQLException {

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        mcqQuestionDAO.createTableMCQQuestion();
        System.out.println("MCQ_QUESTIONS TABLE CREATED IF NOT ALREADY PRESENT");

        mcqChoiceDAO.createTableMCQChoice();
        System.out.println("MCQ_CHOICES TABLE CREATED IF NOT ALREADY PRESENT");

    }

}

