package smartlibrarysystem;

import java.time.LocalDate;

public interface LibraryADT {
    
    // Add a new book into the BST catalogue
    void addBook(int isbn, String title, String author);

    // Search for a book by ISBN using recursive BST search
    void searchBook(int isbn);

    // Display all books in the catalogue (BST in-order traversal)
    void viewCatalogue();


    // ── BORROWING OPERATIONS (STACK) ────────────────────────────────────────

    // Borrow a book - searches BST and pushes it onto the Stack
    void borrowBook(int isbn);

    // Borrow a book with student matrix number and due date tracking
    void borrowBookWithDetails(int isbn, String matrixNumber);

    // Return a book - pops from Stack, calculates fine, re-inserts into BST
    void returnBook(int lateDays);

    // Return a book with a specific return date for fine calculation
    void returnBookWithDetails(int isbn, String matrixNumber, LocalDate returnDate);

    // View detailed borrowing history with matrix number and due dates
    void viewDetailedHistory();
}