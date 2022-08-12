package fr.epita.quizTest.testMCQChoiceDAO;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.services.businessLogic.QuizBL;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestMCQChoiceDAOTopicBasedSearch {

    public static void test() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        QuizBL quizBL = new QuizBL();

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        System.out.println("Enter a Topic : ");
        String topic = scanner.next();

        //Read the choices from the DB using DAO objects
        Map<String, MCQChoice> mcqChoiceMap = mcqChoiceDAO.topicBasedMCQSearch(topic);

        Map<String, List<MCQChoice>> mcqQuestionListMap = quizBL.showMCQQuestionWithChoice(mcqChoiceMap);
        Integer num = 1;
        System.out.println();

        for(Map.Entry<String, List<MCQChoice>> entry : mcqQuestionListMap.entrySet()) {
            num = 1;
            System.out.println(entry.getKey());
            for(MCQChoice choice : entry.getValue()) {
                System.out.println((num++) + " : " + choice.getChoice());
            }
            System.out.println();
        }

        //scanner.close();
    }

}
