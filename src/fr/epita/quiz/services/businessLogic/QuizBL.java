package fr.epita.quiz.services.businessLogic;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.services.dao.CreateDBConnectionDAO;
import fr.epita.quiz.services.dao.OpenQuestionDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    public static Map<String, List<MCQChoice>> getSelectedMCQQuestion(List<Integer> mcqQuestionNumbers) throws SQLException{

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        Connection con = connectionDAO.makeDBConnection();

        String whereCondition = "WHERE MQ.mcq_id IN (";

        Map<String, MCQChoice> mcqChoiceMap = new LinkedHashMap<>();

        for(Integer questionNum : mcqQuestionNumbers) {
            whereCondition = whereCondition + "'" + String.valueOf(questionNum) + "',";
        }
        whereCondition = whereCondition.substring(0, (whereCondition.length() - 1));
        whereCondition = whereCondition + ')';

        String sqlQuery =  "SELECT MC.mcq_choice_id mcq_choice_id" +
                ", MC.mcq_id mcq_id" +
                ", TBL.question question" +
                ", TBL.topics topics" +
                ", TBL.difficulty difficulty" +
                ", MC.choice choice" +
                ", MC.valid valid " +
                "FROM (SELECT mcq_id, question, topics, difficulty FROM MCQ_QUESTIONS MQ " + whereCondition +
                ") TBL, MCQ_CHOICES MC " +
                "WHERE TBL.mcq_id = MC.mcq_id";

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

        Map<String, List<MCQChoice>> mcqQuestionList = showMCQQuestionWithChoice(mcqChoiceMap);
        return mcqQuestionList;
    }

    public static void prepareMCQQuiz(Map<String, List<MCQChoice>> mcqQuestionList, String type) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document dom = docBuilder.newDocument();
        Element e = null, c = null, m = null;
        Integer num = 1;
        Element rootElement = dom.createElement("MCQQuiz");

        for(Map.Entry<String, List<MCQChoice>> entry : mcqQuestionList.entrySet()) {

            num = 0;
            e = dom.createElement("question");
            e.setAttribute("value", entry.getKey());
            m = dom.createElement("choiceList");

            for(MCQChoice choice : entry.getValue()) {
                num = num + 1;
                String choiceNum = String.valueOf(num);
                String choiceTag = "choice" + choiceNum;
                c = dom.createElement(choiceTag);
                c.setAttribute("choiceId", choiceNum);
                c.appendChild(dom.createTextNode(choice.getChoice()));
                m.appendChild(c);
            }

            e.appendChild(m);
            rootElement.appendChild(e);
        }

        dom.appendChild(rootElement);

        String fileName;

        if ("RANDOM".equals(type)) {
            fileName = "./resources/randomMCQQuiz" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        } else if ("TOPIC".equals(type)) {
            fileName = "./resources/topicMCQQuiz" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        } else {
            fileName = "./resources/MCQQuiz" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        }
        try (FileOutputStream output = new FileOutputStream(fileName)) {
            writeXml(dom, output);
        } catch (IOException exe) {
            exe.printStackTrace();
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    public static Map<String, OpenQuestion> getSelectedOpenQuestion(List<Integer> openQuestionNumbers) throws SQLException{

        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        Connection con = connectionDAO.makeDBConnection();

        String whereCondition = "WHERE open_question_id IN (";

        Map<String, OpenQuestion> openQuestionMap = new LinkedHashMap<>();

        for(Integer questionNum : openQuestionNumbers) {
            whereCondition = whereCondition + "'" + String.valueOf(questionNum) + "',";
        }
        whereCondition = whereCondition.substring(0, (whereCondition.length() - 1));
        whereCondition = whereCondition + ')';

        String sqlQuery = "SELECT open_question_id" +
                ", question" +
                ", topics" +
                ", difficulty" +
                " FROM OPEN_QUESTIONS " +
                whereCondition;

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

    public static void prepareOpenExam(Map<String, OpenQuestion> openQuestionList, String type) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document dom = docBuilder.newDocument();
        Element e = null;
        Element rootElement = dom.createElement("OpenExam");

        for(Map.Entry<String, OpenQuestion> entry : openQuestionList.entrySet()) {
            e = dom.createElement("question");
            e.setAttribute("value", entry.getValue().getQuestion());
            //e.appendChild(dom.createTextNode(entry.getValue().getQuestion()));
            rootElement.appendChild(e);
        }

        dom.appendChild(rootElement);

        String fileName;
        if("RANDOM".equals(type)) {
            fileName = "./resources/randomOpenExam" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        } else if ("TOPIC".equals(type)) {
            fileName = "./resources/topicOpenExam" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        } else {
            fileName = "./resources/OpenExam" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        }
        try (FileOutputStream output = new FileOutputStream(fileName)) {
            writeXml(dom, output);
        } catch (IOException exe) {
            exe.printStackTrace();
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void parseXMLFile(String fileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(fileName));

            doc.getDocumentElement().normalize();

            String rootElement = doc.getDocumentElement().getNodeName();

            if("MCQQuiz".equals(rootElement)) {
                System.out.println("This is a MCQ Quiz. Display of MCQ Quiz is Work in Progress\n");
                System.out.println("Please refer the " + fileName + " for the XML File of MCQ Questions");
            } else {
                System.out.println("This is an Open Exam. Below are the questions\n");

                NodeList list = doc.getElementsByTagName("question");
                Integer num = 1;

                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        System.out.println((num++) + " : " + element.getAttribute("value"));
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        }
    }
}
