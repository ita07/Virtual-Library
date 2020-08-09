package sample.com.ita07.virtuallibrary.database;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {

    private static final String JDBA_DRIVER = "jdbc:sqlite:src/main/resources/virtualLibraryDatabase.db";

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(JDBA_DRIVER);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.initOwner(submitButton.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setContentText("An error just occured while trying to establish a connection with the database!\n\nError Output:\t" + e.getMessage());
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fatal error: Project files have been altered!");
            alert.showAndWait();
            System.exit(1);
        }
        return null;
    }
}
