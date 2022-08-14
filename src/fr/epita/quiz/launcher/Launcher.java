package fr.epita.quiz.launcher;

import fr.epita.quizTest.testMCQChoiceDAO.*;
import fr.epita.quizTest.testOpenQuestionDAO.*;
import fr.epita.quizTest.testQuizBL.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Launcher {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, SQLException {

        System.out.println("Welcome. Are you a Teacher ? (Y|N) : ");
        String isTeacher = scanner.next();

        if("y".equals(isTeacher) || "Y".equals(isTeacher) || "yes".equals(isTeacher) || "YES".equals(isTeacher) || "Yes".equals(isTeacher)) {
            teacherMenu();
        } else {
            studentMenu();
        }
        scanner.close();
    }

    private static void teacherMenu() throws IOException, SQLException {

        FileReader reader=new FileReader("credentials.properties");
        Properties properties = new Properties();

        try {
            properties.load(reader);
            if (properties == null) return;
        } catch (Exception e) {
            System.out.println("error while reading credentials.properties file. make sure the file is configured");
            e.printStackTrace();
        }

        String defaultTeacherName = properties.getProperty("teacherName");
        String defaultTeacherPassword = properties.getProperty("teacherPassword");

        System.out.println("Welcome, please enter your name: ");
        scanner.nextLine();
        String teacherName = scanner.nextLine();
        System.out.println("please enter your password: ");
        String teacherPassword = scanner.nextLine();

        boolean authenticated = teacherName.equals(defaultTeacherName)
                && teacherPassword.equals(defaultTeacherPassword);

        if (!authenticated) {
            // return
            System.out.println("Invalid Credentials. You are not Authenticated!");
            return;
        }

        for(;;) {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n~~~~~~~~~~~~~~~  MENU  ~~~~~~~~~~~~~~~\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1. Display MCQ Questions");
            System.out.println("2. Display Open Questions");
            System.out.println("3. CRUD Operations on MCQ Questions");
            System.out.println("4. CRUD Operations on Open Questions");
            System.out.println("5. Search MCQ Questions Based on Topic");
            System.out.println("6. Search Open Questions Based on Topic");
            System.out.println("7. Prepare Random MCQ Quiz");
            System.out.println("8. Prepare Random Open Question Exam");
            System.out.println("9. Prepare Topic Based MCQ Quiz");
            System.out.println("10. Prepare Topic Based Open Question Exam");
            System.out.println("11. EXIT");
            System.out.println("\nPlease Enter your Choice (1 to 11) : ");
            Integer choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Displaying MCQ Questions");
                    try {
                        TestQuizBLShowMCQQuestionWithChoice showMCQQuestionWithChoice = new TestQuizBLShowMCQQuestionWithChoice();
                        showMCQQuestionWithChoice.test();
                    } catch (Exception e) {
                        System.out.println("Exception Occured while Printing MCQ Questions");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Displaying Open Questions");
                    try {
                        TestQuizBLShowOpenQuestions showOpenQuestions = new TestQuizBLShowOpenQuestions();
                        showOpenQuestions.test();
                    } catch (Exception e) {
                        System.out.println("Exception Occured while Printing Open Questions");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("CRUD Operations on MCQ Questions\n");
                    CRUDMCQMenu();
                    break;

                case 4:
                    System.out.println("CRUD Operations on Open Questions\n");
                    CRUDOpenMenu();
                    break;

                case 5:
                    System.out.println("Search MCQ Questions Based on Topic\n");
                    try {
                        TestMCQChoiceDAOTopicBasedSearch searchMCQ = new TestMCQChoiceDAOTopicBasedSearch();
                        searchMCQ.test();
                    } catch (Exception e) {
                        System.out.println("Error occurred while Searching MCQ Questions Based on a Topic");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Search Open Questions Based on Topic\n");
                    try {
                        TestOpenQuestionDAOTopicBasedSearch searchOpenQuestion = new TestOpenQuestionDAOTopicBasedSearch();
                        searchOpenQuestion.test();
                    } catch (Exception e) {
                        System.out.println("Error occurred while Searching Open Questions Based on a Topic");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("Prepare Random MCQ Quiz\n");
                    try{
                        TestQuizBLPrepareMCQQuiz prepareMCQQuiz = new TestQuizBLPrepareMCQQuiz();
                        prepareMCQQuiz.test();
                    } catch (ParserConfigurationException e) {
                        System.out.println("Error while preparing MCQ Quiz");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    System.out.println("Prepare Random Open Question Exam\n");
                    try {
                        TestQuizBLPrepareOpenExam prepareOpenExam = new TestQuizBLPrepareOpenExam();
                        prepareOpenExam.test();
                    } catch (ParserConfigurationException e) {
                        System.out.println("Error while preparing Random Open Exam");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 9:
                    System.out.println("Prepare Topic Based MCQ Quiz");
                    try {
                        TestQuizBLPrepareTopicMCQQuiz topicMCQQuiz = new TestQuizBLPrepareTopicMCQQuiz();
                        topicMCQQuiz.test();
                    } catch (ParserConfigurationException e) {
                        System.out.println("Error while preparing Topic Based MCQ Quiz");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 10:
                    System.out.println("Prepare Topic Based Open Question Exam");
                    try {
                        TestQuizBLPrepareTopicOpenExam openTopicExam = new TestQuizBLPrepareTopicOpenExam();
                        openTopicExam.test();
                    } catch (ParserConfigurationException e) {
                        System.out.println("Error while preparing Topic Based Open Exam");
                        System.out.println(e.getMessage());
                    }
                    break;

                case 11:
                    System.out.println("Exiting. Good Bye!");
                    return;

                default:
                    System.out.println("Invalid Choice! Please Try Again\n");
                    break;
            }

        }

    }

    private static void CRUDMCQMenu() throws IOException, SQLException {

        TestMCQChoiceDAOCreateTable createTable = new TestMCQChoiceDAOCreateTable();
        createTable.test();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~\n  MCQ Question CRUD MENU \n~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1. Create a MCQ Question");
        System.out.println("2. Read All MCQ Questions");
        System.out.println("3. Update a MCQ Question");
        System.out.println("4. Delete a MCQ Question");
        System.out.println("\nPlease Enter your Choice (1 to 4) : ");

        Integer crudMCQChoice = scanner.nextInt();

        switch (crudMCQChoice) {
            case 1:
                System.out.println("Create a MCQ Question");
                try {
                    TestMCQChoiceDAOInsert mcqInsert = new TestMCQChoiceDAOInsert();
                    mcqInsert.test();
                } catch (Exception e) {
                    System.out.println("Exception occurred while Create MCQ Question");
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                System.out.println("Read All MCQ Questions");
                try {
                    TestMCQChoiceDAOReadAll readAllmcq = new TestMCQChoiceDAOReadAll();
                    readAllmcq.test();
                } catch (Exception e) {
                    System.out.println("Error occurred while Reading All MCQ Questions");
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                System.out.println("Update a MCQ Question");
                try {
                    TestMCQChoiceDAOUpdate updateMCQ = new TestMCQChoiceDAOUpdate();
                    updateMCQ.test();
                } catch (Exception e) {
                    System.out.println("Exception occurred while updating MCQ Question");
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
                System.out.println("Delete a MCQ Question");
                try {
                    TestMCQChoiceDAODelete deleteMCQ = new TestMCQChoiceDAODelete();
                    deleteMCQ.test();
                } catch (Exception e) {
                    System.out.println("Exception occurred while deleting MCQ question");
                    System.out.println(e.getMessage());
                }
                break;

            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    private static void CRUDOpenMenu() throws SQLException {

        TestOpenQuestionDAOCreateTable createTable = new TestOpenQuestionDAOCreateTable();
        createTable.test();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~\n  Open Question CRUD MENU \n~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1. Create an Open Question");
        System.out.println("2. Read All Open Questions");
        System.out.println("3. Update an Open Question");
        System.out.println("4. Delete an Open Question");
        System.out.println("\nPlease Enter your Choice (1 to 4) : ");

        Integer crudOpenChoice = scanner.nextInt();

        switch (crudOpenChoice) {
            case 1:
                System.out.println("Create an Open Question");
                try {
                    TestOpenQuestionDAOInsert insertOpenQuestion = new TestOpenQuestionDAOInsert();
                    insertOpenQuestion.test();
                } catch (Exception e) {
                    System.out.println("Error occurred while ");
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                System.out.println("Read All Open Questions");
                try {
                    TestOpenQuestionDAOReadAll showOpenQuestions = new TestOpenQuestionDAOReadAll();
                    showOpenQuestions.test();
                } catch (Exception e) {
                    System.out.println("Error occurred while ");
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                System.out.println("Update an Open Question");
                try {
                    TestOpenQuestionDAOUpdate updateOpenQuestion = new TestOpenQuestionDAOUpdate();
                    updateOpenQuestion.test();
                } catch (Exception e) {
                    System.out.println("Error occurred while ");
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
                System.out.println("Delete an Open Question");
                try {
                    TestOpenQuestionDAODelete deleteOpenQuestion = new TestOpenQuestionDAODelete();
                    deleteOpenQuestion.test();
                } catch (Exception e) {
                    System.out.println("Error occurred while ");
                    System.out.println(e.getMessage());
                }
                break;

            default:
                System.out.println("Invalid Choice");
                break;
        }

    }

    public static void studentMenu() {

        String[] files;
        File file = new File("./resources");
        files = file.list();

        if (files.length == 0) {
            System.out.println("No Quiz has been Uploaded yet by the Teacher. Good Bye!\n");
            return;
        }

        System.out.println("Below is the list of quiz that has been uploaded by Teachers\n");
        for (String f : files) {
            System.out.println(f);
        }

        System.out.println("\nFrom the above list of quiz, enter carefully the name of a OPEN EXAM you would like to see : ");
        String quizToCheck = scanner.next();

        TestQuizBLParseXMLFile parseXMLFile = new TestQuizBLParseXMLFile();

        parseXMLFile.test(quizToCheck);

    }
}

