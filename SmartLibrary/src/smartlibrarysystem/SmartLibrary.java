package smartlibrarysystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class SmartLibrary implements LibraryADT {

    private BookBST catalogue;
    private Stack history;

    private static final double FINE_PER_DAY = 2.00;
    private static final String FILE_NAME = "books.txt"; 

    public SmartLibrary() {
        catalogue = new BookBST();
        history = new Stack();
        loadBooksFromFile();
    }

    @Override
    public void addBook(int isbn, String title, String author) {
        catalogue.insert(isbn, title, author);
        System.out.println("Book added successfully.");
    }

    @Override
    public void searchBook(int isbn) {
        Book book = catalogue.search(isbn);
        if (book != null) {
            System.out.println("\n===== BOOK FOUND =====");
            System.out.println("ISBN    : " + book.getIsbn());
            System.out.println("Title   : " + book.getTitle());
            System.out.println("Author : " + book.getAuthor());
        } else {
            System.out.println("Book not found.");
        }
    }

    @Override
    public void viewCatalogue() {
        catalogue.displayAll();
    }

    @Override
    public void borrowBook(int isbn) {
        Book book = catalogue.search(isbn);
        if (book != null) {
            history.push(book);
            System.out.println("Book borrowed successfully.");
            System.out.println("Title: " + book.getTitle());
        } else {
            System.out.println("Book not available.");
        }
    }

    @Override
    public void borrowBookWithDetails(int isbn, String matrixNumber) {
        Book book = catalogue.search(isbn);
        if (book != null) {
            history.borrowBookWithDetails(matrixNumber, book);
        } else {
            System.out.println("Book not available.");
        }
    }

    @Override
    public void returnBook(int lateDays) {
        Book returnedBook = history.pop();
        if (returnedBook == null) return;

        double fine = lateDays * FINE_PER_DAY;

        System.out.println("\n===== BOOK RETURNED =====");
        System.out.println("ISBN    : " + returnedBook.getIsbn());
        System.out.println("Title   : " + returnedBook.getTitle());
        System.out.println("Author : " + returnedBook.getAuthor());

        if (lateDays > 0) {
            System.out.println("Late Days : " + lateDays);
            System.out.printf("Fine      : RM %.2f%n", fine);
        } else {
            System.out.println("Returned on time.");
            System.out.println("Fine      : RM 0.00");
        }

        catalogue.insert(returnedBook.getIsbn(), returnedBook.getTitle(), returnedBook.getAuthor());
    }

    @Override
    public void returnBookWithDetails(LocalDate returnDate) {
        Stack.BorrowRecord record = history.returnBookWithDetails(returnDate);
        if (record != null) {
            catalogue.insert(
                record.getBook().getIsbn(),
                record.getBook().getTitle(),
                record.getBook().getAuthor()
            );
        }
    }

//    @Override
//    public void viewHistory() {
//        history.displayHistory();
//    }

    @Override
    public void viewDetailedHistory() {
        history.displayDetailedHistory();
    }

    private void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int isbn = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    catalogue.insert(isbn, title, author);
                    counter++;
                }
            }
            if (counter > 0) {
                System.out.println(">>> System database sync complete: " + counter + " record(s) loaded.");
            }
        } catch (IOException e) {
            System.out.println(">>> Database configuration file not found. Starting with an empty catalog profiles.");
        } catch (NumberFormatException e) {
            System.out.println(">>> Corrupt numeric format detected inside database file index.");
        }
    }

    private void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            if (catalogue != null) {
                catalogue.saveToFile(writer);
            }
     
        } catch (IOException e) {
            System.out.println(">>> Error: System tracking state could not be saved to storage device.");
        }
    }

    public void runMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== SMART LIBRARY SYSTEM ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book ");
            System.out.println("4. Return Book ");
            System.out.println("5. View History");
//            System.out.println("6. View Detailed History");
            System.out.println("7. View Catalogue");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter ISBN: ");
                        int isbn = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        if (title.trim().isEmpty() || author.trim().isEmpty()) {
                            System.out.println("Title and Author cannot be empty.");
                            break;
                        }
                        addBook(isbn, title, author);
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter ISBN to search: ");
                        int isbn = Integer.parseInt(sc.nextLine());
                        searchBook(isbn);
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter ISBN to borrow: ");
                        int isbn = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Matrix Number: ");
                        String matrix = sc.nextLine();
                        borrowBookWithDetails(isbn, matrix);
                        catalogue.delete(isbn);
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter return date (YYYY-MM-DD): ");
                        String dateInput = sc.nextLine();
                        LocalDate returnDate = LocalDate.parse(dateInput);
                        returnBookWithDetails(returnDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    }
                    break;

//                case 5:
//                    viewHistory();
//                    break;

                case 5:
                    viewDetailedHistory();
                    break;

                case 7:
                    viewCatalogue();
                    break;

                case 8:
                    System.out.println("Thank you for using Smart Library System.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid menu option.");
            }
            saveBooksToFile();
        }
    }
}
