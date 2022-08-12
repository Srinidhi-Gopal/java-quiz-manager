package testMCQChoiceDAO;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestMCQChoiceDAODelete {

    public static void main(String[] args) throws SQLException {

        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        System.out.println("\nRecords of MCQ_CHOICES is as follows :-");
        Map<String, MCQChoice> mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();
        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {
            System.out.println("mcq_choice_id " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nEnter mcq_choice_id to delete from the above list : ");
        Integer delete_mcq_choice_id = scn.nextInt();

        mcqChoiceDAO.deleteMCQChoice(delete_mcq_choice_id);
        System.out.println("Deleted choice with mcq_choice_id = " + delete_mcq_choice_id);

        System.out.println("\nRecords of MCQ_CHOICES is as follows :-");
        mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();
        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {
            System.out.println("mcq_choice_id " + entry.getKey() + " : " + entry.getValue());
        }

        scn.close();

    }

}
