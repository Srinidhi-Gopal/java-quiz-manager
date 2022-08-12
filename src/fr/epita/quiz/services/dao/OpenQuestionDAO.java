package fr.epita.quiz.services.dao;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OpenQuestionDAO {

    private Integer openQuestionId;
    private CreateDBConnectionDAO connectionDAO;

    public OpenQuestionDAO(CreateDBConnectionDAO connectionDAO) throws SQLException {
        this.connectionDAO = connectionDAO;
        Integer curVal = getCurrentOpenQuestion_ID();
        if (curVal != null) {
            this.openQuestionId = curVal;
        } else {
            this.openQuestionId = 0;
        }
    }

    public Integer getOpenQuestionId() {
        return openQuestionId;
    }

    private Integer getCurrentOpenQuestion_ID() throws SQLException {
        Integer cur_open_question_id = null;
        Connection con = connectionDAO.makeDBConnection();
        String query = "SELECT open_question_id FROM (SELECT open_question_id from OPEN_QUESTIONS ORDER BY open_question_id::int DESC) TBL LIMIT 1";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cur_open_question_id = Integer.parseInt(resultSet.getString("open_question_id"));
            }
        } catch (PSQLException psqlException) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        connectionDAO.closeConnection(con);

        return cur_open_question_id;
    }

    public void createTableOpenQuestion() throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        String createTableQuery = "CREATE TABLE IF NOT EXISTS OPEN_QUESTIONS( open_question_id varchar(30) NOT NULL UNIQUE" +
                ", question varchar(3000)" +
                ", topics varchar(1000)" +
                ", difficulty varchar(10))";

        con.prepareStatement(createTableQuery).execute();
        connectionDAO.closeConnection(con);
    }

    public Integer createOpenQuestion(OpenQuestion openQuestion) throws  SQLException {
        Connection con = connectionDAO.makeDBConnection();
        this.openQuestionId = this.openQuestionId + 1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO OPEN_QUESTIONS VALUES (?, ?, ?, ?)");
            ps.setString(1, String.valueOf(this.openQuestionId));
            ps.setString(2, openQuestion.getQuestion());
            ps.setString(3, String.join(",", openQuestion.getTopics()));
            ps.setString(4, String.valueOf(openQuestion.getDifficulty()));

            ps.execute();

        }catch (Exception e) {
            System.out.println("Error while inserting mcq question : " + openQuestion.getQuestion());
            e.printStackTrace();
        }

        connectionDAO.closeConnection(con);
        return this.openQuestionId;
    }

    public void updateOpenQuestion(OpenQuestion openQuestion, Integer openQuestionId) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        try{
            PreparedStatement ps = con.prepareStatement("UPDATE OPEN_QUESTIONS SET question = ?" +
                    ", topics = ?" +
                    ", difficulty = ?" +
                    " WHERE open_question_id = ?");
            ps.setString(1, openQuestion.getQuestion());
            ps.setString(2, String.join(",", openQuestion.getTopics()));
            ps.setString(3, String.valueOf(openQuestion.getDifficulty()));
            ps.setString(4, String.valueOf(openQuestionId));

            ps.execute();

        } catch (Exception e) {
            System.out.println("Error while updating MCQ Question : " + openQuestion.getQuestion());
            e.printStackTrace();
        }

        connectionDAO.closeConnection(con);
    }

    public void deleteOpenQuestion(Integer openQuestionId) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();
        try{
            PreparedStatement ps = con.prepareStatement("DELETE FROM OPEN_QUESTIONS WHERE open_question_id = ?");
            ps.setString(1, String.valueOf(openQuestionId));
            ps.execute();
        } catch (Exception e) {
            System.out.println("Error while deleting Open Question with open_question_id = " + openQuestionId);
            e.printStackTrace();
        }
        connectionDAO.closeConnection(con);
    }

    public Map<String, OpenQuestion> readAllOpenQuestion() throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        Map<String, OpenQuestion> openQuestionMap = new LinkedHashMap<>();
        String sqlQuery = "SELECT open_question_id, question, topics, difficulty from OPEN_QUESTIONS";

        PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            String open_question_id = resultSet.getString("open_question_id");
            String question = resultSet.getString("question");
            String topics = resultSet.getString("topics");
            List<String> topicList = List.of(topics.split(","));
            String difficulty = resultSet.getString("difficulty");

            OpenQuestion openQuestion = new OpenQuestion(question
                    , topicList
                    , Integer.parseInt(difficulty));
            openQuestion.setOpen_question_id(open_question_id);

            openQuestionMap.put(open_question_id, openQuestion);
        }

        connectionDAO.closeConnection(con);

        return openQuestionMap;
    }

    public Map<String, OpenQuestion> topicBasedOpenQuestionSearch(String topic) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        Map<String, OpenQuestion> openQuestionMap = new LinkedHashMap<>();
        String sqlQuery = "SELECT open_question_id " +
                ", question" +
                ", topics" +
                ", difficulty" +
                " FROM OPEN_QUESTIONS " +
                " WHERE topics LIKE '%" + topic + "%'";

        PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {

            String open_question_id = resultSet.getString("open_question_id");
            String question = resultSet.getString("question");
            String topics = resultSet.getString("topics");
            List<String> topicList = List.of(topics.split(","));
            String difficulty = resultSet.getString("difficulty");

            OpenQuestion openQuestion = new OpenQuestion(question
                    , topicList
                    , Integer.parseInt(difficulty));
            openQuestion.setOpen_question_id(open_question_id);

            openQuestionMap.put(open_question_id, openQuestion);
        }

        connectionDAO.closeConnection(con);

        return openQuestionMap;
    }

}


