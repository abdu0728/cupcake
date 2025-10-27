package app.persistence.dao;

import app.entities.Bruger;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrugerDAO {
    private final Connection connection;

    public BrugerDAO(Connection connection) {
        this.connection = connection;
    }
    public List<Bruger> findAll() throws DatabaseException{
        List<Bruger> brugerListe = new ArrayList<>();
        String sql = "SELECT bruger_id, email, kodeord, rolle, saldo, oprettet FROM bruger ORDER BY bruger_id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Bruger bruger =  new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        resultSet.getString("rolle"),
                        resultSet.getDouble("saldo"),
                        resultSet.getBoolean("oprettet")
                );
                brugerListe.add(bruger);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kunne ikke hente bruger. ", e);
        }
        return brugerListe;
    }
    public Bruger findById(int brugerId) throws DatabaseException {
        String sql = "SELECT bruger_id, email, kodeord, rolle, saldo, oprettet FROM bruger WHERE bruger_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, brugerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        resultSet.getString("rolle"),
                        resultSet.getDouble("saldo"),
                        resultSet.getBoolean("oprettet")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke finde bruger med id. " + brugerId, e.getMessage());
        }
        return null;
        }
    }
