package sample.com.ita07.virtuallibrary.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Provides a convenient way to customize every scene
 *
 */
public class SceneExchange {

    /**
     * Changes scene and handles stage properties
     * @param fxmlFile Fxml file loaded in the scene
     * @param resizeStage Ability to resize the stage
     * @param stageName Title of the stage
     * @throws IOException On fxml load failure
     */
    public void changeScene(String fxmlFile, boolean resizeStage, String stageName) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile)); // root Node of the fxml file
        Scene scene = new Scene(root); // create a Scene for a specific root Node
        scene.getStylesheets().add("/stylesheets/main_menu.css"); // add CSS styling in the scene
        Stage stage = Main.getPrimaryStage(); // get stage object from main menu, no need for a new stage
        stage.setTitle(stageName);
        stage.setResizable(resizeStage);
        stage.setScene(scene); // set scene for the stage
    }
}
