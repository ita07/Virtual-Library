package sample.com.ita07.virtuallibrary.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Book model subclass
 */
public class ScientificBook extends Book {

    //
    // Fields
    //

    private SimpleStringProperty scientificField; // String value - info about the book
    private SimpleStringProperty subGenre; // Genre value from scientific enum
    private SimpleStringProperty bookType; // ΕΠΙΣΤΗΜΟΝΙΚΟ

    //
    // Constructors
    //

    /**
     * Get default value of a Scientific Book
     *
     * @param bookType        Type of the book
     * @param title           Title of the book
     * @param author          Author of the book
     * @param isbn            ISBN number of the book
     * @param releaseYear     Release year of the book
     * @param subGenre        Enum Genre value of the book
     * @param scientificField Info about the book
     */
    public ScientificBook(String bookType, String title, String author, Long isbn, Integer releaseYear, String subGenre, String scientificField) {
        super(title, author, isbn, releaseYear);
        this.bookType = new SimpleStringProperty(bookType);
        this.scientificField = new SimpleStringProperty(scientificField);
        this.subGenre = new SimpleStringProperty(subGenre);

    }

    //
    // Methods
    //

    //
    // Accessor methods
    //

    /**
     * Get info about the book
     *
     * @return Info about the book
     */
    public String getScientificField() {
        return scientificField.get();
    }

    /**
     * Get the genre of the book
     *
     * @return Genre value
     */
    public String getSubGenre() {
        return subGenre.get();
    }

    /**
     * Get the type of the book
     *
     * @return Type of book - "ΕΠΙΣΤΗΜΟΝΙΚΟ"
     */
    @Override
    public String getBookType() {
        return bookType.get();
    }

    //
    // Other methods
    //

    @Override
    public String toString() {
        return getBookType() + System.lineSeparator() + getTitle() + System.lineSeparator() + getAuthor() + System.lineSeparator() + getIsbn() + System.lineSeparator() + getReleaseYear() + System.lineSeparator() + getSubGenre() + System.lineSeparator() + getScientificField();
    }

}
