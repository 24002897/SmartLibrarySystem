package smartlibrarysystem;

public class BookBST {

    private Book root;


    public BookBST() {
        root = null;
    }

    public void insert(int isbn, String title, String author) {
        root = insertRecursive(root, isbn, title, author);
    }

    private Book insertRecursive(Book node, int isbn, String title, String author) {

        if (node == null) {
            return new Book(isbn, title, author);
        }

        if (isbn < node.isbn) {
            node.left = insertRecursive(node.left, isbn, title, author);
        }
        else if (isbn > node.isbn) {
            node.right = insertRecursive(node.right, isbn, title, author);
        }
        else {
            System.out.println("ISBN already exists!");
        }

        return node;
    }


    public Book search(int isbn) {

        if (isbn <= 0) {
            System.out.println("Invalid ISBN.");
            return null;
        }

        return searchRecursive(root, isbn);
    }

    private Book searchRecursive(Book node, int isbn) {

        // Base Case 1: Book not found
        if (node == null) {
            return null;
        }

        // Base Case 2: Book found
        if (node.isbn == isbn) {
            return node;
        }

        // Recursive search in left subtree
        if (isbn < node.isbn) {
            return searchRecursive(node.left, isbn);
        }

        // Recursive search in right subtree
        return searchRecursive(node.right, isbn);
    }

    // Display all books
    public void displayAll() {

        if (root == null) {
            System.out.println("Catalogue is empty.");
            return;
        }

        System.out.println("\n===== BOOK CATALOGUE =====");
        inOrder(root);
    }

    private void inOrder(Book node) {

        if (node == null) {
            return;
        }

        inOrder(node.left);

        System.out.println(
            "ISBN: " + node.isbn +
            " | Title: " + node.title +
            " | Author: " + node.author
        );

        inOrder(node.right);
    }
}