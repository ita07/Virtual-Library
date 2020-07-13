package sample.com.ita07.virtuallibrary.helpers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
    }


    public static void main(String[] args) {
        launch(args);
    }
}