package smartlibrarysystem;

import java.io.PrintWriter;

public class BookBST {

    private Book root;

    public BookBST() {
        root = null;
    }

    // Add a book to the catalogue
    public void insert(int isbn, String title, String author) {
        root = insertRecursive(root, isbn, title, author);
    }

    private Book insertRecursive(Book node, int isbn, String title, String author) {
        if (node == null) {
            return new Book(isbn, title, author);
        }
        if (isbn < node.isbn) {
            node.left = insertRecursive(node.left, isbn, title, author);
        } else if (isbn > node.isbn) {
            node.right = insertRecursive(node.right, isbn, title, author);
        } else {
            System.out.println("ISBN already exists!");
        }
        return node;
    }

    // Search for a book by ISBN
    public Book search(int isbn) {
        if (isbn <= 0) {
            System.out.println("Invalid ISBN.");
            return null;
        }
        return searchRecursive(root, isbn);
    }

    private Book searchRecursive(Book node, int isbn) {
        if (node == null) return null;
        if (node.isbn == isbn) return node;
        if (isbn < node.isbn) return searchRecursive(node.left, isbn);
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
        if (node == null) return;
        inOrder(node.left);
        System.out.println(
            "ISBN: " + node.isbn +
            " | Title: " + node.title +
            " | Author: " + node.author
        );
        inOrder(node.right);
    }

    // Remove the book if book is borrowed
    public void delete(int isbn) {
        root = del(root, isbn);
    }

    private Book del(Book node, int isbn) {
        if (node == null) return null;
        if (isbn < node.isbn) {
            node.left = del(node.left, isbn);
        } else if (isbn > node.isbn) {
            node.right = del(node.right, isbn);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Book min = findMin(node.right);
            node.isbn = min.isbn;
            node.title = min.title;
            node.author = min.author;
            node.right = del(node.right, min.isbn);
        }
        return node;
    }

    private Book findMin(Book node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Save information about books to file
    public void saveToFile(PrintWriter writer) {
        saveToFileHelper(root, writer);
    }

    private void saveToFileHelper(Book node, PrintWriter writer) {
        if (node != null) {
            saveToFileHelper(node.left, writer);
            writer.println(node.isbn + "," + node.title + "," + node.author);
            saveToFileHelper(node.right, writer);
        }
    }
}