package sample.com.ita07.virtuallibrary.helpers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.com.ita07.virtuallibrary.database.DatabaseConnectivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The default class which opens the stage with the menu scene.
 */
public class Main extends Application {

    private static Stage primaryStage; // stage used across all classes

    /**
     * Setter for stage object
     * @param stage
     */
    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    /**
     * Getter for stage object
     * @return Returns the stage object
     */
    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }


    @Override
    public void start(Stage mainMenuStage) throws Exception {
        setPrimaryStage(mainMenuStage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml")); // root Node of the fxml file
        Scene sceneMainMenu = new Scene(root); // create a Scene for a specific root Node
        mainMenuStage.getIcons().add(new Image("images/icon.png")); // add stage Icon
        mainMenuStage.setTitle("Μενου");
        mainMenuStage.setScene(sceneMainMenu); // set scene for the stage
        mainMenuStage.setResizable(false);
        sceneMainMenu.getStylesheets().add("/stylesheets/main_menu.css"); // add CSS styling in the scene
        mainMenuStage.show();
        String createSQLiteTable = "CREATE TABLE IF NOT EXISTS library (\n"
                + "	'Type' TEXT NOT NULL,\n"
                + "	TITLE TEXT NOT NULL,\n"
                + "	AUTHOR TEXT NOT NULL,\n"
                + " ISBN INTEGER PRIMARY KEY,\n"
                + " 'RELEASE YEAR' INTEGER,\n"
                + " CATEGORY TEXT NOT NULL,\n"
                + " 'SCIENTIFIC FIELD' TEXT\n"
                + ");";

        try(Connection connection = DatabaseConnectivity.getConnection();
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


    public static void main(String[] args) {
        launch(args);
    }
}
