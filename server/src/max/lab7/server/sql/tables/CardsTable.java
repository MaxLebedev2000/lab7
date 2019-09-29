package max.lab7.server.sql.tables;


import max.lab5.humans.Card;
import max.lab7.server.sql.SQLUtils;
import org.json.JSONObject;

import java.sql.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CardsTable extends JDBCTable<Card> {

    private static final String NAME = "cards";

    public CardsTable(Connection connection) {
        super(connection);
    }

    @Override
    public boolean put(Card card) {

        if (this.exists(card)){
            return false;
        }

        try (PreparedStatement statement = connection().
                prepareStatement("INSERT INTO cards (date, name, eyes, hair, status, height, " +
                        "headsize, nosesize, cardheight, cardwidth, owner) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, card.getDate().toString());
            statement.setString(2, card.getName());
            statement.setString(3, card.getPhoto().getEyes().toString());
            statement.setString(4, card.getPhoto().getHair().name());
            statement.setString(5, card.getStatus().name());
            statement.setString(6, String.valueOf(card.getHeight()));
            statement.setString(7, String.valueOf(card.getHeadSize()));
            statement.setString(8, String.valueOf(card.getNoseSize()));
            statement.setString(9, String.valueOf(card.getCardHeight()));
            statement.setString(10, String.valueOf(card.getCardWidth()));
            statement.setString(11, card.getOwner());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Card card) {
        try (PreparedStatement statement = connection().
                prepareStatement("DELETE FROM cards WHERE " + condition(card))) {
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public Set<Card> set() {
        Set<Card> result = ConcurrentHashMap.newKeySet();
        try (PreparedStatement statement = connection().prepareStatement("SELECT * FROM " + NAME)) {
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                JSONObject photo = new JSONObject();
                for (int i=1; i<=numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    if (column_name.equals("eyes") || column_name.equals("hair")){
                        photo.put(column_name, rs.getObject(column_name));
                    }

                    obj.put(column_name, rs.getObject(column_name));
                }
                obj.put("photo", photo);
                result.add(new Card(obj));
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString() {
        try (PreparedStatement statement = connection().prepareStatement("SELECT * FROM " + NAME)) {
            return SQLUtils.getTableAsString(statement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public boolean exists(Card card) {
        try (PreparedStatement statement = connection().
                prepareStatement("SELECT FROM cards WHERE " + condition(card))) {
            ResultSet set = statement.executeQuery();
            return set.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addAll(Set<Card> set) {
        for (Card card: set){
            if (!this.exists(card)) {
                this.put(card);
            }
        }
        return true;
    }

    private static String condition(Card card){
        return "name = \'" + card.getName() + "\'" +
                " AND eyes = \'"+card.getPhoto().getEyes().name()+ "\'"+
                " AND hair = \'" +  card.getPhoto().getHair().name()+ "\'"+
                " AND status = \'"+card.getStatus().name()+ "\'"+
                " AND cardheight = \'"+card.getCardHeight()+ "\'"+
                " AND cardwidth = \'"+card.getCardWidth()+ "\'"+
                " AND height = \'"+card.getHeight()+ "\'"+
                " AND headsize = \'"+card.getHeadSize()+ "\'"+
                " AND nosesize = \'"+card.getNoseSize()+ "\'"+
                " AND owner = \'"+card.getOwner()+ "\'" + ";";
    }
}
