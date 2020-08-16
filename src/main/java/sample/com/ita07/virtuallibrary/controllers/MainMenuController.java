package sample.com.ita07.virtuallibrary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sample.com.ita07.virtuallibrary.database.DatabaseConnectivity;
import sample.com.ita07.virtuallibrary.helpers.SceneExchange;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Controller Class for Main Menu scene
 */
public class MainMenuController {
    @FXML  // fx:id of each button
    private Button exitButton, insertBookButton, searchButton, showBooks;

    @FXML
    private VBox mainPane; // fx:id of root Pane

    @FXML
    public void initialize() {
        String createSQLiteTable = "CREATE TABLE IF NOT EXISTS library (\n"
                + "	'Type' TEXT NOT NULL,\n"
                + "	TITLE TEXT NOT NULL,\n"
                + "	AUTHOR TEXT NOT NULL,\n"
                + " ISBN INTEGER PRIMARY KEY,\n"
                + " 'RELEASE YEAR' INTEGER,\n"
                + " CATEGORY TEXT NOT NULL,\n"
                + " 'SCIENTIFIC FIELD' TEXT\n"
                + ");";

        try (Connection connection = DatabaseConnectivity.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createSQLiteTable);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error occured while trying to reach database!");
            alert.showAndWait();
            System.exit(1);
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