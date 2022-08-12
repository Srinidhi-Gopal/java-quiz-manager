package fr.epita.quizTest;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.services.businessLogic.QuizBL;

import javax.xml.parsers.ParserConfigurationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestQuizBLPrepareMCQQuiz {

    public static void test() throws SQLException, ParserConfigurationException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Below is List of MCQ Questions Available in the System\n");

        TestQuizBLShowMCQQuestionWithChoice question = new TestQuizBLShowMCQQuestionWithChoice();
        question.test();

        System.out.println("Enter a COMMA SEPARATED STRING OF QUESTION NUMBERS for the quiz (Example - 1,2,3) : ");
        String queslist = scanner.nextLine();

        List<String> questionList = List.of(queslist.split(","));

        List<Integer> quesIntList = new ArrayList<>();

        for(String s : questionList) {
            quesIntList.add(Integer.valueOf(s));
        }

        QuizBL quizBL = new QuizBL();
        Map<String, List<MCQChoice>> mcqQuestionList = quizBL.getSelectedMCQQuestion(quesIntList);

        System.out.println("Preparing MCQ Quiz as an XML File");
        quizBL.prepareMCQQuiz(mcqQuestionList);
        System.out.println("Prepared MCQ Quiz\n\n" +
                "Please Check ./resources folder with file name : 'randomMCQQuiz' followed by timestamp in format yyyy-MM-dd-HH-mm-ss");

        //scanner.close();
    }

}
