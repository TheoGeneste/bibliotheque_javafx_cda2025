package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cda.bibliotheque.model.Client;

public class ClientDAO {

    public Connection connection;

    public ClientDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Client> getAllCLient() {
        String sql = "SELECT id, firstname,lastname, email FROM client;";
        List<Client> clients = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rSet = stmt.executeQuery(sql)) {
            while (rSet.next()) {
                clients.add(new Client(
                        rSet.getInt("id"),
                        rSet.getString("firstname"),
                        rSet.getString("lastname"),
                        rSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return clients;
    }

    public void addClient(Client client) {
        String sql = "INSERT INTO client( firstname,lastname, email) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getFirstname());
            preparedStatement.setString(2, client.getLastname());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateClient(Client client) {
        String sql = "UDPATE client set firstname = ?,lastname = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getFirstname());
            preparedStatement.setString(2, client.getLastname());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setInt(4, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteClient(Client client) {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
