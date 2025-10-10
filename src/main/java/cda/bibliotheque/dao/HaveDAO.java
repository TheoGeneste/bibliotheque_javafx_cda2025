package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Genre;

public class HaveDAO {
    private Connection connection;

    public HaveDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void insert(Book book, Genre genre){
        String sql = "INSERT INTO have(book_id, gender_id) VALUES (?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,book.getId());
            preparedStatement.setInt(2, genre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("insert have ->" + e.getMessage());
        }
    }

    
}
