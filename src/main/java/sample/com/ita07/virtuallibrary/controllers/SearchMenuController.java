package sample.com.ita07.virtuallibrary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.com.ita07.virtuallibrary.customexceptions.EmptyValueException;
import sample.com.ita07.virtuallibrary.database.DatabaseConnectivity;
import sample.com.ita07.virtuallibrary.helpers.SceneExchange;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller Class for Search Menu scene
 */
public class SearchMenuController {

    private final ToggleGroup group = new ToggleGroup(); // group for radio buttons
    @FXML
    private TextField searchTextfield; // fx:id="searchTextfield"
    @FXML
    private ListView<String> bookList; // fx:id="bookList"
    @FXML
    private Button searchButton; // fx:id="searchButton"
    @FXML
    private Label returnLabel; // fx:id="returnLabel"
    @FXML
    private RadioButton titleRadioButton; // fx:id="titleRadioButton"
    @FXML
    private RadioButton authorRadioButton; // fx:id="authorRadioButton"

    @FXML
    public void initialize() {
        //Assign the radio buttons into the same group so only one can be selected
        titleRadioButton.setToggleGroup(group);
        authorRadioButton.setToggleGroup(group);
    }

    //OnActionEvent Generated Method -- When Title Radio Button is selected
    @FXML
    void handleTitleRadioButton(ActionEvent event) {
        //clear the ListView
        bookList.getItems().clear();
    }

    //OnActionEvent Generated Method -- When Author Radio Button is selected
    @FXML
    void handleAuthorRadioButton(ActionEvent event) {
        //clear the ListView
        bookList.getItems().clear();
    }

    //OnKeyTypedEvent Generated Method -- When a key is pressed in the textfield
    @FXML
    void handleSearchTextFieldKeyPressed(KeyEvent event) {
        //Turn every key pressed to UpperCase
        searchTextfield.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    //OnActionEvent Generated Method -- When Search Button is pressed
    @FXML
    void handleSearchButton(ActionEvent event) {

        try {
            bookList.getItems().clear();
            String input = searchTextfield.getText(); //Get input from textfield
            if (input.trim().isEmpty()) { //if input is empty
                throw new EmptyValueException("Η αναζητηση σου ειναι κενη! Προσπαθησε ξανα!"); //Throw custom exception
            }
            if (titleRadioButton.isSelected()) { // If title radio button is selected
                checkTitle(input);
            } else if (authorRadioButton.isSelected()) { // If title radio button is selected
                checkAuthor(input);
            } else {
                throw new EmptyValueException("Πρεπει να επιλεξεις Τιτλος ή Συγγραφεας ωστε η αναζητηση να γινει με βαση την επιλογη σου!"); //Throw custom exception if none of the above matches
            }
            if (bookList.getItems().isEmpty()) { //If nothing is found containing the input from the textfield
                throw new EmptyValueException("Δεν βρεθηκε καμια εκχωρηση με βαση τις πληροφοριες που εισαγατε!"); // throw custom exception
            }
        } catch (EmptyValueException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR); // Popup error alert
            alert.initOwner(searchButton.getScene().getWindow()); // assign icon in the popup alert
            alert.setTitle("Σφαλμα αναζητησης");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage()); // Set context text as the message thrown by the exception
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR); // Popup error alert
            alert.initOwner(searchButton.getScene().getWindow()); // assign icon in the popup alert
            alert.setTitle("Σφαλμα αναζητησης");
            alert.setHeaderText(null);
            alert.setContentText("Σφαλμα κατα την προσπελαση της βασης δεδομενων"); // Set context text as the message thrown by the exception
            alert.showAndWait();
        }
    }

    //OnMouseClicked Generated Method -- When Return label is clicked
    @FXML
    void handleMouseClickOnReturnLabel(MouseEvent event) throws IOException {
        //Swap scenes
        new SceneExchange().changeScene("/fxml/mainMenu.fxml", false, "Μενου");
    }

    /**
     * Check every Book Title from the database, match it then return the titles matched
     *
     * @param input String value of the search textfield
     */

    private void checkTitle(String input) throws SQLException {
        String sqlSelectTitleQuery = "SELECT TITLE FROM LIBRARY WHERE TITLE LIKE ?;";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectTitleQuery)) {

            preparedStatement.setString(1, "%" + input + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("TITLE");
                bookList.getItems().add(title);
            }
        }
    }


    /**
     * Check every Book Author from the database , match it then return the authors matched
     *
     * @param input String value of the search textfield
     */
    private void checkAuthor(String input) throws SQLException {
        String sqlSelectAuthorQuery = "SELECT AUTHOR FROM LIBRARY WHERE AUTHOR LIKE ?;";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectAuthorQuery)) {

            preparedStatement.setString(1, "%" + input + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String author = resultSet.getString("AUTHOR");
                bookList.getItems().add(author);
            }

        }
    }
}