package fr.epita.quizTest.testQuizBL;

import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.businessLogic.QuizBL;

import javax.xml.parsers.ParserConfigurationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestQuizBLPrepareOpenExam {

    public static void test() throws SQLException, ParserConfigurationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Below is List of Open Questions Available in the System\n");

        TestQuizBLShowOpenQuestions questions = new TestQuizBLShowOpenQuestions();
        questions.test();

        System.out.println("Enter a COMMA SEPARATED STRING OF QUESTION NUMBERS for the Exam (Example - 1,2,3) : ");
        String queslist = scanner.nextLine();

        List<String> questionList = List.of(queslist.split(","));

        List<Integer> quesIntList = new ArrayList<>();

        for(String s : questionList) {
            quesIntList.add(Integer.valueOf(s));
        }

        QuizBL quizBL = new QuizBL();
        Map<String, OpenQuestion> selectedQuestionList = quizBL.getSelectedOpenQuestion(quesIntList);

        System.out.println("Preparing Random Open Exam as an XML File");
        quizBL.prepareOpenExam(selectedQuestionList, "RANDOM");
        System.out.println("Prepared Random Open Exam\n\n" +
                "Please Check ./resources folder with file name : 'randomOpenExam' followed by timestamp in format yyyy-MM-dd-HH-mm-ss");

        //scanner.close();
    }

}
