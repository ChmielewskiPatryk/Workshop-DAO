import org.apache.commons.lang3.ArrayUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    Connection connection = DBUtil.connect("workshop2");

    public UserDAO() throws SQLException {
    }


    public User create(User user) throws SQLException {
        String query = "INSERT INTO users(email, username, pssword) VALUE (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
            statement.executeUpdate();
            System.out.println("User create complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }

    public User read(int userID) throws SQLException {

        String query = "SELECT * FROM users WHERE id =" + userID;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(userID);
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("pssword"));
                return user;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE users SET email =?,username =?, pssword =? WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            if (user.getPassword().equals(read(user.getId()).getPassword()))
            {
                statement.setString(3, user.getPassword());
            }
            else{
                statement.setString(3,BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
            }
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(4, String.valueOf(user.getId()));
            statement.executeUpdate();
            System.out.println("Update complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void delete(int userID) {

        String query = "DELETE FROM  users WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.executeUpdate();
            System.out.println("Delete complete");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public User[] findAll() {
        String query = "SELECT * FROM users";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            User[] tableOfUsers = new User[]{};

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("pssword"));
                tableOfUsers = ArrayUtils.add(tableOfUsers, user);
            }
            return tableOfUsers;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}


