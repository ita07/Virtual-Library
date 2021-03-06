package sample.com.ita07.virtuallibrary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.StringUtils;
import sample.com.ita07.virtuallibrary.customexceptions.CharacterMismatchException;
import sample.com.ita07.virtuallibrary.customexceptions.EmptyValueException;
import sample.com.ita07.virtuallibrary.customexceptions.NumberOutOfBoundsException;
import sample.com.ita07.virtuallibrary.database.CustomQueryBuilder;
import sample.com.ita07.virtuallibrary.helpers.LiteraryType;
import sample.com.ita07.virtuallibrary.helpers.SceneExchange;
import sample.com.ita07.virtuallibrary.helpers.ScientificType;
import sample.com.ita07.virtuallibrary.model.LiteraryBook;
import sample.com.ita07.virtuallibrary.model.ScientificBook;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controller Class for Insertion Menu scene
 */
public class InsertionMenuController {

    @FXML // fx:id="mainPane"
    private AnchorPane mainPane;

    @FXML // fx:id="generalCategoryComboBox"
    private ComboBox<String> generalCategoryComboBox;

    @FXML // fx:id="titleTextField"
    private TextField titleTextField;

    @FXML // fx:id="authorTextField"
    private TextField authorTextField;

    @FXML // fx:id="isbnTextField"
    private TextField isbnTextField;

    @FXML // fx:id="releaseYearTextField"
    private TextField releaseYearTextField;

    @FXML // fx:id="submitButton"
    private Button submitButton;

    @FXML // fx:id="clearButton"
    private Button clearButton;

    @FXML // fx:id="specificLiteraryCategoryComboBox"
    private ComboBox<String> specificLiteraryCategoryComboBox;

    @FXML // fx:id="specificScientificCategoryComboBox"
    private ComboBox<String> specificScientificCategoryComboBox;

    @FXML // fx:id="returnLabel"
    private Label returnLabel;

    @FXML // fx:id="extraPaneForScientific"
    private Pane extraPaneForScientific;

    @FXML // fx:id="scientificFieldTextField"
    private TextField scientificFieldTextField;

    private ObservableList<String> specificLiteraryTypeList; // hold the values Literary Type values
    private ObservableList<String> specificScientificTypeList; // holds the Scientific Type values

    private int currentYear; //holds an int value with the current Year

    @FXML
    public void initialize() {

        LocalDate currentDate = LocalDate.now(); //save today's date
        currentYear = currentDate.getYear(); //get the year
        //Disable context menu in every element of the scene
        mainPane.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        //General Category Initialization
        // holds the general category values
        ObservableList<String> generalCategoryList = FXCollections.observableArrayList("LITERARY", "SCIENTIFIC");
        generalCategoryComboBox.setItems(generalCategoryList);

        //set the combobox values to the corresponding enum string values
        specificScientificTypeList = FXCollections.observableArrayList(ScientificType.valueOf("MAGAZINE").toString(), ScientificType.valueOf("BOOK").toString(), ScientificType.valueOf("CONFERENCE_NOTES").toString());
        specificLiteraryTypeList = FXCollections.observableArrayList(LiteraryType.valueOf("FICTION").toString(), LiteraryType.valueOf("NOVEL").toString(), LiteraryType.valueOf("NARRATIVE").toString(), LiteraryType.valueOf("POETRY").toString());
    }

    //OnActionEvent Generated Method -- When General Category Combobox has a value selected
    @FXML
    void handleGeneralCategorySelection(ActionEvent event) {
        if (generalCategoryComboBox.getValue().equals("SCIENTIFIC")) {
            extraPaneForScientific.setVisible(true); //Set the extra pane's visibility for Scientific Book
            if (specificLiteraryCategoryComboBox.isVisible()) { //hide Literary Book's combobox if it's showing
                specificLiteraryCategoryComboBox.setVisible(false);
            }
            specificScientificCategoryComboBox.getItems().setAll(specificScientificTypeList); // Add values in Scientific's combobox
            specificScientificCategoryComboBox.setVisible(true);
        } else { // If Literary is selected
            extraPaneForScientific.setVisible(false); //hide extra pane for Scientific Book
            scientificFieldTextField.clear(); // Clear scientific text field
            if (specificScientificCategoryComboBox.isVisible()) { //hide Scientific Book's combobox if it's showing
                specificScientificCategoryComboBox.setVisible(false);
            }
            specificLiteraryCategoryComboBox.getItems().setAll(specificLiteraryTypeList);  // Add values in Literary's combobox
            specificLiteraryCategoryComboBox.setVisible(true);
        }
    }

    //OnKeyEvent Generated Method -- title textfield
    @FXML
    void handleTitleKeyEvent(KeyEvent event) {
        handleTitleField(titleTextField);
    }
    //OnKeyEvent Generated Method -- author textfield

    @FXML
    void handleAuthorKeyEvent(KeyEvent event) {
        handleAuthorField(authorTextField);
    }

    //OnKeyEvent Generated Method -- isbn textfield
    @FXML
    void handleIsbnKeyEvent(KeyEvent event) {
        handleIsbnAndReleaseYearFields(isbnTextField);
    }

    //OnKeyEvent Generated Method -- release year textfield
    @FXML
    void handleReleaseYearKeyEvent(KeyEvent event) {
        handleIsbnAndReleaseYearFields(releaseYearTextField);
    }

    //OnActionEvent Generated Method -- When submit button is pressed
    @FXML
    void handleSubmitButton(ActionEvent event) {

        try {
            // Check if general Category Combobox has any value selected
            if (generalCategoryComboBox.getValue() == null) { // if no value is selected
                throw new EmptyValueException("You have to choose one of the options first! It can't be empty"); //throw custome exception
            }
            String typeOfBook = generalCategoryComboBox.getSelectionModel().getSelectedItem(); //Store general category combobox value
            String title = titleTextField.getText(); //Store title value
            String author = authorTextField.getText(); //Store author value
            String isbn = isbnTextField.getText(); //Store isbn value
            String releaseYear = releaseYearTextField.getText(); //Store release Year value
            String innerType;
            String scientificField;
            if (generalCategoryComboBox.getValue().equals("SCIENTIFIC")) { // Check if general combobox value equals "ΕΠΙΣΤΗΜΟΝΙΚΟ"
                //Functions that (double check)/validate the input in the first four textfields
                validateTitle(title);
                validateAuthor(author);
                validateISBN(isbn);
                validateReleaseYear(releaseYear);
                innerType = specificScientificCategoryComboBox.getValue(); //store the corresponding enum's value based on the user's selection
                scientificField = scientificFieldTextField.getText(); // store scientificText field's input -- FREE TEXT no boundaries
                //Additional checks on the remaining inputs
                if (innerType == null) {
                    throw new EmptyValueException("Select on of the options in the Sub Category field! It can't be empty!");
                }
                if (scientificField.isEmpty()) {
                    throw new EmptyValueException("No input found in the Scientific Text! It can't be empty!");
                }
                //If everything goes well, store all the values like so
                ScientificBook bookEntry = new ScientificBook(typeOfBook, StringUtils.stripAccents(title), StringUtils.stripAccents(author), Long.parseLong(isbn), Integer.parseInt(releaseYear), innerType, scientificField);
                new CustomQueryBuilder(bookEntry);

            } else if (generalCategoryComboBox.getValue().equals("LITERARY")) { // Check if general combobox value equals "ΛΟΓΟΤΕΧΝΙΚΟ"
                //Functions that (double check)/validate the input in the first four textfields
                validateTitle(title);
                validateAuthor(author);
                validateISBN(isbn);
                validateReleaseYear(releaseYear);
                innerType = specificLiteraryCategoryComboBox.getValue(); //store the corresponding enum's value based on the user's selection
                //Additional check on the remaining input
                if (innerType == null) {
                    throw new EmptyValueException("Select on of the options in the Sub Category field! It can't be empty!");
                }
                //If everything goes well, store all the values like so
                LiteraryBook bookEntry = new LiteraryBook(typeOfBook, StringUtils.stripAccents(title), StringUtils.stripAccents(author), Long.parseLong(isbn), Integer.parseInt(releaseYear), innerType);
                new CustomQueryBuilder(bookEntry);
            }
            //Successful database operation message
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(submitButton.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setContentText("You have added a new book in your collection!");
            alert.showAndWait();
        } catch (EmptyValueException e) { // custom exception thrown from the validation methods
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(submitButton.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NumberFormatException e) { // custom exception thrown from the validation methods
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(submitButton.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setContentText("Error while parsing a number! Check again!");
            alert.showAndWait();
        } catch (NumberOutOfBoundsException | CharacterMismatchException | SQLException e) { // custom exception thrown from the validation methods
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(isbnTextField.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    //OnActionEvent Generated Method -- When Clear button is pressed
    @FXML
    void handleClearButton(ActionEvent event) {
        titleTextField.clear();
        authorTextField.clear();
        isbnTextField.clear();
        releaseYearTextField.clear();
        if (scientificFieldTextField.isVisible()) {
            specificScientificCategoryComboBox.getSelectionModel().clearSelection();
            scientificFieldTextField.clear();
        }
        specificLiteraryCategoryComboBox.getSelectionModel().clearSelection();
    }

    //OnMouseEvent Generated Method -- When return label is clicked
    @FXML
    void handleMouseClickOnReturnLabel(MouseEvent event) throws IOException {
        new SceneExchange().changeScene("/fxml/mainMenu.fxml", false, "Main Menu");
    }

    /**
     * Validate Isbn and Release Year TextFields on various key events
     *
     * @param textField A textfield to be examined
     */
    private void handleIsbnAndReleaseYearFields(TextField textField) {
        textField.setOnKeyTyped(keyEvent -> { //onKeyTyped
            if (!keyEvent.getCharacter().matches("^[0-9]")) { //if key type != 0-9

                keyEvent.consume(); //consume key typed
            }
        });
        textField.setOnKeyPressed(keyEvent -> { //onKeyPressed
            if ((keyEvent.getCode() == KeyCode.V) && keyEvent.isShortcutDown()) { //if someones tries to paste in the textfield
                keyEvent.consume(); //consume the Ctrl + V keys
            }
        });
    }

    /**
     * Validate Title TextField on various key events
     *
     * @param textField A textfield to be examined
     */
    private void handleTitleField(TextField textField) {
        //Convert every key typed to UpperCase
        textField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        //Consume Ctrl + V keys pressed to escape and paste invalid input!
        textField.setOnKeyPressed(keyEvent -> {
            if ((keyEvent.getCode() == KeyCode.V) && keyEvent.isShortcutDown()) {
                keyEvent.consume();
            }
        });
    }

    /**
     * Validate Author TextField on various key events
     *
     * @param textField A textfield to be examined
     */
    private void handleAuthorField(TextField textField) {
        //Convert every key typed to UpperCase
        textField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        //Restrict numbers in the author textfield and consume the key typed if one is present
        textField.setOnKeyTyped(keyEvent -> {
            if (keyEvent.getCharacter().matches("^[0-9]")) {
                keyEvent.consume();
            }
        });

        //Consume Ctrl + V keys pressed to escape and paste invalid input!
        textField.setOnKeyPressed(keyEvent -> {
            if ((keyEvent.getCode() == KeyCode.V) && keyEvent.isShortcutDown()) {
                keyEvent.consume();
            }
        });
    }

    /**
     * Extra validation for the title textfield in case it bypassed the first checks
     *
     * @param title String value of the titleTextfield input
     * @throws CharacterMismatchException If an restricted character is present in the title textfield
     */
    private void validateTitle(String title) throws CharacterMismatchException {

        if (title.trim().isEmpty()) {
            throw new CharacterMismatchException("Error! Title field can't be empty!");
        }
    }

    /**
     * Extra validation for the author textfield in case it bypassed the first checks
     *
     * @param author String value of the authorTextfield input
     * @throws CharacterMismatchException If a restricted character is present in the title textfield
     */
    private void validateAuthor(String author) throws CharacterMismatchException {
        if (author.isEmpty()) {
            throw new CharacterMismatchException("Error! Author field can't be empty!");
        }

        for (int i = 0; i < author.length(); i++) {
            if (Character.isDigit(author.charAt(i))) { //Check for any numbers inside the title field
                throw new CharacterMismatchException("You can't have numbers in the Title Field!");
            }
            if (!Character.isLetter(author.charAt(i)) && (!Character.toString(author.charAt(i)).matches("\\s"))) { //Check if anything other than letter or a space character is present
                throw new CharacterMismatchException("You can only enter characters in the Author field");
            }
        }
    }

    /**
     * Extra validation for the isbn textfield in case it bypassed the first checks
     *
     * @param isbn String value of the isbnTextfield input
     * @throws CharacterMismatchException If a restricted character is present in the title textfield
     * @throws NumberOutOfBoundsException If numbers are out of the specified bounds
     */
    private void validateISBN(String isbn) throws CharacterMismatchException, NumberOutOfBoundsException {
        String selectISBNQuery = "SELECT ISBN FROM library WHERE ISBN = ?";
        if (isbn.isEmpty()) {
            throw new CharacterMismatchException("Error! ISBN field can't be empty!");
        } else if (isbn.trim().length() != 13) { //ISBN size must be 13 digits
            throw new NumberOutOfBoundsException("ISBN field contains an invalid number of digits! Please enter 13 digits!");

        } else if (Long.parseLong(isbn) < 9780000000000L || Long.parseLong(isbn) > 9799999999999L) { // ISBN number must be within these bounds
            throw new NumberOutOfBoundsException("ISBN number is invalid! Number must begin with 978 or 978!");
        } else {
            for (int i = 0; i < isbn.length(); i++) {
                if (!Character.isDigit(isbn.charAt(i))) {
                    throw new CharacterMismatchException("You can only use numbers on the ISBN field!");
                }
            }
        }
        //Check if our isbn input has already been registered in our database

    }

    /**
     * Extra validation for the release year textfield in case it bypassed the first checks
     *
     * @param releaseYear String value of the releaseYearTextfield input
     * @throws NumberOutOfBoundsException If numbers are out of the specified bounds
     * @throws CharacterMismatchException If a restricted character is present in the title textfield
     */
    private void validateReleaseYear(String releaseYear) throws NumberOutOfBoundsException, CharacterMismatchException {
        if (releaseYear.isEmpty()) {
            throw new CharacterMismatchException("Error! Year of Publication field is empty!");
        } else if (releaseYear.trim().length() != 4) { // releaseYear must be 4 digits
            throw new NumberOutOfBoundsException("Year of Publication field consists of 4 digits only!");
        } else if (Integer.parseInt(releaseYear) < 1000 || Integer.parseInt(releaseYear) > currentYear) { //release year must be within these bounds
            throw new NumberOutOfBoundsException("Year of Publication field is invalid! Check again");
        } else {
            for (int i = 0; i < releaseYear.length(); i++) {
                if (!Character.isDigit(releaseYear.charAt(i))) {
                    throw new CharacterMismatchException("You can only use numbers on the Year of Publication field!");
                }
            }
        }
    }
}