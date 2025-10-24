package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/cupcake_db";
        String user = "cupcakeuser";
        String password = "cupcake123";

        System.out.printf("Connecting to database");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        System.out.println("Cupcake projektet virker");
    }
}