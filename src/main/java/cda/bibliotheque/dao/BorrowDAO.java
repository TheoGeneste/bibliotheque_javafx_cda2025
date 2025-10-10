package cda.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Borrow;
import cda.bibliotheque.model.Client;

public class BorrowDAO {

    private Connection connection;

    public BorrowDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Borrow> getAllBorrow() {
        String sql = "SELECT book_id, client_id, end_date, start_date, ";
        sql = sql + " isDone, title, release_date, isAvailable, firstname, lastname, email FROM borrow ";
        sql = sql + " INNER JOIN books ON books.id = book_id ";
        sql = sql + " INNER JOIN client ON client.id = client_id";
        List<Borrow> borrows = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet rSet = statement.executeQuery(sql)) {
            while (rSet.next()) {
                Client client = new Client(
                        rSet.getInt("client_id"),
                        rSet.getString("firstname"),
                        rSet.getString("lastname"),
                        rSet.getString("email")
                );
                Book book = new Book(
                        rSet.getInt("book_id"),
                        rSet.getString("title"),
                        rSet.getDate("release_date").toLocalDate(),
                        rSet.getBoolean("isAvailable")
                );
                borrows.add(new Borrow(
                        client,
                        book,
                        rSet.getDate("end_date").toLocalDate(),
                        rSet.getDate("start_date").toLocalDate(),
                        rSet.getBoolean("isDone")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return borrows;
    }

    public void addBorrow(Borrow borrow) {
        String sql = "INSERT INTO borrow(book_id, client_id,start_date,end_date,isDone) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrow.getBook().getId());
            preparedStatement.setInt(2, borrow.getClient().getId());
            preparedStatement.setDate(3, borrow.getStartDate_DATE());
            preparedStatement.setDate(4, borrow.getEndDate_DATE());
            preparedStatement.setBoolean(5, borrow.getIsDone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateBorrow(Borrow borrow) {
        String sql = "UPDATE borrow set start_date = ?, end_date = ? ,isDone = ? WHERE book_id = ? AND client_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, borrow.getStartDate_DATE());
            preparedStatement.setDate(2, borrow.getEndDate_DATE());
            preparedStatement.setBoolean(3, borrow.getIsDone());
            preparedStatement.setInt(4, borrow.getBook().getId());
            preparedStatement.setInt(5, borrow.getClient().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteBorrow(Borrow borrow) {
        String sql = "DELETE FROM borrow  WHERE book_id = ? AND client_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrow.getBook().getId());
            preparedStatement.setInt(2, borrow.getClient().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
