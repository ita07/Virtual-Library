package sample.com.ita07.virtuallibrary.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Book model subclass
 */
public class LiteraryBook extends Book {

    //
    // Fields
    //

    private SimpleStringProperty subGenre; // Genre value from scientific enum
    private SimpleStringProperty bookType; // ΛΟΓΟΤΕΧΝΙΚΟ

    //
    // Constructors
    //

    /**
     * Get default value of a Literary Book
     *
     * @param bookType    Type of the book
     * @param title       Title of the book
     * @param author      Author of the book
     * @param isbn        ISBN number of the book
     * @param releaseYear Release year of the book
     * @param subGenre    Enum Genre value of the book
     */
    public LiteraryBook(String bookType, String title, String author, Long isbn, Integer releaseYear, String subGenre) {
        super(title, author, isbn, releaseYear);
        this.subGenre = new SimpleStringProperty(subGenre);
        this.bookType = new SimpleStringProperty(bookType);
    }

    //
    // Methods
    //

    //
    // Accessor methods
    //

    /**
     * Get the genre of the book
     *
     * @return Genre value
     */
    @Override
    public String getSubGenre() {
        return subGenre.get();
    }

    /**
     * Get the type of the book
     *
     * @return Type of book - "ΛΟΓΟΤΕΧΝΙΚΟ"
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
        return getBookType() + System.lineSeparator() + getTitle() + System.lineSeparator() + getAuthor() + System.lineSeparator() + getIsbn() + System.lineSeparator() + getReleaseYear() + System.lineSeparator() + getSubGenre();
    }

}

