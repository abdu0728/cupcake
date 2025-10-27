package app.persistence.dao;

import app.entities.Kurv;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KurvDAO {
    private final ConnectionPool connectionPool;

    public KurvDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    public Kurv create(Kurv kurv) throws DatabaseException {
        String sql = "INSERT INTO kurv (bruger_id, bund_id, top_id, antal, tilfojet) VALUES (?, ?, ?, ?, NOW()) RETURNING kurv_id, tilfojet";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, kurv.getBrugerId());
            preparedStatement.setInt(2, kurv.getBundId());
            preparedStatement.setInt(3, kurv.getTopId());
            preparedStatement.setInt(4, kurv.getAntal());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("kurv_id");
                    String tilfojet = resultSet.getString("tilfojet");
                            return new Kurv(generatedId, kurv.getBrugerId(), kurv.getBundId(), kurv.getTopId(), kurv.getAntal(), tilfojet);
                } else {
                    throw new DatabaseException("Kunne ikke oprette kurv.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette kurv. " + e.getMessage());
                }
            }
public Kurv findById(int kurvId) throws DatabaseException {
        String sql = "SELECT kurv_id, bruger_id, bund_id, top_id, antal, tilfojet FROM kurv WHERE kurv_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, kurvId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke finde kurv med id. " + kurvId, e.getMessage());
        }
}
public List<Kurv> findByBrugerId(int brugerId) throws DatabaseException {
    String sql = "SELECT kurv_id, bruger_id, bund_id, top_id, antal, tilfojet FROM kurv WHERE bruger_id = ? ORDER BY tilfojet ASC";
    List<Kurv> result = new ArrayList<>();
    try (Connection connection = connectionPool.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, brugerId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        }
        return result;
    } catch (SQLException e) {
        throw new DatabaseException("Kunne ikke finde kurv med brugerId: " + brugerId + ". " + e.getMessage());
            }
        }
public boolean updateAntal (int kurvId, int antal) throws DatabaseException {
        String sql = "UPDATE kurv SET antal = ? WHERE kurv_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, antal);
            preparedStatement.setInt(2, kurvId);
            int rows = preparedStatement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke opdatere antal i kurv med id " + kurvId + ". " + e.getMessage());
        }
    }

    public boolean deleteById(int kurvId) throws DatabaseException {
        String sql = "DELETE FROM kurv WHERE kurv_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, kurvId);
            int rows = preparedStatement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke slette kurv med id: " + kurvId + ". " + e.getMessage());
        }
    }

    public int clearForBruger (int brugerId) throws DatabaseException {
        String sql = "DELETE FROM kurv WHERE bruger_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, brugerId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke slette kurv fra bruger. " + brugerId + ". " + e.getMessage());
        }
    }
private Kurv mapRow(ResultSet resultSet) {
        try {
            int kurvId = resultSet.getInt("kurv_id");
            int brugerId = resultSet.getInt("bruger_id");
            int bundId = resultSet.getInt("bund_id");
            int topId = resultSet.getInt("top_id");
            int antal = resultSet.getInt("antal");
            String tilfojet = resultSet.getString("tilfojet");
            return new Kurv(kurvId, brugerId, bundId, topId, antal, tilfojet);
        } catch (SQLException e) {
            throw new RuntimeException("Kunne ikke mappe kolonner til kurv. " + e.getMessage());
        }
    }
}
