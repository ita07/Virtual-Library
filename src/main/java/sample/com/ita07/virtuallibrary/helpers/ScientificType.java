package sample.com.ita07.virtuallibrary.helpers;

/**
 * ScientificType options
 */
@SuppressWarnings("NonAsciiCharacters")
public enum ScientificType {
    MAGAZINE("MAGAZINE"),
    BOOK("BOOK"),
    CONFERENCE_NOTES("CONFERENCE NOTES");

    private final String scientificBook; // String representation of the enum value

    /**
     * Pass String value that associates with the enum
     *
     * @param scientificBook A string value that associates with the enum
     */
    ScientificType(String scientificBook) {
        this.scientificBook = scientificBook;
    }

    public String toString() {
        return this.scientificBook;
    }
}
