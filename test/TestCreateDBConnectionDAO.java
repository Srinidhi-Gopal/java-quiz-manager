import fr.epita.quiz.services.dao.CreateDBConnectionDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class TestCreateDBConnectionDAO {

    public static void main(String[] args) throws SQLException {
        CreateDBConnectionDAO connectionDAO = new CreateDBConnectionDAO();
        Connection con = connectionDAO.makeDBConnection();
        if(con != null) {
            System.out.println("Successfully connected to Database whose details is present in ./resources/db_connection_details.csv");
        } else {
            System.out.println("Error while connecting to Database whose details is present in ./resources/db_connection_details.csv");
        }
    }

}
