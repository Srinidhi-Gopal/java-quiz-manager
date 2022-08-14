package fr.epita.quizTest.testQuizBL;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.businessLogic.QuizBL;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestQuizBLShowOpenQuestions {

    public static void test() throws SQLException {

        QuizBL quizBL = new QuizBL();

        Map<String, OpenQuestion> openQuestionMap = quizBL.showOpenQuestion();
        System.out.println();

        for(Map.Entry<String, OpenQuestion> entry : openQuestionMap.entrySet()) {
            System.out.println("Question " + entry.getKey() + " : " + entry.getValue().getQuestion());
        }
    }

}
