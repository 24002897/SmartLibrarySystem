package smartlibrarysystem;

import java.io.PrintWriter;

public class BookBST {

    private Book root;


    public BookBST() {
        root = null;
    }

    //Add a book to the catalogue
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

    //Search for a book by ISBN
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

    //Using inOrder traversal to display books in sorted order by ISBN
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

    //Remove the book if book is borrowed
    public void delete(int isbn) {
        root = del(root, isbn);
    }

    private BSTNode del(BSTNode r, int i) {
        if (r == null) return null;
        if (i < r.book.getIsbn())
            r.left = del(r.left, i);
        else if (i > r.book.getIsbn())
            r.right = del(r.right, i);
        else {
            if (r.left == null) return r.right;
            if (r.right == null) return r.left;
            BSTNode min = findMin(r.right);
            r.book = min.book;
            r.right = del(r.right, min.book.getIsbn());
        }
        return r;
    }
    private BSTNode findMin(BSTNode r) {
        while (r.left != null) r = r.left;
        return r;
}
    //Save information about books to file
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