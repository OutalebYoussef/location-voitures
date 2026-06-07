package dao;

import database.DBConnection;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public String login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Session.setUserId(rs.getInt("id"));
                Session.setUsername(rs.getString("username"));
                Session.setRole(rs.getString("role"));

                return rs.getString("role");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}