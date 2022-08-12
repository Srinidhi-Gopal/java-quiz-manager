package testOpenQuestionDAO;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQQuestionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TestOpenQuestionDAOInsert {

    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        System.out.println("Enter a question : ");
        String question = scn.nextLine();

        System.out.println("Enter comma separated topic for this question (Example- java,uml,oop) : ");
        String topics = scn.next();
        List<String> topicList = List.of(topics.split(","));

        System.out.println("Enter the Difficulty level as an Integer between 1 and 5 : ");
        Integer difficulty = scn.nextInt();

        OpenQuestion openQuestion = new OpenQuestion(question, topicList, difficulty);
        Integer open_question_id = openQuestionDAO.createOpenQuestion(openQuestion);
        System.out.println("Successfully Inserted question whose mcq_id : " + open_question_id);

        scn.close();
    }

}
