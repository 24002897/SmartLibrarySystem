package smartlibrarysystem;

// The ArrayList acts as our stack storage
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Stack {
    
    private ArrayList<Book> history;
    private ArrayList<BorrowRecord> detailedHistory;  // NEW: For records with dates
    
    // NEW: Inner class to store borrowing records with dates
    public class BorrowRecord {
        private String matrixNumber;
        private Book book;
        private LocalDate borrowDate;
        private LocalDate dueDate;
        
        public BorrowRecord(String matrixNumber, Book book, LocalDate borrowDate) {
            this.matrixNumber = matrixNumber;
            this.book = book;
            this.borrowDate = borrowDate;
            this.dueDate = borrowDate.plusDays(14);
        }
        
        public String getMatrixNumber() { 
            return matrixNumber; 
        }
        
        public Book getBook() { 
            return book; 
        }
        
        public LocalDate getBorrowDate() { 
            return borrowDate; 
        }
        
        public LocalDate getDueDate() { 
            return dueDate; 
        }
        
        public String toString() {
            return book.getTitle() + " (Due: " + dueDate + ")";
        }
    }
    
    // Constructor - Creates an empty borrowing history
    public Stack() {
        history = new ArrayList<>();
        detailedHistory = new ArrayList<>();  // NEW: Initialize detailed history
    }
    
    // Add a book to borrowing history (push to top)
    public void addToHistory(Book book) {
        if (book == null) {
            System.out.println("Error: Cannot add null book to history");
            return;
        }
        history.add(book);
        System.out.println("Book added to borrowing history: " + book.getTitle());
    }
    
    // Remove most recent book from history (pop from top)
    public Book removeFromHistory() {
        if (isEmpty()) {
            System.out.println("Error: No borrowing history to return from");
            return null;
        }
        Book returnedBook = history.remove(history.size() - 1);
        System.out.println("Book returned and removed from history: " + returnedBook.getTitle());
        return returnedBook;
    }
    
    // View most recent borrowed book without removing it
    public Book viewMostRecent() {
        if (isEmpty()) {
            System.out.println("No borrowing history available.");
            return null;
        }
        return history.get(history.size() - 1);
    }
    
    public boolean isEmpty() {
        return history.isEmpty();
    }
    
    // Get total number of books ever borrowed
    public int size() {
        return history.size();
    }
    
    public void clearHistory() {
        history.clear();
        System.out.println("Borrowing history has been cleared.");
    }
    
//    public void displayHistory() {
//        System.out.println("\n========================================");
//        System.out.println("           STUDENT BORROWING HISTORY            ");
//        System.out.println("========================================");
//        
//        if (history.isEmpty()) {
//            System.out.println("No borrowing history found.");
//            System.out.println("   (Student has not borrowed any books yet)\n");
//            return;
//        }
//        
//        System.out.println("Showing most recent first:\n");
//        
//        // Loop backwards to show most recent on top
//        for (int i = history.size() - 1; i >= 0; i--) {
//            int position = history.size() - i;  
//            Book book = history.get(i);
//            System.out.println(position + ". \"" + book.getTitle() + "\"");
//            System.out.println("   Author: " + book.getAuthor());
//            System.out.println("   ISBN: " + book.getIsbn());
//            System.out.println("   ========================================");
//        }
//        
//        System.out.println("========================================");
//        System.out.println("Total books borrowed: " + history.size());
//        System.out.println("========================================\n");
//    }
    
    public void displaySimpleHistory() {
        if (history.isEmpty()) {
            System.out.println("Borrowing history is empty.");
            return;
        }
        
        System.out.println("\n=== BORROWING HISTORY (Most Recent First) ===");
        for (int i = history.size() - 1; i >= 0; i--) {
            Book book = history.get(i);
            System.out.println((history.size() - i) + ". " + book.toString());
        }
        System.out.println("==============================================\n");
    }

    // push method - adds book to top of stack
    public void push(Book book) {
        addToHistory(book);
    }
    
    // pop method - removes and returns top book
    public Book pop() {
        return removeFromHistory();
    }
    
    // peek method - views top book without removing
    public Book peek() {
        return viewMostRecent();
    }
    
    // ========== NEW METHODS FOR FINE MANAGEMENT ==========
    
    // NEW: Borrow with matrix number and dates
    public void borrowBookWithDetails(String matrixNumber, Book book) {
        if (matrixNumber == null || matrixNumber.trim().isEmpty()) {
            System.out.println("Error: Invalid matrix number");
            return;
        }
        if (book == null) {
            System.out.println("Error: Cannot borrow null book");
            return;
        }
        
        // Also add to your original history
        addToHistory(book);
        
        // Add to detailed history
        BorrowRecord record = new BorrowRecord(matrixNumber, book, LocalDate.now());
        detailedHistory.add(record);
        
        System.out.println("\n Book borrowed successfully!");
        System.out.println("  Matrix: " + matrixNumber);
        System.out.println("  Book: " + book.getTitle());
        System.out.println("  Borrow date: " + record.getBorrowDate());
        System.out.println("  Due date: " + record.getDueDate());
    }
    
    // NEW: Return book and get details for fine calculation
    // UPDATED: Pass returnDate as a parameter for easier testing!
public BorrowRecord returnBookWithDetails(int isbn, String matrixNumber, LocalDate returnDate) {
    if (detailedHistory.isEmpty()) {
        System.out.println("Error: No borrowing history with details");
        return null;
    }
    
    BorrowRecord targetRecord = null;
    int targetIndex = -1;
    
    for (int i = 0; i < detailedHistory.size(); i++) {
            BorrowRecord record = detailedHistory.get(i);
            if (record.getBook().getIsbn() == isbn && record.getMatrixNumber().equals(matrixNumber)) {
                targetRecord = record;
                targetIndex = i;
                break;
            }
    }
    
    if (targetRecord == null) {
            System.out.println("Error: No matching borrow record found for Matrix " + matrixNumber + " and ISBN " + isbn);
            return null;
        }
    
    detailedHistory.remove(targetIndex);
        if (targetIndex < history.size()) {
            history.remove(targetIndex);
        }
        
        System.out.println("\n Book returned: " + targetRecord.getBook().getTitle());
        System.out.println("  Matrix: " + targetRecord.getMatrixNumber());
        System.out.println("  Borrowed: " + targetRecord.getBorrowDate());
        System.out.println("  Due date: " + targetRecord.getDueDate());
        
    if (returnDate.isAfter(targetRecord.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(targetRecord.getDueDate(), returnDate);
            double fine = daysLate * 1.00; 
            System.out.println("  OVERDUE by " + daysLate + " days");
            System.out.println("  Fine: RM" + fine);
        } else {
            System.out.println("  Returned on time! No fine.");
        }
        
        return targetRecord;
    }    
        
    

    
    // NEW: View most recent detailed record
    public BorrowRecord viewMostRecentDetail() {
        if (detailedHistory.isEmpty()) {
            System.out.println("No detailed borrowing history");
            return null;
        }
        return detailedHistory.get(detailedHistory.size() - 1);
    }
    
    // NEW: Display detailed history
    public void displayDetailedHistory() {
        System.out.println("\n========================================");
        System.out.println("     DETAILED BORROWING HISTORY");
        System.out.println("========================================");
        
        if (detailedHistory.isEmpty()) {
            System.out.println("No detailed borrowing history found.\n");
            return;
        }
        
        for (int i = detailedHistory.size() - 1; i >= 0; i--) {
            int position = detailedHistory.size() - i;
            BorrowRecord record = detailedHistory.get(i);
            System.out.println(position + ". " + record.getBook().getTitle());
            System.out.println("   Matrix: " + record.getMatrixNumber());
            System.out.println("   Borrowed: " + record.getBorrowDate());
            System.out.println("   Due date: " + record.getDueDate());
            System.out.println("   ========================================");
        }
        
        System.out.println("Total borrows: " + detailedHistory.size());
        System.out.println("========================================\n");
    }
    
    // NEW: Get size of detailed history
    public int detailedSize() {
        return detailedHistory.size();
    }
    
    // NEW: Check if detailed history is empty
    public boolean isDetailedEmpty() {
        return detailedHistory.isEmpty();
    }
    
    // NEW: Clear detailed history
    public void clearDetailedHistory() {
        detailedHistory.clear();
        System.out.println("Detailed borrowing history cleared.");
    }
}
