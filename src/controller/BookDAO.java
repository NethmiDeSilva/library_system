package controller;

import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class BookDAO {
    private Connection connection;

    public BookDAO() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new SQLException("Failed to initialize database connection.", e);
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setYearPublished(rs.getInt("year_published"));
            books.add(book);
        }

        return books;
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, year_published) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, book.getTitle());
        pstmt.setString(2, book.getAuthor());
        pstmt.setInt(3, book.getYearPublished());
        pstmt.executeUpdate();
    }

    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ?, year_published = ? WHERE book_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, book.getTitle());
        pstmt.setString(2, book.getAuthor());
        pstmt.setInt(3, book.getYearPublished());
        pstmt.setInt(4, book.getBookId());
        pstmt.executeUpdate();
    }

    public void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM books WHERE book_id= ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, bookId);
        pstmt.executeUpdate();
    }
}
