package testOpenQuestionDAO;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestOpenQuestionDAOReadAll {

    public static void main(String[] args) throws SQLException {

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        Map<String, OpenQuestion> openQuestionMap = openQuestionDAO.readAllOpenQuestion();
        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("open_question_id " + entry.getKey() + " : " + entry.getValue());
        }
    }

}
