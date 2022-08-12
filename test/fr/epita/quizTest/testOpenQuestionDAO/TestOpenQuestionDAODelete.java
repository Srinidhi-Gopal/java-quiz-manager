package fr.epita.quizTest.testOpenQuestionDAO;

import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestOpenQuestionDAODelete {

    public static void test() throws SQLException {
        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        System.out.println();
        Map<String, OpenQuestion> openQuestionMap = openQuestionDAO.readAllOpenQuestion();
        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("open_question_id " + entry.getKey() + " : " + entry.getValue());
            System.out.println();
        }

        System.out.println("\nFrom the Above List Enter open_question_id to Delete : ");
        Integer delete_open_question_id = scn.nextInt();

        openQuestionDAO.deleteOpenQuestion(delete_open_question_id);

        System.out.println("Open Question with open_question_id = " + delete_open_question_id + " Successfully Deleted");
        /*
        openQuestionMap = openQuestionDAO.readAllOpenQuestion();
        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("open_question_id " + entry.getKey() + " : " + entry.getValue());
        }
        */
        //scn.close();
    }
}
