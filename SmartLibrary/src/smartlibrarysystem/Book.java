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
    
    public int getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
