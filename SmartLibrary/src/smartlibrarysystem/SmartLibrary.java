package smartlibrarysystem;

import java.util.Scanner;

public class SmartLibrary implements LibraryADT {

    private BookBST catalogue;
    private Stack history;

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

            System.out.println("Borrowed: " + book.getTitle());

            // Uncomment if BST delete() is added later
            // catalogue.delete(isbn);

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

    public void runMenu() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== SMART LIBRARY SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. View History");
            System.out.println("5. View Catalogue");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
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
                        searchBook(Integer.parseInt(sc.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }

                    break;

                case 3:

                    try {
                        System.out.print("Enter ISBN to borrow: ");
                        borrowBook(Integer.parseInt(sc.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("ISBN must be numeric.");
                    }

                    break;

                case 4:
                    viewHistory();
                    break;

                case 5:
                    viewCatalogue();
                    break;

                case 6:
                    System.out.println("Exiting Smart Library System...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }
}