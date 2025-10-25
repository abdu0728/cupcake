package app.persistence;


import app.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT bruger_id, email, kodeord, saldo, rolle,oprettet FROM bruger ORDER BY bruger_id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User u = new User(
                        rs.getInt("bruger_id"),
                        rs.getString("email"),
                        rs.getString("kodeord"),
                        rs.getString("rolle"),
                        rs.getDouble("saldo"),
                        rs.getBoolean("oprettet")
                );
                users.add(u);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findByEmail(String email) {
        String sql = "SELECT bruger_id, email, kodeord, saldo, rolle,oprettet FROM bruger WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("kodeord"),
                        rs.getString("rolle"),
                        rs.getDouble("saldo"),
                        rs.getBoolean("oprettet")
                );
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create(User user) {
        String sql = "INSERT INTO users (bruger_id,email,kodeord,rolle,saldo,oprettet) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,user.getBruger_id());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getKodeord());
            stmt.setDouble(4, user.getSaldo());
            stmt.setString(5, user.getRolle());
            stmt.setBoolean(6,user.isOprettet());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSaldo(int bruger_id, double nySaldo) {
        String sql = "UPDATE bruger SET saldo = ? WHERE bruger_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, nySaldo);
            stmt.setInt(2, bruger_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

