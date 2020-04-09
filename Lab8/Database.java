import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // static variable single_instance of type Singleton
    private static Database single_instance = null;

    Connection con = null;

    // private constructor restricted to this class itself
    private Database() throws SQLException, ClassNotFoundException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe" ;
        try {
            this.con = DriverManager.getConnection(
                    url, "student","STUDENT");
        } catch(SQLException e) {
            System.err.println("Cannot connect to DB: " + e);
        }
    }

    // static method to create instance of Singleton class
    public static Database getInstance() throws SQLException, ClassNotFoundException {
        if (single_instance == null)
            single_instance = new Database();

        return single_instance;
    }
}
