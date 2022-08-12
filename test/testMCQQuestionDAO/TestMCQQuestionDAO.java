package testMCQQuestionDAO;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestMCQQuestionDAO {

    public static void main(String[] args) throws SQLException {

        Scanner scn = new Scanner(System.in);
        //create table api
        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQQuestionDAO mcqQuestionDAO = new MCQQuestionDAO(connectionDAO);

        mcqQuestionDAO.createTableMCQQuestion();
        System.out.println("MCQ_QUESTIONS TABLE CREATED");
        //insert api
        System.out.println("Enter a question : ");
        String question = scn.nextLine();

        System.out.println("Enter comma separated topic for this question (Example- java,uml,oop) : ");
        String topics = scn.next();
        List<String> topicList = List.of(topics.split(","));

        System.out.println("Enter the Difficulty level as an Integer between 1 and 10 : ");
        Integer difficulty = scn.nextInt();

        MCQQuestion mcqQuestion = new MCQQuestion(question, topicList, difficulty);
        Integer mcq_id = mcqQuestionDAO.createMCQQuestion(mcqQuestion);
        System.out.println("Successfully Inserted question whose mcq_id : " + mcq_id);
        //read all api
        Map<String, MCQQuestion> mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }
        //update
        System.out.println("From the Above list enter mcq_id whose record needs to be updated : ");
        Integer updateMCQ_Id = scn.nextInt();

        System.out.println("Enter the new MCQ Question for mcq_id " + updateMCQ_Id + " : ");
        scn.nextLine();
        String update_question = scn.nextLine();

        System.out.println("Enter comma separated topic for this question (Example- java,uml,oop) : ");
        String update_topics = scn.next();
        List<String> update_topicList = List.of(update_topics.split(","));

        System.out.println("Enter the Difficulty level as an Integer between 1 and 10 : ");
        Integer update_difficulty = scn.nextInt();

        MCQQuestion update_mcqQuestion = new MCQQuestion(update_question, update_topicList, update_difficulty);
        mcqQuestionDAO.updateMCQQuestion(update_mcqQuestion, updateMCQ_Id);

        System.out.println("Updated MCQ Questions is as follows : ");
        mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }
        //delete
        System.out.println("From the Above List Enter mcq_id to Delete : ");
        Integer delete_mcqId = scn.nextInt();

        mcqQuestionDAO.deleteMCQQuestion(delete_mcqId);

        System.out.println("After MCQ Question Deletion, DB is as follows : ");
        mcqQuestionMap = mcqQuestionDAO.readAllMCQQuestion();
        for(Map.Entry<String, MCQQuestion> entry : mcqQuestionMap.entrySet()) {
            System.out.println("mcq_id " + entry.getKey() + " : " + entry.getValue());
        }

        scn.close();

    }

}

