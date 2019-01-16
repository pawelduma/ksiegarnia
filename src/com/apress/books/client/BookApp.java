package com.apress.books.client;

import java.util.List;

import com.apress.books.dao.BookDAO;
import com.apress.books.dao.BookDAOImpl;
import com.apress.books.model.Book;

public class BookApp {
    private static BookDAO bookDAO = new BookDAOImpl();

    public static void main(String[] args) {
        //Wyświetlanie wszystkich książek
        System.err.println("Lista wszystkich książek:");
        findAllBooks();
        //Wyszukiwanie książek za pomocą słów kluczowych
        System.err.println("Wyszukiwanie książęk ze słowem klucozwym 'Groovy' w tytule:");

        searchBooks("Groovy");
        System.out.println();

        System.err.println("Wyszukiwanie książęk ze słowem klucozwym 'Josh' dla imienia autora:");

        searchBooks("Josh");


    }

    private static void findAllBooks() {
        List<Book> books = bookDAO.findAllBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void searchBooks(String keyWord) {
        List<Book> books = bookDAO.searchBooksByKeyword(keyWord);
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
