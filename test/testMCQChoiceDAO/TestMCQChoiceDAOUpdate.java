package testMCQChoiceDAO;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestMCQChoiceDAOUpdate {

    public static void main(String[] args) throws SQLException {

        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

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

        scn.close();

    }

}
