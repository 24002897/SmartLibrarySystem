package smartlibrarysystem;

// The ArrayList acts as our stack storage
import java.util.ArrayList;

public class Stack {
    
    private ArrayList<Book> history;
    
//Constructor - Creates an empty borrowing history
    public Stack() {
        history = new ArrayList<>();
    }
    
//Add a book to borrowing history (push to top)
    public void addToHistory(Book book) {
        if (book == null) {
            System.out.println("Error: Cannot add null book to history");
            return;
        }
        history.add(book);
        System.out.println("Book added to borrowing history: " + book.getTitle());
    }
    
//Remove most recent book from history (pop from top)
    public Book removeFromHistory() {
        if (isEmpty()) {
            System.out.println("Error: No borrowing history to return from");
            return null;
        }
        Book returnedBook = history.remove(history.size() - 1);
        System.out.println("Book returned and removed from history: " + returnedBook.getTitle());
        return returnedBook;
    }
    
//View most recent borrowed book without removing it
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
    
//Get total number of books ever borrowed

    public int size() {
        return history.size();
    }
    
    public void clearHistory() {
        history.clear();
        System.out.println("Borrowing history has been cleared.");
    }
    
    public void displayHistory() {
        System.out.println("\n========================================");
        System.out.println("           STUDENT BORROWING HISTORY            ");
        System.out.println("========================================");
        
        if (history.isEmpty()) {
            System.out.println("No borrowing history found.");
            System.out.println("   (Student has not borrowed any books yet)\n");
            return;
        }
        
        System.out.println("Showing most recent first:\n");
        
        // Loop backwards to show most recent on top
        for (int i = history.size() - 1; i >= 0; i--) {
            int position = history.size() - i;  
            Book book = history.get(i);
            System.out.println(position + ". \"" + book.getTitle() + "\"");
            System.out.println("   Author: " + book.getAuthor());
            System.out.println("   ISBN: " + book.getIsbn());
            System.out.println("   ========================================");
        }
        
        System.out.println("========================================");
        System.out.println("Total books borrowed: " + history.size());
        System.out.println("========================================\n");
    }
    
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

//push method - adds book to top of stack
    public void push(Book book) {
        addToHistory(book);
    }
    
//pop method - removes and returns top book
    public Book pop() {
        return removeFromHistory();
    }
    
//peek method - views top book without removing
    public Book peek() {
        return viewMostRecent();
    }
}