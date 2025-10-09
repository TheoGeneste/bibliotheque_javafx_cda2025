package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteDAO {
    private Connection connection;

    public WriteDAO(){
        connection = DatabaseConnection.getConnection();
    }

    public void insert(int authorId , int bookId){
        String sql = "INSERT INTO WRITES(book_id, author_id) VALUES (?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout dant insert -> " + e.getMessage());
        }
    }
    
}
