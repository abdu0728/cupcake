package app.tools;

import app.persistence.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {
    public static void main(String[] args) {
        System.out.println("Databaseforbindelse test");

        String sql = "SELECT COUNT (*) FROM bund";

        try (Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    int antalBund = resultSet.getInt(1);
                    System.out.println("Virker! Antal rækker i bund: " + antalBund);
                }
            } catch (SQLException e){
                    System.out.println("Fejl ved henting af data fra databasen");
                    e.printStackTrace();
                }
            }
        }