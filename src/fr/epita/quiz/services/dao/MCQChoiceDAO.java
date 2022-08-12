package fr.epita.quiz.services.dao;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MCQChoiceDAO {

    private Integer mcqChoiceId;
    private CreateDBConnectionDAO connectionDAO;

    public MCQChoiceDAO(CreateDBConnectionDAO connectionDAO) throws SQLException {
        this.connectionDAO = connectionDAO;
        Integer curVal = getCurrentMcqChoice_ID();
        if(curVal != null) {
            this.mcqChoiceId = curVal;
        } else {
            this.mcqChoiceId = 0;
        }
    }

    public Integer getMcqChoiceId() {
        return mcqChoiceId;
    }

    public CreateDBConnectionDAO getConnectionDAO() {
        return connectionDAO;
    }

    public void setMcqChoiceId(Integer mcqChoiceId) {
        this.mcqChoiceId = mcqChoiceId;
    }

    private Integer getCurrentMcqChoice_ID() throws SQLException {
        Integer cur_MCQChoiceId = null;
        Connection con = connectionDAO.makeDBConnection();
        String query = "SELECT mcq_choice_id FROM (SELECT mcq_choice_id from MCQ_CHOICES ORDER BY mcq_choice_id::int DESC) TBL LIMIT 1";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                cur_MCQChoiceId = Integer.parseInt(resultSet.getString("mcq_choice_id"));
            }
        } catch (PSQLException psqlException) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        connectionDAO.closeConnection(con);

        return cur_MCQChoiceId;
    }

    public void createTableMCQChoice() throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        String createTableQuery = "CREATE TABLE IF NOT EXISTS MCQ_CHOICES( mcq_choice_id varchar(30) NOT NULL UNIQUE" +
                ", mcq_id varchar(30)" +
                ", choice varchar(3000)" +
                ", valid boolean)";

        con.prepareStatement(createTableQuery).execute();
        connectionDAO.closeConnection(con);
    }

    public Integer createMCQChoice(MCQChoice mcqChoice, Integer mcq_id) throws  SQLException {
        Connection con = connectionDAO.makeDBConnection();
        this.mcqChoiceId = this.mcqChoiceId + 1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO MCQ_CHOICES VALUES (?, ?, ?, ?)");
            ps.setString(1, String.valueOf(this.mcqChoiceId));
            ps.setString(2, String.valueOf(mcq_id));
            ps.setString(3, mcqChoice.getChoice());
            ps.setBoolean(4, mcqChoice.getValid());

            ps.execute();

        }catch (Exception e) {
            System.out.println("Error while inserting choice : " + mcqChoice.getChoice() + " : for mcq_id : " + mcq_id);
            e.printStackTrace();
        }

        connectionDAO.closeConnection(con);
        return this.mcqChoiceId;
    }

    public void updateMCQChoice(MCQChoice mcqChoice,Integer mcqQuestionId, Integer mcqChoiceId) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        try{
            PreparedStatement ps = con.prepareStatement("UPDATE MCQ_CHOICES SET choice = ?" +
                    ", valid = ?" +
                    " WHERE mcq_id = ?" +
                    " AND mcq_choice_id = ?");
            ps.setString(1, mcqChoice.getChoice());
            ps.setBoolean(2, mcqChoice.getValid());
            ps.setString(3, String.valueOf(mcqQuestionId));
            ps.setString(4, String.valueOf(mcqChoiceId));

            ps.execute();

        } catch (Exception e) {
            System.out.println("Error while updating choice : " + mcqChoiceId + " : of question : " + mcqQuestionId);
            e.printStackTrace();
        }

        connectionDAO.closeConnection(con);
    }

    public void deleteMCQChoice(Integer mcqChoiceId) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();
        try{
            PreparedStatement ps = con.prepareStatement("DELETE FROM MCQ_CHOICES WHERE mcq_choice_id = ?");
            ps.setString(1, String.valueOf(mcqChoiceId));
            ps.execute();
        } catch (Exception e) {
            System.out.println("Error while deleting MCQ Choice with mcq_choice_id = " + mcqChoiceId);
            e.printStackTrace();
        }
        connectionDAO.closeConnection(con);
    }

    public Map<String, MCQChoice> readAllMCQChoice() throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        Map<String, MCQChoice> mcqChoiceMap = new LinkedHashMap<>();
        String sqlQuery = "SELECT  mcq_choice_id" +
                ", mcq_id" +
                ", (SELECT question FROM MCQ_QUESTIONS MQ WHERE MQ.mcq_id = MC.mcq_id) question" +
                ", (SELECT topics FROM MCQ_QUESTIONS MQ WHERE MQ.mcq_id = MC.mcq_id) topics" +
                ", (SELECT difficulty FROM MCQ_QUESTIONS MQ WHERE MQ.mcq_id = MC.mcq_id) difficulty" +
                ", choice" +
                ", valid " +
                "from MCQ_CHOICES MC";

        PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {

            String mcq_choice_id = resultSet.getString("mcq_choice_id");
            String mcq_id = resultSet.getString("mcq_id");
            String question = resultSet.getString("question");
            String topics = resultSet.getString("topics");
            List<String> topicList = List.of(topics.split(","));
            String difficulty = resultSet.getString("difficulty");
            String choice = resultSet.getString("choice");
            Boolean valid = resultSet.getBoolean("valid");

            MCQQuestion mcqQuestion = new MCQQuestion(question
                    , topicList
                    , Integer.parseInt(difficulty));
            mcqQuestion.setMcq_id(mcq_id);

            MCQChoice mcqChoice = new MCQChoice(choice
                    , valid
                    , mcqQuestion);
            mcqChoice.setMcq_choice_id(mcq_choice_id);
            mcqChoice.setMcq_id(mcq_id);

            mcqChoiceMap.put(mcq_choice_id, mcqChoice);
        }

        connectionDAO.closeConnection(con);

        return mcqChoiceMap;
    }

    public Map<String, MCQChoice> topicBasedMCQSearch(String topic) throws SQLException {
        Connection con = connectionDAO.makeDBConnection();

        Map<String, MCQChoice> mcqChoiceMap = new LinkedHashMap<>();
        String sqlQuery = "SELECT TBL.mcq_id mcq_id" +
                ", TBL.question question" +
                ", TBL.topics topics" +
                ", TBL.difficulty difficulty" +
                ", MC.mcq_choice_id mcq_choice_id" +
                ", MC.choice choice" +
                ", MC.valid valid " +
                "FROM (SELECT mcq_id, question, topics, difficulty FROM MCQ_QUESTIONS MQ WHERE MQ.topics LIKE '%" + topic + "%'" +
                ") TBL, MCQ_CHOICES MC " +
                "WHERE TBL.mcq_id = MC.mcq_id";

        PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {

            String mcq_id = resultSet.getString("mcq_id");
            String question = resultSet.getString("question");
            String topics = resultSet.getString("topics");
            List<String> topicList = List.of(topics.split(","));
            String difficulty = resultSet.getString("difficulty");
            String mcq_choice_id = resultSet.getString("mcq_choice_id");
            String choice = resultSet.getString("choice");
            Boolean valid = resultSet.getBoolean("valid");

            MCQQuestion mcqQuestion = new MCQQuestion(question
                    , topicList
                    , Integer.parseInt(difficulty));
            mcqQuestion.setMcq_id(mcq_id);

            MCQChoice mcqChoice = new MCQChoice(choice
                    , valid
                    , mcqQuestion);
            mcqChoice.setMcq_choice_id(mcq_choice_id);
            mcqChoice.setMcq_id(mcq_id);

            mcqChoiceMap.put(mcq_choice_id, mcqChoice);
        }

        connectionDAO.closeConnection(con);

        return mcqChoiceMap;
    }

}
