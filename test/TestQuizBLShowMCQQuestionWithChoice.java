import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.services.businessLogic.QuizBL;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestQuizBLShowMCQQuestionWithChoice {

    public static void main(String[] args) throws SQLException {

        QuizBL quizBLServices = new QuizBL();

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        MCQChoiceDAO mcqChoiceDAO = new MCQChoiceDAO(connectionDAO);

        //Read the choices from the DB using DAO objects
        Map<String, MCQChoice> mcqChoiceMap = mcqChoiceDAO.readAllMCQChoice();

        Map<String, List<MCQChoice>> mcqQuestionListMap = quizBLServices.showMCQQuestionWithChoice(mcqChoiceMap);
        Integer num = 1;
        System.out.println();

        for(Map.Entry<String, List<MCQChoice>> entry : mcqQuestionListMap.entrySet()) {
            num = 1;
            System.out.println(entry.getKey());
            for(MCQChoice choice : entry.getValue()) {
                System.out.println((num++) + " : " + choice.getChoice());
            }
            System.out.println();
        }
    }

}
