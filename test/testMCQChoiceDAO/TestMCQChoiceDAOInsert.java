package testMCQChoiceDAO;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestMCQChoiceDAOInsert {

    public static void main(String[] args) throws SQLException {

        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

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

        System.out.println("Enter number of choices for the above question : ");
        Integer choiceCount = scn.nextInt();

        List<MCQChoice> mcqChoiceList = new ArrayList<>();
        for(int i = 1; i <= choiceCount; i++) {
            System.out.println("Enter Choice " + i + " for the above question");
            scn.nextLine();
            String choice1 = scn.nextLine();

            System.out.println("Is Choice " + i + " Valid? (Y|N) : ");
            String validString = scn.next();
            Boolean valid = ("Y".equals(validString))
                    || ("y".equals(validString))
                    || ("yes".equals(validString))
                    || ("YES".equals(validString))
                    || ("Yes".equals(validString));

            MCQChoice mcqChoice = new MCQChoice(choice1, valid, mcqQuestion);
            mcqChoiceList.add(mcqChoice);
            Integer mcqChoiceId = mcqChoiceDAO.createMCQChoice(mcqChoice, mcq_id);
            System.out.println("Successfully Inserted choice with mcq_choice_id = " + mcqChoiceId + " for question whose mcq_id = " + mcq_id);
        }

        System.out.println("Successfully inserted below MCQ Question with its Choices\n");
        System.out.println(mcqQuestion);
        for(MCQChoice mc : mcqChoiceList) {
            System.out.println(mc);
        }

        scn.close();

    }

}

