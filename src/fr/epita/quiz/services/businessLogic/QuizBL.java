package fr.epita.quiz.services.businessLogic;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.MCQChoiceDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;

import java.sql.SQLException;
import java.util.*;

public class QuizBL {
    public static Map<String, List<MCQChoice>> showMCQQuestionWithChoice(Map<String, MCQChoice> mcqChoiceMap) throws SQLException {

        Map<String, List<MCQChoice>> questionMap = new LinkedHashMap<>();

        for(Map.Entry<String, MCQChoice> entry : mcqChoiceMap.entrySet()) {

            //Get the MCQQuestion from the MCQChoice object
            String mcqQuestion = "Question "
                    + entry.getValue().getMcqQuestion().getMcq_id()
                    + " : "
                    + entry.getValue().getMcqQuestion().getQuestion();

            boolean questionStored = questionMap.containsKey(mcqQuestion);
            List<MCQChoice> mcqChoiceList;

            if(questionStored) {
                //if already present, extract its choice List
                mcqChoiceList = questionMap.get("Question "
                        + entry.getValue().getMcqQuestion().getMcq_id()
                        + " : "
                        + entry.getValue().getMcqQuestion().getQuestion());
            } else {
                //if not present create a new choice list
                mcqChoiceList = new ArrayList<>();
            }
            //push the choice the choicelist
            mcqChoiceList.add(entry.getValue());

            //add the choice list to the map
            questionMap.put(mcqQuestion, mcqChoiceList);
        }

        return questionMap;
    }

    public static Map<String, OpenQuestion> showOpenQuestion() throws SQLException {
        Map<String, OpenQuestion> questionMap = new LinkedHashMap<>();

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        OpenQuestionDAO openQuestionDAO = new OpenQuestionDAO(connectionDAO);

        //Read the choices from the DB using DAO objects
        questionMap = openQuestionDAO.readAllOpenQuestion();

        return questionMap;

    }



}
