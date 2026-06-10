package smartlibrarysystem;

public interface LibraryADT {
 
    // Add a new book to the BST catalogue
    void addBook(int isbn, String title, String author);
 
    // Search for a book by ISBN using recursive BST search
    void searchBook(int isbn);
 
    // Borrow a book - finds it in BST and pushes it onto the Stack
    void borrowBook(int isbn);
 
    // View full borrowing history from the Stack (most recent on top)
    void viewHistory();
}