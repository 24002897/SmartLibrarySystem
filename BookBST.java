class BookBST {
    private Book root;

    public void insert(int isbn, String title, String author) {
        root = ins(root, isbn, title, author);
    }

    private Book ins(Book r, int i, String t, String a) {
        if (r == null) return new Book(i, t, a);
        if (i < r.isbn)
            r.left = ins(r.left, i, t, a);
        else if (i > r.isbn)
            r.right = ins(r.right, i, t, a);
        else
            System.out.println("ISBN already exists!");
        return r;
    }

    public Book search(int i) {
        return sea(root, i);
    }

    private Book sea(Book r, int i) {
        if (r == null || r.isbn == i) return r;
        return (i < r.isbn) ? sea(r.left, i) : sea(r.right, i);
    }

    public void displayAll() {
        if (root == null) {
            System.out.println("Catalogue is empty.");
        } else {
            System.out.println("All Books (sorted by ISBN):");
            inOrder(root);
        }
    }

    private void inOrder(Book r) {
        if (r == null) return;
        inOrder(r.left);
        System.out.println("ISBN: " + r.isbn + " | Title: " + r.title + " | Author: " + r.author);
        inOrder(r.right);
    }
}

