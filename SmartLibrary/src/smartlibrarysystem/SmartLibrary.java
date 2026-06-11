package smartlibrarysystem;

import java.time.LocalDate;
import java.util.Scanner;

public class SmartLibrary implements LibraryADT {

    private BookBST catalogue;
    private Stack history;

    private static final double FINE_PER_DAY = 2.00;

    public SmartLibrary() {
        catalogue = new BookBST();
        history = new Stack();
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
            System.out.println("ISBN   : " + book.getIsbn());
            System.out.println("Title  : " + book.getTitle());
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
        System.out.println("ISBN   : " + returnedBook.getIsbn());
        System.out.println("Title  : " + returnedBook.getTitle());
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

    @Override
    public void viewHistory() {
        history.displayHistory();
    }

    @Override
    public void viewDetailedHistory() {
        history.displayDetailedHistory();
    }

    public void runMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== SMART LIBRARY SYSTEM ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Borrow Book (with Matrix & Due Date)");
            System.out.println("5. Return Book");
            System.out.println("6. Return Book (with Return Date)");
            System.out.println("7. View History");
            System.out.println("8. View Detailed History");
            System.out.println("9. View Catalogue");
            System.out.println("10. Exit");
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
                        borrowBook(isbn);
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter ISBN to borrow: ");
                        int isbn = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Matrix Number: ");
                        String matrix = sc.nextLine();
                        borrowBookWithDetails(isbn, matrix);
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Enter number of late days: ");
                        int lateDays = Integer.parseInt(sc.nextLine());
                        if (lateDays < 0) {
                            System.out.println("Late days cannot be negative.");
                            break;
                        }
                        returnBook(lateDays);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Enter return date (YYYY-MM-DD): ");
                        String dateInput = sc.nextLine();
                        LocalDate returnDate = LocalDate.parse(dateInput);
                        returnBookWithDetails(returnDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    }
                    break;

                case 7:
                    viewHistory();
                    break;

                case 8:
                    viewDetailedHistory();
                    break;

                case 9:
                    viewCatalogue();
                    break;

                case 10:
                    System.out.println("Thank you for using Smart Library System.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }
}