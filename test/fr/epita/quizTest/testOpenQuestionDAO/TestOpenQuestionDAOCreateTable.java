package fr.epita.quizTest.testOpenQuestionDAO;

import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;

public class TestOpenQuestionDAOCreateTable {

    public static void test() throws SQLException {

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        openQuestionDAO.createTableOpenQuestion();
        System.out.println("OPEN_QUESTIONS TABLE CREATED!");

    }

}
