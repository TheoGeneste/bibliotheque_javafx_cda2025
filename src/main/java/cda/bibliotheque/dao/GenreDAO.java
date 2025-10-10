package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cda.bibliotheque.model.Genre;

public class GenreDAO {

    private Connection connection;

    public GenreDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Genre> getAllGenre() {
        String sql = "SELECT id, label FROM gender";
        List<Genre> genres = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rSet = stmt.executeQuery(sql)) {
            while (rSet.next()) {
                genres.add(new Genre(
                        rSet.getInt("id"),
                        rSet.getString("label")
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return genres;
    }

    public void addGenre(Genre genre) {
        String sql = "INSERT INTO gender(label) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, genre.getLabel());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateGenre(Genre genre) {
        String sql = "UPDATE gender set label = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, genre.getLabel());
            preparedStatement.setInt(2, genre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteGenre(Genre genre) {
        String sql = "DELETE FROM gender WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Genre> getGenresByBook(int id){
        String sql = "SELECT id, label FROM gender INNER JOIN have on id = gender_id WHERE book_id = ?";
        List<Genre> genres = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                genres.add(new Genre(rs.getInt("id"),rs.getString("label")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return genres;
    }
}
