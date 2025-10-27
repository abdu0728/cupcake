package app.persistence.dao;

import app.entities.Ordrelinje;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdrelinjeDAO {
    private final ConnectionPool connectionPool;

    public OrdrelinjeDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Ordrelinje create(Ordrelinje ordrelinje) throws DatabaseException {
        String sql =
                "INSERT INTO ordrelinje (ordre_id, bund_id, top_id, antal, linje_pris) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING ordrelinje_id";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, ordrelinje.getOrdreId());
            preparedStatement.setInt(2, ordrelinje.getBundId());
            preparedStatement.setInt(3, ordrelinje.getTopId());
            preparedStatement.setInt(4, ordrelinje.getAntal());
            preparedStatement.setDouble(5, ordrelinje.getLinjePris());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("ordrelinje_id");
                    return new
                            Ordrelinje(generatedId,
                            ordrelinje.getOrdreId(),
                            ordrelinje.getBundId(),
                            ordrelinje.getTopId(),
                            ordrelinje.getAntal(),
                            ordrelinje.getLinjePris());
                } else {
                    throw new DatabaseException("Kunne ikke oprette ordrelinje.");
                }
            }
        } catch (SQLException e) {
        throw new DatabaseException("Kunne ikke oprette ordrelinje. " + e.getMessage());
        }
    }

    public List<Ordrelinje> findByOrdreId(int ordreId) throws DatabaseException {
        String sql = "SELECT ordrelinje_id, ordre_id, bund_id, top_id, antal, linje_pris " +
                "FROM ordrelinje WHERE ordre_id = ? ORDER BY ordrelinje_id ASC";
        List<Ordrelinje> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, ordreId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(mapRow(resultSet));
                    }
                }
                return result;
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke finde ordrelinjer med ordreId: " + ordreId + ". " + e.getMessage());
        }
    }

    public boolean updateAntal(int ordrelinjeId, int antal) throws DatabaseException {
        String sql = "UPDATE ordrelinje SET antal = ? WHERE ordrelinje_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, antal);
            preparedStatement.setInt(2, ordrelinjeId);
            int rows = preparedStatement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke opdatere antal i ordrelinje med id: " + ordrelinjeId + ". " + e.getMessage());
        }
    }

    public boolean deleteById(int ordrelinjeId) throws DatabaseException {
        String sql = "DELETE FROM ordrelinje WHERE ordrelinje_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ordrelinjeId);
            int rows = preparedStatement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke slette ordrelinje med id: " + ordrelinjeId + ". " + e.getMessage());
        }
    }

    public int clearByOrdreId (int ordreId) throws DatabaseException {
        String sql = "DELETE FROM ordrelinje WHERE ordre_id =?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ordreId);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke slette ordrelinjer med ordreId: " + ordreId + ". " + e.getMessage());
        }
    }

    private Ordrelinje mapRow(ResultSet resultSet) {
        try {
            int ordrelinjeId = resultSet.getInt("ordrelinje_id");
            int ordreId = resultSet.getInt("ordre_id");
            int bundId = resultSet.getInt("bund_id");
            int topId = resultSet.getInt("top_id");
            int antal = resultSet.getInt("antal");
            double linjePris = resultSet.getDouble("linje_pris");

            return new Ordrelinje(ordrelinjeId, ordreId, bundId, topId, antal, linjePris);
        } catch (SQLException e) {
            throw new RuntimeException("Kunne ikke mappe kolonner til ordrelinje. " + e.getMessage());
        }
    }
}
