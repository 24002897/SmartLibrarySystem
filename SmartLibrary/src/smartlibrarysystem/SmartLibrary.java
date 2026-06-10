package smartlibrarysystem;

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
    public void viewHistory() {
        history.displayHistory();
    }

    public void viewCatalogue() {
        catalogue.displayAll();
    }

    public void returnBook(int lateDays) {

        Book returnedBook = history.pop();

        if (returnedBook == null) {
            return;
        }

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

        // Optional:
        // Add back into catalogue when returned

        catalogue.insert(
                returnedBook.getIsbn(),
                returnedBook.getTitle(),
                returnedBook.getAuthor()
        );
    }

    // Menu
    public void runMenu() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n========== SMART LIBRARY SYSTEM ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View History");
            System.out.println("6. View Catalogue");
            System.out.println("7. Exit");
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

                case 5:

                    viewHistory();
                    break;

                case 6:

                    viewCatalogue();
                    break;

                case 7:

                    System.out.println("Thank you for using Smart Library System.");
                    sc.close();
                    return;

                default:

                    System.out.println("Invalid menu option.");
            }
        }
    }
}