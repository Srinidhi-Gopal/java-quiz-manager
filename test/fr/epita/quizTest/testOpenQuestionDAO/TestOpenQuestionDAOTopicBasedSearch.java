package fr.epita.quizTest.testOpenQuestionDAO;

import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestOpenQuestionDAOTopicBasedSearch {

    public static void test() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        System.out.println("Enter a Topic : ");
        String topic = scanner.next();

        //Read the choices from the DB using DAO objects
        Map<String, OpenQuestion> openQuestionMap = openQuestionDAO.topicBasedOpenQuestionSearch(topic);
        System.out.println();

        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("Question " + entry.getKey() + " : " + entry.getValue().getQuestion());
        }

        //scanner.close();
    }
}
