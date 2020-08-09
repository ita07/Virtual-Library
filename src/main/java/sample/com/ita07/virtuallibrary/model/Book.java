package sample.com.ita07.virtuallibrary.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Book model class
 */
abstract public class Book {

    //
    // Fields
    //
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleLongProperty isbn;
    private SimpleIntegerProperty releaseYear;

    //
    // Constructors
    //

    /**
     * A book's default values
     *
     * @param title       Book's Title
     * @param author      Book's Author
     * @param isbn        Book's ISBN
     * @param releaseYear Book's Release Year
     */

    public Book(String title, String author, Long isbn, Integer releaseYear) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleLongProperty(isbn);
        this.releaseYear = new SimpleIntegerProperty(releaseYear);
    }

    //
    // Methods
    //


    //
    // Accessor methods
    //


    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title.get();
    }


    /**
     * Get the value of author
     *
     * @return the value of author
     */
    public String getAuthor() {
        return author.get();
    }


    /**
     * Get the value of isbn
     *
     * @return the value of isbn
     */
    public Long getIsbn() {
        return isbn.get();
    }


    /**
     * Get the value of releaseYear
     *
     * @return the value of releaseYear
     */
    public Integer getReleaseYear() {
        return releaseYear.get();
    }


    abstract public String getBookType();


    abstract public String getSubGenre();

    //
    // Other methods
    //

    public String toString() {
        return getBookType() + System.lineSeparator() + getTitle() + System.lineSeparator() + getAuthor() + System.lineSeparator() + getIsbn() + System.lineSeparator() + getReleaseYear() + System.lineSeparator() + getSubGenre();
    }

}
