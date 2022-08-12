package fr.epita.quiz.services.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CreateDBConnectionDAO {

    private Connection connection;

    public CreateDBConnectionDAO() {
        this.connection = null;
    }

    private String[] getDBDetails() {
        String[] fields = new String[3];
        try{
            List<String> lines = Files.readAllLines(new File("./resources/db_connection_details.csv").toPath());
            String line = lines.get(1);
            fields = line.split(",");
        } catch (IOException e) {
            System.out.println("Error while reading db details from ./resources/db_connection_details.csv");
            e.printStackTrace();
        }
        return fields;
    }

    public Connection makeDBConnection() throws SQLException {

        String[] dbFields = getDBDetails();
        String url = "jdbc:postgresql://localhost:5432/" + dbFields[0];
        String user = dbFields[1];
        String password = dbFields[2];
        try {
            Connection con = DriverManager.getConnection(url
                    , user
                    , password);

            this.connection = con;
            return this.connection;
        } catch (Exception e) {
            System.out.println("Error while connecting to Database");
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(Connection con) throws SQLException {
        con.close();
    }

}
