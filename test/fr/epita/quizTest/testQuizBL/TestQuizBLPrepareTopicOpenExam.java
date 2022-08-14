package fr.epita.quizTest.testQuizBL;

import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.businessLogic.QuizBL;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import javax.xml.parsers.ParserConfigurationException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestQuizBLPrepareTopicOpenExam {

    public static void test() throws SQLException, ParserConfigurationException {
        Scanner scanner = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        System.out.println("Enter a Topic : ");
        String topic = scanner.next();

        //Read the choices from the DB using DAO objects
        Map<String, OpenQuestion> openQuestionMap = openQuestionDAO.topicBasedOpenQuestionSearch(topic);
        System.out.println();

        QuizBL quizBL = new QuizBL();

        System.out.println("Preparing Topic Based Open Exam as an XML File");
        quizBL.prepareOpenExam(openQuestionMap, "TOPIC");
        System.out.println("Prepared Topic Based Open Exam\n\n" +
                "Please Check ./resources folder with file name : 'topicOpenExam' followed by timestamp in format yyyy-MM-dd-HH-mm-ss");

        //scanner.close();
    }

}
