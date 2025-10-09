package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Editor;

public class EditorDAO {

    private Connection connection;

    private final DistributeDAO distributeDAO = new DistributeDAO();

    public EditorDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Editor> getAllEditor() {
        String sql = "SELECT id, label, created_at FROM editor;";
        List<Editor> editors = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rSet = stmt.executeQuery(sql)) {
            while (rSet.next()) {
                List<Book> books = distributeDAO.getBookByEditor(rSet.getInt("id"));
                editors.add(new Editor(
                        rSet.getInt("id"),
                        rSet.getString("label"),
                        rSet.getDate("created_at"),
                        books
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return editors;
    }

    public void addEditor(Editor editor) {
        String sql = "INSERT INTO editor(label, created_at) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, editor.getLabel());
            preparedStatement.setDate(2, editor.getCreatedAt_DATE());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateEditor(Editor editor) {
        String sql = "UPDATE editor set label = ? , created_at = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, editor.getLabel());
            preparedStatement.setDate(2, editor.getCreatedAt_DATE());
            preparedStatement.setInt(3, editor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

    public void deleteEditor(Editor editor) {
        String sql = "DELETE FROM editor WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, editor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
