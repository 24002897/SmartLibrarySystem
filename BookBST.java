import smartlibrarysystem.BSTNode;

class BookBST {
    private BSTNode root;

    public void insert(int isbn, String title, String author) {
        root = ins(root, isbn, title, author);
    }

    private BSTNode ins(BSTNode r, int i, String t, String a) {
        if (r == null) return new BSTNode(new Book(i, t, a));
        if (i < r.book.isbn)
            r.left = ins(r.left, i, t, a);
        else if (i > r.book.isbn)
            r.right = ins(r.right, i, t, a);
        else
            System.out.println("ISBN already exists!");
        return r;
    }

    public void displayAll() {
        if (root == null) {
            System.out.println("Catalogue is empty.");
        } else {
            System.out.println("All Books (sorted by ISBN):");
            inOrder(root);
        }
    }

    private void inOrder(BSTNode r) {
    if (r == null) return;
    inOrder(r.left);
    System.out.println("ISBN: " + r.book.getIsbn() + " | Title: " + r.book.getTitle() + " | Author: " + r.book.getAuthor());
    inOrder(r.right);
    }

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

