package testMCQQuestionDAO;

import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;

public class TestMCQQuestionDAOCreateTable {

    public static void main(String[] args) throws SQLException {

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);

        mcqQuestionDAO.createTableMCQQuestion();
        System.out.println("MCQ_QUESTIONS TABLE CREATED");
    }

}
