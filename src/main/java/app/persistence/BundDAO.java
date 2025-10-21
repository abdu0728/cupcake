package app.persistence;

import app.entities.Bund;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class BundDAO {
        private Connection connection;

        public BundDAO(Connection connection) {
            this.connection = connection;
        }

        public List<Bund> findAll() {
            List<Bund> result = new ArrayList<>();
            String sql = "SELECT bund_id, navn, pris, aktiv FROM bund ORDER BY pris, navn";

            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Bund bund = new Bund(
                            rs.getInt("bund_id"),
                            rs.getString("navn"),
                            rs.getDouble("pris")
                    );
                    result.add(bund);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result;
        }
    }


