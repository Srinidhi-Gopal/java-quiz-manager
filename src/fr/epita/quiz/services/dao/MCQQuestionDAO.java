package fr.epita.quiz.services.dao;

import fr.epita.quiz.datamodel.MCQQuestion;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MCQQuestionDAO {

    private Integer mcqQuestionId;
    private CreateDBConnectionDAO connectionDAO;

    public MCQQuestionDAO(CreateDBConnectionDAO connectionDAO) throws SQLException {
        this.connectionDAO = connectionDAO;
        Integer curVal = getCurrentMCQ_ID();
        if(curVal != null) {
            this.mcqQuestionId = curVal;
        } else {
            this.mcqQuestionId = 0;
        }
    }

    public Integer getMcqQuestionId() {
        return mcqQuestionId;
    }

    private Integer getCurrentMCQ_ID() throws SQLException {
        Integer cur_MCQId = null;
        Connection con = connectionDAO.makeDBConnection();
        String query = "SELECT mcq_id FROM (SELECT mcq_id from MCQ_QUESTIONS ORDER BY mcq_id::int DESC) TBL LIMIT 1";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cur_MCQId = Integer.parseInt(resultSet.getString("mcq_id"));
            }
        } catch (PSQLException psqlException) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        connectionDAO.closeConnection(con);

        return cur_MCQId;
    }

    public void createTableMCQQuestion() throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        String createTableQuery = "CREATE TABLE IF NOT EXISTS MCQ_QUESTIONS( mcq_id varchar(30) NOT NULL UNIQUE" +
                ", question varchar(3000)" +
                ", topics varchar(1000)" +
                ", difficulty varchar(10))";

        con.prepareStatement(createTableQuery).execute();
        connectionDAO.closeConnection(con);
    }

    public Integer createMCQQuestion(MCQQuestion mcqQuestion) throws  SQLException {
        Connection con = connectionDAO.makeDBConnection();
        this.mcqQuestionId = this.mcqQuestionId + 1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO MCQ_QUESTIONS VALUES (?, ?, ?, ?)");
            ps.setString(1, String.valueOf(this.mcqQuestionId));
            ps.setString(2, mcqQuestion.getQuestion());
            ps.setString(3, String.join(",", mcqQuestion.getTopics()));
            ps.setString(4, String.valueOf(mcqQuestion.getDifficulty()));

            ps.execute();

        }catch (Exception e) {
            System.out.println("Error while inserting mcq question : " + mcqQuestion.getQuestion());
            e.printStackTrace();
        }

        connectionDAO.closeConnection(con);
        return this.mcqQuestionId;
    }

    public void updateMCQQuestion(MCQQuestion mcqQuestion, Integer mcqQuestionId) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        try{
            PreparedStatement ps = con.prepareStatement("UPDATE MCQ_QUESTIONS SET question = ?" +
                    ", topics = ?" +
                    ", difficulty = ?" +
                    " WHERE mcq_id = ?");
            ps.setString(1, mcqQuestion.getQuestion());
            ps.setString(2, String.join(",", mcqQuestion.getTopics()));
            ps.setString(3, String.valueOf(mcqQuestion.getDifficulty()));
            ps.setString(4, String.valueOf(mcqQuestionId));

            ps.execute();

        } catch (Exception e) {
            System.out.println("Error while updating MCQ Question : " + mcqQuestion.getQuestion());
            e.printStackTrace();
        }

        connectionDAO.closeConnection(con);
    }

    public void deleteMCQQuestion(Integer mcqQuestionId) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();
        try{
            PreparedStatement ps = con.prepareStatement("DELETE FROM MCQ_QUESTIONS WHERE mcq_id = ?");
            ps.setString(1, String.valueOf(mcqQuestionId));
            ps.execute();
        } catch (Exception e) {
            System.out.println("Error while deleting MCQ Question with mcq_id = " + mcqQuestionId);
            e.printStackTrace();
        }
        connectionDAO.closeConnection(con);
    }

    public Map<String, MCQQuestion> readAllMCQQuestion() throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        Map<String, MCQQuestion> mcqQuestionMap = new LinkedHashMap<>();
        String sqlQuery = "SELECT  mcq_id, question, topics, difficulty from MCQ_QUESTIONS";

        PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            String mcq_id = resultSet.getString("mcq_id");
            String question = resultSet.getString("question");
            String topics = resultSet.getString("topics");
            List<String> topicList = List.of(topics.split(","));
            String difficulty = resultSet.getString("difficulty");

            mcqQuestionMap.put(mcq_id, (new MCQQuestion(question
                    , topicList
                    , Integer.parseInt(difficulty))));
        }

        connectionDAO.closeConnection(con);

        return mcqQuestionMap;
    }

}


