package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cda.bibliotheque.model.Author;
import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Editor;
import cda.bibliotheque.model.Genre;

public class BookDAO {

    private Connection connection;
    private final WriteDAO writeDAO = new WriteDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();
    private final GenreDAO genreDAO = new GenreDAO();
    private final HaveDAO haveDAO = new HaveDAO();
    private final DistributeDAO distributeDAO = new DistributeDAO();

    public BookDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, release_date, isAvailable FROM books;";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Author> authors = authorDAO.getAuthorsByBook(rs.getInt("id"));
                List<Genre> genres = genreDAO.getGenresByBook(rs.getInt("id"));
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDate("release_date").toLocalDate(),
                        rs.getBoolean("isAvailable"),
                        authors,
                        genres
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de la récupération dans getAllBook -> " + e.getMessage());
        }
        return books;
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books(title, release_date, isAvailable) VALUES (?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setDate(2, book.getReleaseDate_Date());
            pstmt.setBoolean(3, book.getIsAvailable());
            pstmt.executeUpdate();
            Book lastBook = getLastBookInserted();
            for (Author auth : book.getAuthors()) {
                writeDAO.insert(auth.getId(), lastBook.getId());
            }
            for (Genre genre : book.getGenres()) {
                haveDAO.insert(lastBook, genre);
            }

            for (Editor e : book.getEditors()) {
                distributeDAO.insert(lastBook, e);
            }
            System.out.println("Ajout du livre effectué");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout dans addBook -> " + e.getMessage());
        }
    }

    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, release_date = ?, isAvailable = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
             pstmt.setString(1, book.getTitle());
            pstmt.setDate(2, book.getReleaseDate_Date());
            pstmt.setBoolean(3, book.getIsAvailable());
            pstmt.setInt(4, book.getId());
            int rows = pstmt.executeUpdate();
            writeDAO.deleteByBook(book.getId());
            for(Author auth : book.getAuthors()){
                writeDAO.insert(auth.getId(), book.getId());
            }
            if (rows > 0) {
                System.out.println("Livre mis à jour");
            } else {
                System.out.println("Livre n'existe pas");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification dans updateBook -> " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Livre supprimé avec succés");
            } else {
                System.out.println("Livre n'existe pas");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression dans deleteBook -> " + e.getMessage());
        }
    }

    private  Book getLastBookInserted(){
        String SQL = "SELECT id, title, release_date, isAvailable FROM books ORDER BY id DESC LIMIT 1";
        Book book = new Book();
        try(Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(SQL)){
            while(resultSet.next()){
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsAvailable(resultSet.getBoolean("isAvailable"));
                book.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la recupération dans getLastBookInserted -> " + e.getMessage());
        }
        return book;
    }
}
