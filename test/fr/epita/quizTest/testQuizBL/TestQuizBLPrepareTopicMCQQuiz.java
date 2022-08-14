package fr.epita.quizTest.testQuizBL;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.services.businessLogic.QuizBL;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;

import javax.xml.parsers.ParserConfigurationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestQuizBLPrepareTopicMCQQuiz {

    public static void test() throws SQLException, ParserConfigurationException {

        Scanner scanner = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);
        QuizBL quizBL = new QuizBL();

        System.out.println("Enter a Topic : ");
        String topic = scanner.next();

        Map<String, MCQChoice> mcqChoiceMap = mcqChoiceDAO.topicBasedMCQSearch(topic);
        Map<String, List<MCQChoice>> mcqQuestionList = quizBL.showMCQQuestionWithChoice(mcqChoiceMap);

        System.out.println("Preparing Topic Based MCQ Quiz as an XML File");
        quizBL.prepareMCQQuiz(mcqQuestionList, "TOPIC");
        System.out.println("Prepared Topic Based MCQ Quiz\n\n" +
                "Please Check ./resources folder with file name : 'topicMCQQuiz' followed by timestamp in format yyyy-MM-dd-HH-mm-ss");

        //scanner.close();
    }

}
