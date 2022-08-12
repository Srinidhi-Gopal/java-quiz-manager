package testMCQChoiceDAO;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestMCQChoiceDAO {

    public static void main(String[] args) throws SQLException {

        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        mcqQuestionDAO.createTableMCQQuestion();
        System.out.println("MCQ_QUESTIONS TABLE CREATED");

        mcqChoiceDAO.createTableMCQChoice();
        System.out.println("MCQ_CHOICES TABLE CREATED");

        //Create
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

        System.out.println("Successfully inserted below MCQ Question with its Choices");
        System.out.println("\n" + mcqQuestion);
        for(MCQChoice mc : mcqChoiceList) {
            System.out.println(mc);
        }

        //Read All
        System.out.println("\nRecords in Choice DataBase is as follows :-");
        Map<String, MCQChoice> mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();
        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {
            System.out.println("mcq_choice_id " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nRecords in Questions' DataBase is as follows :-");
        Map<String, MCQQuestion> mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }

        //Update
        System.out.println("\nEnter the mcq_id to update : ");
        Integer updateMCQ_Id = scn.nextInt();

        System.out.println("Enter the mcq_choice_id to update : ");
        Integer updateMCQChoice_Id = scn.nextInt();

        System.out.println("Enter the updated Choice :");
        scn.nextLine();
        String update_choice = scn.nextLine();

        System.out.println("Is Above Choice Valid? (Y|N) : ");
        String validString = scn.next();
        Boolean update_valid = ("Y".equals(validString))
                || ("y".equals(validString))
                || ("yes".equals(validString))
                || ("YES".equals(validString))
                || ("Yes".equals(validString));

        mcqChoiceDAO.updateMCQChoice(new MCQChoice(update_choice, update_valid, null)
                , updateMCQ_Id
                , updateMCQChoice_Id);
        System.out.println("updated choice : " + updateMCQChoice_Id);

        System.out.println("\nRecords in DataBase is as follows :-");
        mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();
        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {
            System.out.println("mcq_choice_id " + entry.getKey() + " : " + entry.getValue());
        }

        //Delete
        System.out.println("\nEnter mcq_choice_id to delete from the above list : ");
        Integer delete_mcq_choice_id = scn.nextInt();

        mcqChoiceDAO.deleteMCQChoice(delete_mcq_choice_id);
        System.out.println("Deleted choice with mcq_choice_id = " + delete_mcq_choice_id);

        System.out.println("\nRecords in DataBase is as follows :-");
        mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();
        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {
            System.out.println("mcq_choice_id " + entry.getKey() + " : " + entry.getValue());
        }

        scn.close();

    }

}

