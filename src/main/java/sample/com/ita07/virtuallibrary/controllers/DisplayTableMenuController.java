package sample.com.ita07.virtuallibrary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.com.ita07.virtuallibrary.helpers.SceneExchange;
import sample.com.ita07.virtuallibrary.model.Book;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Optional;

/**
 * Controller Class for Display Table Menu scene
 */
public class DisplayTableMenuController {

    @FXML
    private TableView<Book> tableView;  // fx:id="tableView"

    @FXML
    private TableColumn<Book, String> columnBook;  // fx:id="columnBook"

    @FXML
    private TableColumn<Book, String> columnTitle; // fx:id="columnTitle"

    @FXML
    private TableColumn<Book, String> columnAuthor; // fx:id="columnAuthor"

    @FXML
    private TableColumn<Book, Long> columnISBN; // fx:id="columnISBN"

    @FXML
    private TableColumn<Book, Integer> columnReleaseYear; // fx:id="columnReleaseYear"

    @FXML
    private TableColumn<Book, String> columnType; // fx:id="columnType"

    @FXML
    private TableColumn<Book, String> columnScientificField; // fx:id="columnScientificField"

    @FXML
    private Button returnButton; // fx:id="returnButton"

    @FXML
    private Button deleteButton; // fx:id="deleteButton"

    private ObservableList<Book> bookList = FXCollections.observableList(MainMenuController.books); // list that holds all the book objects -- turned the static list books in an observable list

    @FXML
    public void initialize() {
        //Set Table Properties on scene initialization and bind every column to a property
        columnBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookType"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        columnISBN.setCellValueFactory(new PropertyValueFactory<Book, Long>("isbn"));
        columnReleaseYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("releaseYear"));
        columnType.setCellValueFactory(new PropertyValueFactory<Book, String>("subGenre"));
        columnScientificField.setCellValueFactory(new PropertyValueFactory<Book, String>("scientificField"));
        //Populate table with the book objects from the list.
        tableView.setItems(bookList);
    }

    //OnActionEvent Generated Method -- When Return Button is pressed

    @FXML
    void handleReturnButton(ActionEvent event) throws IOException {
        //Swap Scenes
        new SceneExchange().changeScene("/fxml/mainMenu.fxml", false, "Μενου");
    }

    //OnActionEvent Generated Method -- When Delete Button is pressed
    @FXML
    void handleDeleteButton(ActionEvent event) throws IOException {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem(); //Save the book entry that's about to get deleted

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Popup alert to double check book deletion.
        alert.initOwner(deleteButton.getScene().getWindow()); // assign icon in the popup alert
        alert.setTitle("Επιβεβαιωση διαγραφης!");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText("Εισαι σιγουρος οτι θελεις να διαγραψεις το βιβλιο?");

        //Handle Alert Response
        Optional<ButtonType> result = alert.showAndWait(); //Save button pressed in the alert popup

        if (result.get() == ButtonType.OK) { //If button pressed is OK
            //Check if a book entry was actually selected before pressing the delete button
            if (tableView.getSelectionModel().getSelectedItem() == null) { //if nothing was selected
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.initOwner(deleteButton.getScene().getWindow());
                alert1.setTitle("Σφαλμα");
                alert1.setHeaderText(null);
                alert1.setResizable(false);
                alert1.setContentText("Δεν υπαρχει επιλεγμενο βιβλιο!");
                alert1.showAndWait();

            } else { //If user selected a book entry from the table
                tableView.getItems().remove(selectedBook); //Remove book from the table
                MainMenuController.books.remove(selectedBook); //Remove book entry from books list

                //Empty the database file
                FileChannel.open(Paths.get("src/main/resources/database.txt"), StandardOpenOption.WRITE).truncate(0).close();

                for (Book book : MainMenuController.books) {
                    //Write every entry of the books list in the database text file
                    Files.write(Paths.get("src/main/resources/database.txt"), Arrays.asList(book.toString(), ""), StandardOpenOption.APPEND);
                }
            }
        }
    }
}