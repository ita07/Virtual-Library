package sample.com.ita07.virtuallibrary.helpers;

/**
 * LiteraryType options
 */
@SuppressWarnings("NonAsciiCharacters")
public enum LiteraryType {
    ΜΥΘΙΣΤΟΡΗΜΑ("ΜΥΘΙΣΤΟΡΗΜΑ"),
    ΝΟΥΒΕΛΑ("ΝΟΥΒΕΛΑ"),
    ΔΙΗΓΗΜΑ("ΔΙΗΓΗΜΑ"),
    ΠΟΙΗΣΗ("ΠΟΙΗΣΗ");

    private final String literaryBook; // String representation of the enum value

    /**
     * Pass String value that associates with the enum
     *
     * @param literaryBook A string value that associates with the enum
     */
    LiteraryType(String literaryBook) {
        this.literaryBook = literaryBook;
    }

    public String toString() {
        return this.literaryBook;
    }


}
