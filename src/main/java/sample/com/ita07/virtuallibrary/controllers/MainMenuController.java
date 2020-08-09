package sample.com.ita07.virtuallibrary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sample.com.ita07.virtuallibrary.customexceptions.EmptyValueException;
import sample.com.ita07.virtuallibrary.helpers.SceneExchange;
import sample.com.ita07.virtuallibrary.model.Book;
import sample.com.ita07.virtuallibrary.model.LiteraryBook;
import sample.com.ita07.virtuallibrary.model.ScientificBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for Main Menu scene
 */
public class MainMenuController {
    @FXML  // fx:id of each button
    private Button exitButton, insertBookButton, searchButton, showBooks;

    @FXML
    private VBox mainPane; // fx:id of root Pane

    private List<String> currentBook = new ArrayList<>(); // list that adds every book entry read from the database into the books list
    static List<String> bookISBN = new ArrayList<>(); // list that holds all ISBN numbers of books -- used in Insertion Menu
    static List<Book> books = new ArrayList<>(); // list that holds all book entries as objects from database
    static List<String> bookTitles = new ArrayList<>(); // list that holds all ISBN numbers of books -- used in Search Menu
    static List<String> bookAuthors = new ArrayList<>(); // list that holds all ISBN numbers of books -- used in Search Menu

    @FXML
    public void initialize() {

        try {
            //Clear the lists every time Main Menu is accessed
            bookTitles.clear();
            bookAuthors.clear();
            bookISBN.clear();
            //Read database file every time Main Menu is accessed
            parseDatabase();

        } catch (IOException | EmptyValueException e) { // catch clause for error while parsing the file.
            Alert alert = new Alert(Alert.AlertType.ERROR); // Popup error alert
            alert.initOwner(mainPane.getScene().getWindow()); // assign icon in the popup alert
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage()); // Set context text as the message thrown by the exception
            alert.showAndWait();
        }


    }

    /**
     * Read and parse the database text file
     *
     * @throws IOException         On error reading the file
     * @throws EmptyValueException On format error of the text file
     */
    private void parseDatabase() throws IOException, EmptyValueException {
        //Clear books list every time so no old values are there
        books.clear();
        //Export every text file line as a String into a List
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"));
        //Return if text file is empty
        if (lines.isEmpty()) {
            return;
        }
        // Read every entry in the list
        for (String line : lines) {
            // if entry isn't empty , add the entry into an Arraylist which holds the book that's being parsed
            if (!line.isEmpty()) {
                currentBook.add(line);
            }
            //if entry is empty it means we finished parsing a book entry
            if (line.isEmpty()) {
                //Add the book entry through the getBook() method into the list book which consists every book as an object entry
                books.add(getBook(currentBook));
                currentBook = new ArrayList<>();
            }

        }

    }

    /**
     * Gets a book object
     *
     * @param bookEntry List that holds all the information of a Book
     * @return Depending on bookType, it returns the corresponding book object
     */
    private Book getBook(List<String> bookEntry) throws EmptyValueException {
        bookTitles.add(bookEntry.get(1)); // saves book's Title into the bookTitles list
        bookAuthors.add(bookEntry.get(2)); // saves book's Author into the bookAuthor list
        bookISBN.add(bookEntry.get(3)); // saves book's ISBN into the bookISBN list
        String bookType = bookEntry.get(0); // store Type of Book value
        switch (bookType) {
            case "ΛΟΓΟΤΕΧΝΙΚΟ":
                return new LiteraryBook(bookEntry.get(0), bookEntry.get(1), bookEntry.get(2), Long.parseLong(bookEntry.get(3)), Integer.parseInt(bookEntry.get(4)), bookEntry.get(5));
            case "ΕΠΙΣΤΗΜΟΝΙΚΟ":
                return new ScientificBook(bookEntry.get(0), bookEntry.get(1), bookEntry.get(2), Long.parseLong(bookEntry.get(3)), Integer.parseInt(bookEntry.get(4)), bookEntry.get(5), bookEntry.get(6));
            default:
                throw new EmptyValueException("Σφαλμα κατα την προσπελαση του αρχειου!");
        }
    }

    //OnActionEvent Generated Method -- When Show Book Library Button is pressed
    @FXML
    void handleShowButtonActionPerformed(ActionEvent event) throws IOException {
        //Swap scenes
        new SceneExchange().changeScene("/fxml/displayTableMenu.fxml", true, "Λιστα Βιβλιων");
    }

    //OnActionEvent Generated Method -- When Search Button is pressed
    @FXML
    void handleSearchButtonActionPerformed(ActionEvent event) throws IOException {
        //Swap scenes
        new SceneExchange().changeScene("/fxml/searchMenu.fxml", true, "Ευρεση Βιβλιου");
    }

    //OnActionEvent Generated Method -- When Insert Button is pressed
    @FXML
    void handleInsertBookButtonActionPerformed(ActionEvent event) throws IOException {
        //Swap scenes
        new SceneExchange().changeScene("/fxml/insertionMenu.fxml", false, "Εισαγωγη Βιβλιου");


    }

    //OnActionEvent Generated Method -- When Exit Button is pressed
    @FXML
    void handleExitButtonActionPerformed(ActionEvent event) {
        //Exit the program
        System.exit(0);
    }
}