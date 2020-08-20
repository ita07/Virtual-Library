package sample.com.ita07.virtuallibrary.database;

import sample.com.ita07.virtuallibrary.model.LiteraryBook;
import sample.com.ita07.virtuallibrary.model.ScientificBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomQueryBuilder {

    private final String checkISBNQuery = "SELECT ISBN FROM library WHERE ISBN = ?";

    public CustomQueryBuilder(ScientificBook scientificBook) throws SQLException {
        String insertScientificSqlQuery = "INSERT INTO library VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement checkISBNStatement = connection.prepareStatement(checkISBNQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertScientificSqlQuery)) {

            checkISBNStatement.setLong(1, scientificBook.getIsbn());
            ResultSet resultSet = checkISBNStatement.executeQuery();

            if (resultSet.next()) {
                throw new SQLException("This ISBN number already exists. Try again!");
            }
            insertStatement.setString(1, scientificBook.getBookType());
            insertStatement.setString(2, scientificBook.getTitle());
            insertStatement.setString(3, scientificBook.getAuthor());
            insertStatement.setLong(4, scientificBook.getIsbn());
            insertStatement.setInt(5, scientificBook.getReleaseYear());
            insertStatement.setString(6, scientificBook.getSubGenre());
            insertStatement.setString(7, scientificBook.getScientificField());
            insertStatement.executeUpdate();
        }
    }

    public CustomQueryBuilder(LiteraryBook literaryBook) throws SQLException {
        String insertLiterarySqlQuery = "INSERT INTO library(Type, TITLE, AUTHOR, ISBN, 'RELEASE YEAR', CATEGORY) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement checkISBNStatement = connection.prepareStatement(checkISBNQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertLiterarySqlQuery)) {

            checkISBNStatement.setLong(1, literaryBook.getIsbn());
            ResultSet resultSet = checkISBNStatement.executeQuery();

            if (resultSet.next()) {
                throw new SQLException("This ISBN number already exists. Try again!");
            }
            insertStatement.setString(1, literaryBook.getBookType());
            insertStatement.setString(2, literaryBook.getTitle());
            insertStatement.setString(3, literaryBook.getAuthor());
            insertStatement.setLong(4, literaryBook.getIsbn());
            insertStatement.setInt(5, literaryBook.getReleaseYear());
            insertStatement.setString(6, literaryBook.getSubGenre());
            insertStatement.executeUpdate();
        }
    }

}
