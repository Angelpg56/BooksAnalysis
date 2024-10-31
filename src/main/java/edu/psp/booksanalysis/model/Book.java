package edu.psp.booksanalysis.model;

/**
 * Represents a book with a title, author, genre, and publication year.
 */
public class Book {
    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The genre of the book.
     */
    private String genre;

    /**
     * The year the book was published.
     */
    private int published;

    /**
     * Constructs a new Book with the specified title, author, genre, and publication year.
     *
     * @param title     the title of the book
     * @param author    the author of the book
     * @param genre     the genre of the book
     * @param published the year the book was published
     */
    public Book(String title, String author, String genre, Integer published) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        if (published == null) {
            this.published = 0;
        } else {
            this.published = published;
        }
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the genre of the book.
     *
     * @return the genre of the book
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     *
     * @param genre the new genre of the book
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns the year the book was published.
     *
     * @return the year the book was published
     */
    public int getPublished() {
        return published;
    }

    /**
     * Sets the year the book was published.
     *
     * @param published the new publication year of the book
     */
    public void setPublished(int published) {
        this.published = published;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return a string representation of the book
     */
    @Override
    public String toString() {
        return "book {" +
                " title = '" + title + "'" +
                ", author = '" + author + "'" +
                ", genre = '" + genre + "'" +
                ", published = " + published +
                '}';
    }
}