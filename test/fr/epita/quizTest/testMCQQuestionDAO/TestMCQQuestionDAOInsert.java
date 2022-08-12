package fr.epita.quizTest.testMCQQuestionDAO;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TestMCQQuestionDAOInsert {

    public static void main(String[] args) throws SQLException {

        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);

        System.out.println("Enter a question : ");
        String question = scn.nextLine();

        System.out.println("Enter comma separated topic for this question (Example- java,uml,oop) : ");
        String topics = scn.next();
        List<String> topicList = List.of(topics.split(","));

        System.out.println("Enter the Difficulty level as an Integer between 1 and 5 : ");
        Integer difficulty = scn.nextInt();

        MCQQuestion mcqQuestion = new MCQQuestion(question, topicList, difficulty);
        Integer mcq_id = mcqQuestionDAO.createMCQQuestion(mcqQuestion);
        System.out.println("Successfully Inserted question whose mcq_id : " + mcq_id);

        scn.close();
    }

}
