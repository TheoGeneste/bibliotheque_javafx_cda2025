package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cda.bibliotheque.model.Author;

public class AuthorDAO {

    private Connection connection;

    public AuthorDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT id, lastname, firstname, born_at FROM author;";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("id"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getDate("born_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de la récupération dans getAllAuthors -> " + e.getMessage());
        }
        return authors;
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO author(lastname, firstname, born_at) VALUES (?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, author.getLastname());
            pstmt.setString(2, author.getFirstname());
            pstmt.setDate(3, author.getBorn_at_Date());
            pstmt.executeUpdate();
            System.out.println("Ajout de l'auteur effectué");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout dans addAuthor -> " + e.getMessage());
        }
    }

    public void updateAuthor(Author author) {
        String sql = "UPDATE author SET lastname = ?, firstname = ?, born_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, author.getLastname());
            pstmt.setString(2, author.getFirstname());
            pstmt.setDate(3, author.getBorn_at_Date());
            pstmt.setInt(4, author.getId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Auteur mis à jour");
            } else {
                System.out.println("Auteur n'existe pas");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification dans updateAuthor -> " + e.getMessage());
        }
    }

    public void deleteAuthor(int id) {
        String sql = "DELETE FROM author WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Auteur supprimé avec succés");
            } else {
                System.out.println("Auteur n'existe pas");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression dans deleteAuthor -> " + e.getMessage());
        }
    }
}
