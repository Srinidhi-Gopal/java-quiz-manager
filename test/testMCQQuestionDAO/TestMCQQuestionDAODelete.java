package testMCQQuestionDAO;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class TestMCQQuestionDAODelete {

    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);

        System.out.println();
        Map<String, MCQQuestion> mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nFrom the Above List Enter mcq_id to Delete : ");
        Integer delete_mcqId = scn.nextInt();

        mcqQuestionDAO.deleteMCQQuestion(delete_mcqId);

        System.out.println("\nAfter MCQ Question Deletion, DB is as follows : \n");
        mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }
        scn.close();
    }

}
