package smartlibrarysystem;

public class Book {

    int isbn;
    String title;
    String author;
    Book left;
    Book right;

    public Book(int isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        left = null;
        right = null;
    }
}
