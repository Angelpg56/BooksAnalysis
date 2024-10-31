package edu.psp.booksanalysis;

import edu.psp.booksanalysis.model.Book;
import edu.psp.booksanalysis.utils.FileUtils;
import edu.psp.booksanalysis.utils.MessageUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class for the main application window.
 */
public class MainController {
    /**
     * TableView for the books.
     */
    public TableView tv_Libros;

    /**
     * TableColumn of the title of each book.
     */
    public TableColumn tc_Title;

    /**
     * TableColumn of the author of each book.
     */
    public TableColumn tc_Author;

    /**
     * TableColumn of the genre of each book.
     */
    public TableColumn tc_Genre;

    /**
     * TableColumn of the publication year of each book.
     */
    public TableColumn tc_Year;

    /**
     * ChoiceBox for filter options.
     */
    public ChoiceBox cb_Filter;

    /**
     * TextField for the book title.
     */
    public TextField txt_Title;

    /**
     * TextField for the book author.
     */
    public TextField txt_Author;

    /**
     * TextField for the book genre.
     */
    public TextField txt_Genre;

    /**
     * TextField for the book publication year.
     */
    public TextField txt_Year;

    /**
     * Button to add a book.
     */
    public Button btn_Add;

    /**
     * Button to update a book.
     */
    public Button btn_Update;

    /**
     * Button to delete a book.
     */
    public Button btn_Delete;

    /**
     * TextField for searching books.
     */
    public TextField txt_Search;

    /**
     * ObservableList of books.
     */
    private ObservableList<Book> books;

    /**
     * Label for the top text.
     */
    public Label lbl_TopText;

    /**
     * Label for the book title.
     */
    public Label lbl_Title;

    /**
     * Label for the book author.
     */
    public Label lbl_Author;

    /**
     * Label for the book genre.
     */
    public Label lbl_Genre;

    /**
     * Label for the book publication year.
     */
    public Label lbl_Year;

    /**
     * Label for the search field.
     */
    public Label lbl_Search;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     *
     * @throws NoSuchFieldException if a field is not found
     * @throws IllegalAccessException if a field is not accessible
     */
    @FXML
    public void initialize() throws NoSuchFieldException, IllegalAccessException {
        tc_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tc_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        tc_Genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tc_Year.setCellValueFactory(new PropertyValueFactory<>("published"));

        books = FXCollections.observableArrayList(FileUtils.loadFromFile());
        cb_Filter.getItems().addAll(
                "Without filter", "Total number of books", "The same genre than...",
                "Authors sorted alphabetically", "Average publication year");
        cb_Filter.setOnAction(event -> handleComboBoxSelection());
        cb_Filter.getSelectionModel().selectFirst();

        refreshTable(books);
    }

    /**
     * Handles the window close request event.
     *
     * @param event the window event
     */
    public void onCloseRequest(WindowEvent event) {
        FileUtils.saveToFile(books);
    }

    /**
     * Refreshes the table with the given list of books.
     *
     * @param bookList the list of books to display
     */
    private void refreshTable(ObservableList<Book> bookList) {
        tv_Libros.setItems(bookList);
    }

    /**
     * Adds a new book to the list.
     *
     * @param event the action event
     */
    public void addBook(ActionEvent event) {
        try {
            if (revisionCampos() && books.stream().filter(b -> b.getTitle().equals(txt_Title.getText())).count() == 0) {
                books.add(new Book(
                        txt_Title.getText(),
                        txt_Author.getText(),
                        txt_Genre.getText(),
                        Integer.parseInt(txt_Year.getText())
                ));
                MessageUtils.showMessage(
                        "Book added", "The book " + txt_Title.getText() + " added correctly ");

                handleComboBoxSelection();
            } else {
                MessageUtils.showMessage(
                        "Book already exists", "The book you are trying to add already exists");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(); // This will give the actual exception stack trace
        }
    }

    /**
     * Checks if all required fields are filled and valid.
     *
     * @return true if all fields are valid, false otherwise
     */
    public Boolean revisionCampos() {
        try {
            if (!txt_Author.getText().isEmpty() &&
                    !txt_Genre.getText().isEmpty() &&
                    !txt_Title.getText().isEmpty() &&
                    !txt_Year.getText().isEmpty()) {

                Integer.parseInt(txt_Year.getText());

                return true;
            } else {
                MessageUtils.showMessage(
                        "Missing data", "All camps must be completed to add a new book");
            }
        } catch (NumberFormatException e){
            MessageUtils.showError(
                    "NumberFormatException", "The published year must be a number");
        }

        return false;
    }

    /**
     * Selects a book from the table and displays its details in the text fields.
     *
     * @param mouseEvent the mouse event
     */
    public void selectBook(MouseEvent mouseEvent) {
        Book selectedBook = (Book) tv_Libros.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            txt_Title.setText(selectedBook.getTitle());
            txt_Author.setText(selectedBook.getAuthor());
            txt_Genre.setText(selectedBook.getGenre());
            txt_Year.setText(String.valueOf(selectedBook.getPublished()));
        }
    }

    /**
     * Updates the selected book with the details from the text fields.
     *
     * @param event the action event
     */
    public void updateBook(ActionEvent event) {
        Book selectedBook = (Book) tv_Libros.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            if(revisionCampos()) {
                if(MessageUtils.showConfirmation(
                        "Update book", "Are you sure you want to update the book?")) {
                    books.remove(selectedBook);
                    selectedBook.setTitle(txt_Title.getText());
                    selectedBook.setAuthor(txt_Author.getText());
                    selectedBook.setGenre(txt_Genre.getText());
                    selectedBook.setPublished(Integer.parseInt(txt_Year.getText()));

                    books.add(selectedBook);
                    MessageUtils.showMessage(
                            "Book updated", "The book " + selectedBook.getTitle() + " has been updated");
                    handleComboBoxSelection();
                    tv_Libros.getSelectionModel().clearSelection();
                }
            }
        } else {
            MessageUtils.showMessage(
                    "No book selected", "You must select a book to update");
        }
    }

    /**
     * Deletes the selected book from the list.
     *
     * @param event the action event
     */
    public void deleteBook(ActionEvent event) {
        Book selectedBook = (Book) tv_Libros.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            if (MessageUtils.showConfirmation(
                    "Delete book", "Are you sure you want to delete the book?")) {
                books.remove(selectedBook);
                MessageUtils.showMessage(
                        "Book deleted", "The book " + selectedBook.getTitle() + " has been deleted");
                tv_Libros.getSelectionModel().clearSelection();
                handleComboBoxSelection();
            }
        } else {
            MessageUtils.showMessage(
                    "No book selected", "You must select a book to delete");
        }
    }

    /**
     * Handles the selection of a filter option from the combo box.
     */
    private void handleComboBoxSelection() {
        String filterOption = cb_Filter.getSelectionModel().getSelectedItem().toString();
        switch (filterOption) {
            case "Without filter":
                refreshTable(books);
                break;
            case "Total number of books":
                MessageUtils.showMessage(
                        "Total number of books", "The total number of books is: " + books.size());
                break;
            case "The same genre than...":
                if(tv_Libros.getSelectionModel().getSelectedItem() == null) {
                    MessageUtils.showMessage(
                            "No book selected", "You must select a book to filter by genre");
                } else {
                    refreshTable(
                            books.filtered(b ->
                                    b.getGenre().equals(
                                            ((Book) tv_Libros.getSelectionModel().getSelectedItem()).getGenre())));
                }
                break;
            case "Authors sorted alphabetically":
                refreshTable(
                        books.sorted(Comparator.comparing(Book::getAuthor)));
                break;
            case "Average publication year":
                double average = books.stream().mapToInt(Book::getPublished).average().orElse(0);
                MessageUtils.showMessage(
                        "Average publication year", "The average publication year is: " + average);
                break;
        }
    }

    /**
     * Searches for books based on the text entered in the search field.
     *
     * @param keyEvent the key event
     */
    public void searchBook(KeyEvent keyEvent) {
        String search = txt_Search.getText().toLowerCase();
        refreshTable(
                ((ObservableList<Book>)tv_Libros.getItems()).filtered(b ->
                        b.getTitle().toLowerCase().contains(search) ||
                                b.getAuthor().toLowerCase().contains(search) ||
                                b.getGenre().toLowerCase().contains(search) ||
                                String.valueOf(b.getPublished()).contains(search)
                )
        );
    }
}