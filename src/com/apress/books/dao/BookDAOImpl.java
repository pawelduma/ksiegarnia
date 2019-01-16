package com.apress.books.dao;

import com.apress.books.model.Author;
import com.apress.books.model.Book;
import com.apress.books.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookDAOImpl implements BookDAO {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/książki", "root", "MySQLpass");
    }

    private void closeConnection(Connection connection) {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }

    public List<Book> findAllBooks() {
        List<Book> result = new ArrayList<>();
        List<Author> authorList = new ArrayList<>();

        String sql = "select * from książka inner join autor on książka.id = autor.id_książki";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                Author author = new Author();
                book.setId(resultSet.getLong("id"));
                book.setBookTitle(resultSet.getString("tytuł książki"));
                book.setCategoryId(resultSet.getLong("id_kategorii"));
                author.setBookId(resultSet.getLong("id_książki"));
                author.setFirstName(resultSet.getString("imię"));
                author.setLastName(resultSet.getString("nazwisko"));
                authorList.add(author);
                book.setAuthors(authorList);
                book.setPublisherName(resultSet.getString("wydawca"));
                result.add(book);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return  result;
    }

    public List<Book> searchBooksByKeyword(String keyWord) {
        List<Book> result = new ArrayList<>();
        List<Author> authorList = new ArrayList<>();

        String sql = "select * from książka inner join autor on książka.id = autor.id_książki"
                + "where tytuł książki like '%"
                + keyWord.trim()
                + "%'"
                + "or imię like '%"
                + keyWord.trim()
                + "%'"
                + " or nazwisko like '%" + keyWord.trim() + "%'";

        Connection connection = null;
        try {

            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                Author author = new Author();
                book.setId(resultSet.getLong("id"));
                book.setBookTitle(resultSet.getString("tytuł_książki"));
                book.setPublisherName(resultSet.getString("wydawca"));
                author.setFirstName(resultSet.getString("imię"));
                author.setLastName(resultSet.getString("nazwisko"));
                author.setBookId(resultSet.getLong("id_książki"));
                authorList.add(author);
                book.setAuthors(authorList);
                result.add(book);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return result;
    }

    public List<Category> findAllCategories() {
        List<Category> result = new ArrayList<>();
        String sql = "select * from category";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setCategoryDescription(resultSet.getString("opis_kategorii"));
                result.add(category);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    public void insert(Book book) {}

    public void update(Book book) {}

    public void delete(Long bookId) {}
}
