package max.lab7.server.sql;

import max.lab7.server.sql.tables.CardsTable;
import max.lab7.server.sql.tables.UserTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCWorker {

//    private final String DB_URL = "jdbc:postgresql://pg:5432/studs";
//    private final String USER = "...";
//    private final String PASS = "*****";
    private final String DB_URL = "jdbc:postgresql://localhost:5432/lab7";
    private final String USER = "...";
    private final String PASS = "*****";
    private static JDBCWorker instance;

    public static JDBCWorker instance(){
        if (instance == null){
            instance = new JDBCWorker();
        }
        return instance;
    }

    private Connection connection;
    private CardsTable cards ;
    private UserTable users;
    private JDBCWorker() {
        try {
            Class.forName("org.postgresql.Driver");
            Properties prop = new Properties();
            prop.put("user", USER);
            prop.put("password", PASS);
            prop.put("stringtype", "unspecified");

            connection = DriverManager.getConnection(DB_URL, prop);
            this.cards = new CardsTable(connection);
            this.users = new UserTable(connection);
            System.out.println("The database connection was successful");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public CardsTable getCards(){
        return cards;
    }

    public UserTable getUsers(){
        return users;
    }




}
