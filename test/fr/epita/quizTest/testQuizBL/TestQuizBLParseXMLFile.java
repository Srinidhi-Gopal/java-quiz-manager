package fr.epita.quizTest.testQuizBL;

import fr.epita.quiz.services.businessLogic.QuizBL;

public class TestQuizBLParseXMLFile {

    public static void test(String fileName) {
        QuizBL quizBL = new QuizBL();
        String filePath = "./resources/" + fileName;
        quizBL.parseXMLFile(filePath);
    }

}

