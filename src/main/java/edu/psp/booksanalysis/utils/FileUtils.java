package edu.psp.booksanalysis.utils;

import edu.psp.booksanalysis.model.Book;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file operations related to books.
 */
public class FileUtils {

    /**
     * The file path where books are saved.
     */
    private static final String booksFile = "save/books.txt";

    /**
     * Converts a Book object to a string representation suitable for saving to a file.
     *
     * @param book the book to convert
     * @return a string representation of the book
     */
    private static String bookToSaveString(Book book) {
        return book.getAuthor() + ";" + book.getGenre() + ";" + book.getTitle() + ";" + book.getPublished();
    }

    /**
     * Creates a Book object from a string representation.
     *
     * @param line the string representation of the book
     * @return a Book object
     */
    private static Book bookFromString(String line) {
        String[] parts = line.split(";");

        try {
            if (parts.length != 4 || parts[2].equals("")) {
                return null;
            }
            if (parts[1].isEmpty())
                parts[1] = "Unknown";
            if(parts[0].isEmpty())
                parts[0] = "Unknown";

            return new Book(parts[2], parts[0], parts[1], Integer.parseInt(parts[3]));
        } catch (NumberFormatException e) {
            return new Book(parts[2], parts[0], parts[1], null);
        }
    }

    /**
     * Saves a list of books to a file.
     *
     * @param books the list of books to save
     */
    public static void saveToFile(ObservableList<Book> books) {
        try (PrintWriter printWriter = new PrintWriter(booksFile)) {
            books.forEach(book ->
                    printWriter.println(
                            bookToSaveString(book)
                    )
            );
        } catch (Exception e) {
        }
    }

    /**
     * Loads a list of books from a file.
     *
     * @return a list of books
     */
    public static List<Book> loadFromFile() {
        List<Book> books = null;
        try {
            books = java.nio.file.Files.lines(java.nio.file.Paths.get(booksFile))
                    .map(FileUtils::bookFromString)
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        if (books == null) {
            books = new ArrayList<>();
        }

        return books;
    }
}