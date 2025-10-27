package app.persistence.dao;

import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToppingDAO {

        public Topping create(Topping topping) throws DatabaseException {
            String sql = "INSERT INTO topping (navn, pris, aktiv) VALUES (?, ?, ?) RETURNING top_id, navn, pris, aktiv";
            try (Connection connection = ConnectionPool.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, topping.getNavn());
                preparedStatement.setDouble(2, topping.getPris());
                preparedStatement.setBoolean(3, topping.isAktiv());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapRow(resultSet);
                    } else {
                        throw new DatabaseException("Kunne ikke tilføje topping.");
                    }
                    }
                } catch (SQLException e) {
                    throw new DatabaseException("Kunne ikke tilføje topping. " + e.getMessage());
                }
            }

        public List <Topping> findAll() throws DatabaseException {
            String sql = "SELECT top_id, navn, pris, aktiv FROM topping ORDER BY top_id ASC";
            List <Topping> result = new ArrayList<>();
            try (Connection connection = ConnectionPool.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    result.add(mapRow(resultSet));
                }
                    return result;
            } catch (SQLException e) {
                throw new DatabaseException("Kunne ikke hente toppings. " + e.getMessage());
            }
        }
            public boolean update (Topping topping) throws DatabaseException {
                String sql = "UPDATE topping SET navn = ?, pris = ?, aktiv = ? WHERE top_id = ?";
                try (Connection connection = ConnectionPool.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    preparedStatement.setString(1, topping.getNavn());
                    preparedStatement.setDouble(2, topping.getPris());
                    preparedStatement.setBoolean(3, topping.isAktiv());
                    preparedStatement.setInt(4, topping.getTopId());

                    int rows = preparedStatement.executeUpdate();
                    return rows == 1;
                } catch (SQLException e) {
                    throw new DatabaseException("Kunne ikke opdatere topping med id: " + topping.getTopId() + ". " + e.getMessage());
                }
            }
    public boolean deleteById (int topId) throws DatabaseException {
                String sql = "DELETE FROM topping WHERE top_id = ?";
                try (Connection connection = ConnectionPool.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    preparedStatement.setInt(1, topId);
                    int rows = preparedStatement.executeUpdate();
                    return rows == 1;
                } catch (SQLException e) {
                    throw new DatabaseException("Kunne ikke slette topping med id: " + topId + ". " + e.getMessage());
                }
        }
            private Topping mapRow (ResultSet resultSet) {
                try {
                    int topId = resultSet.getInt("top_id");
                    String navn = resultSet.getString ("navn");
                    double pris = resultSet.getDouble ("pris");
                    boolean aktiv = resultSet.getBoolean ("aktiv");

                    return new Topping(topId, navn, pris, aktiv);
                } catch (SQLException e) {
                    throw new RuntimeException("Kunne ikke mappe kolonner til topping. " + e.getMessage());
                }
            }
    }

