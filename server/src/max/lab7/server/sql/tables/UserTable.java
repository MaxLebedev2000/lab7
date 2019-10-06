package max.lab7.server.sql.tables;

import max.lab5.humans.Card;
import max.lab7.server.sql.SQLUtils;
import max.lab7.server.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class UserTable extends JDBCTable<User> {

    public UserTable(Connection connection){
        super(connection);
    }

    @Override
    public boolean put(User user) {
        try (PreparedStatement statement = connection().
                prepareStatement("INSERT INTO users (email, login, password) values (?, ?, ?)")){

            statement.setString(1, user.email);
            statement.setString(2, user.login);
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addAll(Set<User> set) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(User user) {
        try (PreparedStatement statement = connection().
                prepareStatement("DELETE FROM users WHERE login= \'" + user.login + "\';")){
            statement.executeUpdate();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Set<User> set() {
        return null;
    }

    @Override
    public boolean exists(User user) {
        try (PreparedStatement statement = connection().
                prepareStatement("SELECT * FROM users WHERE login = \'" + user.login + "\';")){

            ResultSet set = statement.executeQuery();
            return set.next();

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean passwordCorrect(String login, String password) throws SQLException{

        try (PreparedStatement statement = connection().
                prepareStatement("SELECT * FROM users WHERE login = \'" + login + "\';")){

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                String pass = set.getString("password");
                return pass.equals(password);
            } else {
                return false;
            }
        }

    }
}
