package app;

import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.dao.ToppingDAO;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Forbinder til cupcake databasen.");

        try (Connection connection = ConnectionPool.getConnection()) {
            System.out.println("Forbundet via ConnectionPool.");

            ToppingDAO toppingDAO = new ToppingDAO();

            System.out.println("Test toppings: ");

            System.out.println("Opret toppings: ");
            Topping newTopping = new Topping(0, "TestTopping", 9.5, true);
            Topping created = toppingDAO.create(newTopping);
            System.out.println("Oprettet: " + created);

            System.out.println("Find alle toppings:");
            List<Topping> toppings = toppingDAO.findAll();
            for (Topping topping : toppings) {
                System.out.println(topping);
            }

            System.out.println("Opdater pris for topping: ");
            created.setPris(10.0);
            boolean updated = toppingDAO.update(created);
            System.out.println("Opdateret: " + updated);

            System.out.println("Slet topping:");
            boolean deleted = toppingDAO.deleteById(created.getTopId());
            System.out.println("Slettet: " + deleted);

        } catch (DatabaseException e) {
            System.out.println("Ikke forbundet. " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Fejl i forbindelse: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Cupcake virker.");
    }
}