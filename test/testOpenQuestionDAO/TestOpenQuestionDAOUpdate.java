package testOpenQuestionDAO;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestOpenQuestionDAOUpdate {

    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        Map<String, OpenQuestion> openQuestionMap = openQuestionDAO.readAllOpenQuestion();
        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("open_question_id " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("From the Above list enter open_question_id whose record needs to be updated : ");
        Integer updateOpen_question_Id = scn.nextInt();

        System.out.println("Enter the new Open Question for open_question_id " + updateOpen_question_Id + " : ");
        scn.nextLine();
        String update_question = scn.nextLine();

        System.out.println("Enter new comma separated topic for this question (Example- java,uml,oop) : ");
        String update_topics = scn.next();
        List<String> update_topicList = List.of(update_topics.split(","));

        System.out.println("Enter the new Difficulty level as an Number between 1 and 5 : ");
        Integer update_difficulty = scn.nextInt();

        OpenQuestion update_openQuestion = new OpenQuestion(update_question, update_topicList, update_difficulty);
        openQuestionDAO.updateOpenQuestion(update_openQuestion, updateOpen_question_Id);

        System.out.println("Updated Open Questions " + updateOpen_question_Id);
        System.out.println("\nRecords of OPEN_QUESTIONS are as follows");

        openQuestionMap = openQuestionDAO.readAllOpenQuestion();
        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("open_question_id " + entry.getKey() + " : " + entry.getValue());
        }

        scn.close();
    }

}

